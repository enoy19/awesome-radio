package org.enoy.awesomeradio.view.events;

import org.enoy.awesomeradio.user.AwesomeRadioUser;

import java.io.Serializable;

public class LoggedInEvent implements Serializable {

	private final AwesomeRadioUser user;

	public LoggedInEvent(AwesomeRadioUser user) {
		this.user = user;
	}

	public AwesomeRadioUser getUser() {
		return user;
	}

}
