package org.enoy.awesomeradio.view.components;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.HorizontalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringComponent
@UIScope
public class AwesomeRadioBody extends HorizontalLayout {

	@Autowired
	private AwesomeRadioSongList songList;

	@Autowired
	private NextSong nextSong;

	@Autowired
	private Online online;

	@PostConstruct
	private void init() {

		songList.setSizeFull();
		nextSong.setSizeFull();
		online.setSizeFull();

		addComponent(songList);
		addComponent(nextSong);
		addComponent(online);

	}

}
