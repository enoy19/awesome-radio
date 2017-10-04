package org.enoy.awesomeradio.view.components;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.enoy.awesomeradio.view.SongData;
import org.enoy.awesomeradio.view.events.SongsUpdatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventBus.ApplicationEventBus;
import org.vaadin.spring.events.EventScope;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;

import javax.annotation.PostConstruct;

@SpringComponent
@UIScope
public class NextSong extends VerticalLayout {

	@Autowired
	private ApplicationEventBus applicationEventBus;

	@Autowired
	private SongData songData;

	@Autowired
	private MusicGrid musicGrid;

	@PostConstruct
	private void init() {

		setMargin(false);

		Label label = new Label("Next");
		label.addStyleName(ValoTheme.LABEL_HUGE);

		musicGrid.setSizeFull();

		musicGrid.getColumn("artist").setSortable(false);
		musicGrid.getColumn("title").setSortable(false);

		addComponent(label);
		addComponent(musicGrid);

		setComponentAlignment(label, Alignment.MIDDLE_CENTER);

		setExpandRatio(musicGrid, 1);

		updateGrid();

	}

	@EventBusListenerMethod(scope = EventScope.APPLICATION)
	private void songAdded(SongsUpdatedEvent event) {
		getUI().access(this::updateGrid);
	}

	private void updateGrid() {
		musicGrid.setItems(songData.getNextSongs());
	}

	@Override
	public void attach() {
		super.attach();
		applicationEventBus.subscribe(this);
	}

	@Override
	public void detach() {
		super.detach();
		applicationEventBus.unsubscribe(this);
	}
}
