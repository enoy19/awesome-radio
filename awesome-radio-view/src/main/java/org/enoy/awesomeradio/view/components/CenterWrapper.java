package org.enoy.awesomeradio.view.components;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

public class CenterWrapper extends VerticalLayout {

	public CenterWrapper(Component component) {
		setSizeFull();
		setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		addComponent(component);
	}

}
