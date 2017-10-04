package org.enoy.awesomeradio.view.components;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Grid;
import org.enoy.awesomeradio.user.AwesomeRadioUser;
import org.enoy.awesomeradio.view.ApplicationData;
import org.enoy.awesomeradio.view.events.UpdateUserGridEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.vaadin.spring.events.EventBus.ApplicationEventBus;
import org.vaadin.spring.events.EventScope;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;

import javax.annotation.PostConstruct;

@SpringComponent
@Scope("prototype")
public class UserGrid extends Grid<AwesomeRadioUser> {

	@Autowired
	private ApplicationData data;

	@Autowired
	private ApplicationEventBus eventBus;

	public UserGrid() {

	}

	@PostConstruct
	private void init() {
		Column<AwesomeRadioUser, String> username = addColumn(AwesomeRadioUser::getUsername);
		username.setCaption("User");
		refresh();
	}

	public void refresh() {
		setItems(data.getLoggedInUsers());
	}

	@EventBusListenerMethod(scope = EventScope.APPLICATION)
	private void updateUserGrid(UpdateUserGridEvent event) {
		getUI().access(this::refresh);
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
