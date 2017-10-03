package org.enoy.awesomeradio.view;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;
import org.enoy.awesomeradio.user.AwesomeRadioUser;

@SpringComponent
@VaadinSessionScope
public class SessionVars {

	private AwesomeRadioUser user;

	public AwesomeRadioUser getUser() {
		return user;
	}

	public void setUser(AwesomeRadioUser user) {
		this.user = user;
	}

	public boolean isLoggedIn() {
		return user != null;
	}

}
