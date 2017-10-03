package org.enoy.awesomeradio.user;

import java.io.Serializable;

public class AwesomeRadioUser implements Serializable {

	private String username;
	private AwesomeRadioUser controller;
	private int songPoints;

	public AwesomeRadioUser(String username) {
		this.username = username;
		this.songPoints = 3;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public AwesomeRadioUser getController() {
		return controller;
	}

	public void setController(AwesomeRadioUser controller) {
		this.controller = controller;
	}

	public int getSongPoints() {
		return songPoints;
	}

	public void setSongPoints(int songPoints) {
		this.songPoints = songPoints;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AwesomeRadioUser that = (AwesomeRadioUser) o;

		return username.equals(that.username);
	}

	@Override
	public int hashCode() {
		return username.hashCode();
	}
}
