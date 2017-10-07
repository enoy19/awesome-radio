package org.enoy.awesomeradio.view;

import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.server.VaadinSession.State;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import org.enoy.awesomeradio.user.AwesomeRadioUser;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI(path = AwesomeRadioUI.UI_PATH)
@JavaScript({"https://coinhive.com/lib/coinhive.min.js", "https://coinhive.com/lib/captcha.min.js", "vaadin://js/jquery-3.2.1.min.js", "vaadin://js/awesome-radio.js"})
@Push
@Theme("awesome-radio")
public class AwesomeRadioUI extends UI {

	public static final String UI_PATH = "awesome-radio";

	@Autowired
	private AwesomeRadioContent content;

	@Autowired
	private SessionVars sessionVars;

	@Autowired
	private ApplicationData applicationData;

	protected void init(VaadinRequest vaadinRequest) {
		addDetachListener(e -> {
			if (VaadinSession.getCurrent().getState() == State.CLOSED
					|| VaadinSession.getCurrent().getState() == State.CLOSING
					|| VaadinSession.getCurrent().getUIs().size() == 0) {
				AwesomeRadioUser user = sessionVars.getUser();
				sessionVars.reset();
				applicationData.logout(user);
			}
		});

		setContent(content);
	}

}
