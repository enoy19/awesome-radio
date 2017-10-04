package org.enoy.awesomeradio.view;

import com.vaadin.spring.annotation.SpringComponent;
import org.enoy.awesomeradio.user.AwesomeRadioUser;
import org.enoy.awesomeradio.view.events.LogoutEvent;
import org.enoy.awesomeradio.view.events.UpdateUserGridEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.ApplicationScope;
import org.vaadin.spring.events.EventBus.ApplicationEventBus;
import org.vaadin.spring.events.EventScope;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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

	@PostConstruct
	private void init() {
		eventBus.subscribe(this);
	}

	private boolean isUserLoggedIn(AwesomeRadioUser user) {
		return loggedInUsers.contains(user);
	}

	public synchronized AwesomeRadioUser login(String username) {
		AwesomeRadioUser user = new AwesomeRadioUser(username);

		if (isUserLoggedIn(user))
			return null;

		loggedInUsers.add(user);
		updateUserGridEvent();

		return user;
	}

	public Collection<AwesomeRadioUser> getLoggedInUsers() {
		return Collections.unmodifiableCollection(loggedInUsers);
	}

	@EventBusListenerMethod(scope = EventScope.APPLICATION)
	private void userLoggedOut(LogoutEvent event) {
		synchronized (loggedInUsers) {
			loggedInUsers.remove(event.getUser());
		}

		updateUserGridEvent();
	}

	private void updateUserGridEvent() {
		eventBus.publish(this, new UpdateUserGridEvent());
	}

	@PreDestroy
	private void destroy() {
		eventBus.unsubscribe(this);
	}

}
