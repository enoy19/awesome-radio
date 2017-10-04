package org.enoy.awesomeradio.view;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import org.enoy.awesomeradio.view.components.CenterWrapper;
import org.enoy.awesomeradio.view.components.LoginComponent;
import org.enoy.awesomeradio.view.events.LoggedInEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventBus.UIEventBus;
import org.vaadin.spring.events.EventScope;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;

@SpringUI(path = AwesomeRadioLoginUI.UI_PATH)
@Theme("awesome-radio")
public class AwesomeRadioLoginUI extends UI {

	public static final String UI_PATH = "/";

	@Autowired
	private SessionVars sessionVars;

	@Autowired
	private LoginComponent loginComponent;

	@Autowired
	private UIEventBus eventBus;

	protected void init(VaadinRequest vaadinRequest) {
		if (!sessionVars.isLoggedIn()) {
			showLogin();
		} else {
			sendToAwesomeRadio();
		}
	}

	private void showLogin() {

		Window loginModal = new Window("Login", new CenterWrapper(loginComponent));
		loginModal.setClosable(false);
		loginModal.setModal(true);
		loginModal.center();
		loginModal.setResizable(false);
		loginModal.setDraggable(false);
		loginModal.setWidth(800, Unit.PIXELS);
		loginModal.setHeight(600, Unit.PIXELS);
		addWindow(loginModal);

	}

	private void sendToAwesomeRadio() {
		getPage().setLocation(AwesomeRadioUI.UI_PATH);
	}

	@EventBusListenerMethod(scope = EventScope.UI)
	private void loggedIn(LoggedInEvent e) {
		sendToAwesomeRadio();
	}

	@Override
	public void attach() {
		super.attach();
		eventBus.subscribe(this);
	}

	@Override
	public void detach() {
		super.detach();
		eventBus.unsubscribe(this);
	}

}
