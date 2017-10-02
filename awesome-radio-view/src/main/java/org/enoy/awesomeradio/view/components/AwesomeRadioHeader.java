package org.enoy.awesomeradio.view.components;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringComponent
@UIScope
public class AwesomeRadioHeader extends HorizontalLayout {

	@Autowired
	private MusicProgress musicProgress;

	@PostConstruct
	private void init() {

		setMargin(false);

		Button muteButton = new Button(VaadinIcons.SOUND_DISABLE);
		muteButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);

		setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		addComponent(muteButton);
		addComponent(musicProgress);

		setExpandRatio(musicProgress, 1);

	}

}
