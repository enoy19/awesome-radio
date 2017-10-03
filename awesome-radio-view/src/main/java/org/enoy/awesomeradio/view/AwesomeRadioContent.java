package org.enoy.awesomeradio.view;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.VerticalLayout;
import org.enoy.awesomeradio.view.components.AwesomeRadioBody;
import org.enoy.awesomeradio.view.components.AwesomeRadioHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventBus.ApplicationEventBus;

import javax.annotation.PostConstruct;

@SpringComponent
@UIScope
public class AwesomeRadioContent extends VerticalLayout {

	@Autowired
	private AwesomeRadioHeader header;

	@Autowired
	private AwesomeRadioBody body;

	@Autowired
	private ApplicationEventBus eventBus;

	@PostConstruct
	private void init() {

		setSizeFull();

		header.setWidth(100, Unit.PERCENTAGE);
		body.setSizeFull();

		addComponent(header);
		addComponent(body);

		setExpandRatio(body, 1);

	}


}
