package org.enoy.awesomeradio.view;

import com.vaadin.spring.annotation.SpringComponent;
import org.enoy.awesomeradio.user.AwesomeRadioUser;
import org.enoy.awesomeradio.view.events.LoggedInEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.ApplicationScope;
import org.vaadin.spring.events.EventBus.ApplicationEventBus;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@SpringComponent
@ApplicationScope
public class ApplicationData {

	@Autowired
	private ApplicationEventBus eventBus;

	private Set<AwesomeRadioUser> loggedInUsers;

	ApplicationData() {
		loggedInUsers = new HashSet<>();
	}

	private boolean isUserLoggedIn(AwesomeRadioUser user) {
		return loggedInUsers.contains(user);
	}

	public synchronized AwesomeRadioUser login(String username) {
		AwesomeRadioUser user = new AwesomeRadioUser(username);

		if (isUserLoggedIn(user))
			return null;

		loggedInUsers.add(user);
		eventBus.publish(this, new LoggedInEvent(user));

		return user;
	}

	public Collection<AwesomeRadioUser> getLoggedInUsers() {
		return Collections.unmodifiableCollection(loggedInUsers);
	}

}
