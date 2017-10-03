package org.enoy.awesomeradio.view.components;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.VerticalLayout;
import org.enoy.awesomeradio.music.MusicDescription;
import org.enoy.awesomeradio.music.MusicProvider;
import org.enoy.awesomeradio.view.events.SearchEvent;
import org.enoy.awesomeradio.view.events.SongAddedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventBus.ApplicationEventBus;
import org.vaadin.spring.events.EventBus.UIEventBus;
import org.vaadin.spring.events.EventScope;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringComponent
@UIScope
public class AwesomeRadioSongList extends VerticalLayout {

	@Autowired
	private SongSearch songSearch;

	@Autowired
	private UIEventBus eventBus;

	@Autowired
	private ApplicationEventBus applicationEventBus;

	@Autowired
	private MusicGrid musicGrid;

	@Autowired
	private MusicProvider musicProvider;

	@PostConstruct
	private void init() {

		setMargin(false);

		songSearch.setWidth(100, Unit.PERCENTAGE);
		musicGrid.setSizeFull();

		addComponent(songSearch);
		addComponent(musicGrid);

		setExpandRatio(musicGrid, 1);

		musicGrid.addItemClickListener(e -> {
			if (e.getMouseEventDetails().isDoubleClick()) {
				addSongConfirm(e.getItem());
			}
		});

	}

	private void addSongConfirm(MusicDescription item) {
		String content = String.format("Are you sure that you want to add \"%s\" ?", item.getTitle());
		ConfirmDialog confirmDialog = new ConfirmDialog("Add Song", content, result -> addSong(result, item));

		getUI().addWindow(confirmDialog);
	}

	private void addSong(boolean sure, MusicDescription item) {
		if (sure)
			applicationEventBus.publish(this, new SongAddedEvent(item));
	}

	@EventBusListenerMethod(scope = EventScope.UI)
	private void search(SearchEvent searchEvent) {
		List<MusicDescription> result = musicProvider.search(searchEvent.getQuery());
		musicGrid.setItems(result);
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
