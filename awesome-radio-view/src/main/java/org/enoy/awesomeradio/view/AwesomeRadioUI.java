package org.enoy.awesomeradio.view;

import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.Push;
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

@SpringUI
@JavaScript("vaadin://js/awesome-radio.js")
@Push
public class AwesomeRadioUI extends UI {

	@Autowired
	private SessionVars sessionVars;

	@Autowired
	private LoginComponent loginComponent;

	@Autowired
	private AwesomeRadioContent content;

	@Autowired
	private UIEventBus uiEventBus;

	private Window loginModal;

	protected void init(VaadinRequest vaadinRequest) {
		if (!sessionVars.isLoggedIn()) {
			showLogin();
			return;
		}

		setupAwesomeRadio();

	}

	private void showLogin() {

		loginModal = new Window("Login", new CenterWrapper(loginComponent));
		loginModal.setClosable(false);
		loginModal.setModal(true);
		loginModal.center();
		loginModal.setResizable(false);
		loginModal.setDraggable(false);
		loginModal.setWidth(800, Unit.PIXELS);
		loginModal.setHeight(600, Unit.PIXELS);
		addWindow(loginModal);

	}

	private void setupAwesomeRadio() {
		if (loginModal != null && loginModal.isAttached())
			loginModal.close();

		setContent(content);
	}

	@EventBusListenerMethod(scope = EventScope.UI)
	private void loggedIn(LoggedInEvent event) {
		setupAwesomeRadio();
	}

	@Override
	public void attach() {
		super.attach();
		uiEventBus.subscribe(this);
	}

	@Override
	public void detach() {
		super.detach();
		uiEventBus.unsubscribe(this);
	}

}
