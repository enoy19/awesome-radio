package org.enoy.awesomeradio.view.components;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import org.enoy.awesomeradio.view.AwesomeRadioLoginUI;

@SpringComponent
@UIScope
public class LoginToAddSongs extends CenterWrapper<Button> {

	public LoginToAddSongs() {
		super(new Button(VaadinIcons.SIGN_IN));
		component.setDescription("Login to add Songs!");
		component.addClickListener(e -> {
			getUI().getPage().setLocation(AwesomeRadioLoginUI.UI_PATH);
		});
	}

}
