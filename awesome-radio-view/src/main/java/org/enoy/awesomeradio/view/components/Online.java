package org.enoy.awesomeradio.view.components;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringComponent
@UIScope
public class Online extends VerticalLayout {

	@Autowired
	private UserGrid userGrid;

	@PostConstruct
	private void init() {

		setMargin(false);

		Label label = new Label("Online");
		label.addStyleName(ValoTheme.LABEL_HUGE);

		userGrid.setSizeFull();
		addComponent(label);
		addComponent(userGrid);

		setComponentAlignment(label, Alignment.MIDDLE_CENTER);

		setExpandRatio(userGrid, 1);

	}

}
