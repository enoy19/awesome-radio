package org.enoy.awesomeradio.view.components;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;
import org.enoy.awesomeradio.user.AwesomeRadioUser;
import org.enoy.awesomeradio.view.ApplicationData;
import org.enoy.awesomeradio.view.SessionVars;
import org.enoy.awesomeradio.view.events.LoggedInEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.vaadin.spring.events.EventBus.UIEventBus;

import javax.annotation.PostConstruct;

@SpringComponent
@Scope("prototype")
public class LoginComponent extends VerticalLayout {

	@Autowired
	private SessionVars sessionVars;

	@Autowired
	private ApplicationData applicationData;

	@Autowired
	private UIEventBus eventBus;

	@Autowired
	private CoinHiveCaptcha captcha;

	@PostConstruct
	private void init() {

		setWidthUndefined();

		Label label = new Label("Enter Username");
		label.addStyleName(ValoTheme.LABEL_HUGE);

		final TextField textField = new TextField();
		textField.setPlaceholder("Username");
		textField.addShortcutListener(new ShortcutListener("Login", KeyCode.ENTER, null) {
			@Override
			public void handleAction(Object o, Object o1) {
				login(textField.getValue());
			}
		});

		Button loginButton = new Button(VaadinIcons.SIGN_IN);
		loginButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
		loginButton.addClickListener(e -> login(textField.getValue()));

		setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

		captcha.setHashes(256);

		addComponent(label);
		addComponent(textField);
		addComponent(captcha);
		addComponent(loginButton);

	}

	private void login(String name) {
		if (!captcha.isVerified()) {
			Notification.show("Please verify captcha", Type.ERROR_MESSAGE);
			return;
		}

		name = name.trim();

		if (name.isEmpty())
			return;


		AwesomeRadioUser user = applicationData.login(name);

		if (user != null) {
			sessionVars.setUser(user);
			eventBus.publish(this, new LoggedInEvent(user));
		}
	}

}
