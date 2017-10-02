package org.enoy.awesomeradio.view.components;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
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

		addComponent(label);
		addComponent(textField);

	}

	private void login(String name) {
		name = name.trim();

		boolean isFree = !applicationData.isUserLoggedIn(name);

		if (isFree) {
			boolean successful = applicationData.login(name);

			if (successful) {
				sessionVars.setUsername(name);
				eventBus.publish(this, new LoggedInEvent(name));
			}
		}
	}

}
