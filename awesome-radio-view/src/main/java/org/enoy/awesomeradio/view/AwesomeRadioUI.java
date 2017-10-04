package org.enoy.awesomeradio.view;

import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI(path = AwesomeRadioUI.UI_PATH)
@JavaScript("vaadin://js/awesome-radio.js")
@Push
@Theme("awesome-radio")
public class AwesomeRadioUI extends UI {

	public static final String UI_PATH = "awesome-radio";

	@Autowired
	private AwesomeRadioContent content;

	@Autowired
	private UIVars uiVars;

	protected void init(VaadinRequest vaadinRequest) {
		VaadinSession.getCurrent().getSession().setMaxInactiveInterval(-1);
		uiVars.setVaadinRequest(vaadinRequest);
		setContent(content);
	}

}
