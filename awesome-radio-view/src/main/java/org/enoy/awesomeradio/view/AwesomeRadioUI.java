package org.enoy.awesomeradio.view;

import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.Push;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI(path = AwesomeRadioUI.UI_PATH)
@JavaScript("vaadin://js/awesome-radio.js")
@Push
public class AwesomeRadioUI extends UI {

	public static final String UI_PATH = "awesome-radio";

	@Autowired
	private AwesomeRadioContent content;

	protected void init(VaadinRequest vaadinRequest) {
		setContent(content);
	}

}
