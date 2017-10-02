package org.enoy.awesomeradio.view.components;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Grid;
import org.enoy.awesomeradio.music.MusicDescription;

import javax.annotation.PostConstruct;

@SpringComponent
@UIScope
public class MusicGrid extends Grid<MusicDescription> {

	public MusicGrid() {
		super(MusicDescription.class);
	}

	@PostConstruct
	private void init() {

	}

}
