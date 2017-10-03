package org.enoy.awesomeradio.view.components;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Grid;
import org.enoy.awesomeradio.music.MusicDescription;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

@SpringComponent
@Scope("prototype")
public class MusicGrid extends Grid<MusicDescription> {

	public MusicGrid() {
		super(MusicDescription.class);
	}

	@PostConstruct
	private void init() {

		removeColumn("uniqueIdentifier");
		removeColumn("length");

	}

}
