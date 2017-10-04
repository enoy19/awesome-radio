package org.enoy.awesomeradio.view.components;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

public class CenterWrapper<T extends Component> extends VerticalLayout {

	protected final T component;

	public CenterWrapper(T component) {
		this.component = component;
		setSizeFull();
		setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		addComponent(component);
	}

}
