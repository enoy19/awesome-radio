package org.enoy.awesomeradio.view.components;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.VerticalLayout;
import org.enoy.awesomeradio.view.events.SearchEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventBus.UIEventBus;
import org.vaadin.spring.events.EventScope;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;

import javax.annotation.PostConstruct;

@SpringComponent
@UIScope
public class AwesomeRadioSongList extends VerticalLayout {

	@Autowired
	private SongSearch songSearch;

	@Autowired
	private UIEventBus eventBus;

	@Autowired
	private MusicGrid musicGrid;

	@PostConstruct
	private void init() {


		addComponent(songSearch);
		addComponent(musicGrid);

	}

	@EventBusListenerMethod(scope = EventScope.UI)
	private void search(SearchEvent searchEvent) {
		// TODO
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
