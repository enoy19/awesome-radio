package org.enoy.awesomeradio.view.events;

import java.io.Serializable;

public class LoggedInEvent implements Serializable {

	private final String username;

	public LoggedInEvent(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

}
