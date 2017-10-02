package org.enoy.awesomeradio.view;

import com.vaadin.spring.annotation.SpringComponent;
import org.enoy.awesomeradio.view.events.LoggedInEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.ApplicationScope;
import org.vaadin.spring.events.EventBus.ApplicationEventBus;

import java.util.HashSet;
import java.util.Set;

@SpringComponent
@ApplicationScope
public class ApplicationData {

	@Autowired
	private ApplicationEventBus eventBus;

	private Set<String> loggedInUsers;

	ApplicationData() {
		loggedInUsers = new HashSet<>();
	}

	public boolean isUserLoggedIn(String username) {
		return loggedInUsers.contains(username);
	}

	public synchronized boolean login(String username) {
		if (isUserLoggedIn(username))
			return false;

		loggedInUsers.add(username);
		eventBus.publish(this, new LoggedInEvent(username));
		return true;
	}

}
