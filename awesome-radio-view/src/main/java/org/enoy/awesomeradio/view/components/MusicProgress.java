package org.enoy.awesomeradio.view.components;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import javax.annotation.PostConstruct;

@SpringComponent
@UIScope
public class MusicProgress extends VerticalLayout {

	@PostConstruct
	private void init() {

		Label musicTitle = new Label("<TITLE>");
		musicTitle.addStyleName(ValoTheme.LABEL_TINY);

		ProgressBar progressBar = new ProgressBar(0f);
		progressBar.setWidth(100, Unit.PERCENTAGE);

		setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		addComponent(musicTitle);
		addComponent(progressBar);

	}

}
