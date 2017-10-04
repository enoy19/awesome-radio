package org.enoy.awesomeradio.view.events;

import org.enoy.awesomeradio.user.AwesomeRadioUser;

public class LogoutEvent {

	private final AwesomeRadioUser user;

	public LogoutEvent(AwesomeRadioUser user) {
		this.user = user;
	}

	public AwesomeRadioUser getUser() {
		return user;
	}
}
