package org.enoy.awesomeradio.view.components;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.enoy.awesomeradio.music.MusicDescription;
import org.enoy.awesomeradio.music.MusicProvider;
import org.enoy.awesomeradio.view.SessionVars;
import org.enoy.awesomeradio.view.events.SearchEvent;
import org.enoy.awesomeradio.view.events.SongAddedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.vaadin.spring.events.EventBus.ApplicationEventBus;
import org.vaadin.spring.events.EventBus.UIEventBus;
import org.vaadin.spring.events.EventScope;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;

@SpringComponent
@UIScope
public class AwesomeRadioSongList extends VerticalLayout {

	@Autowired
	private SessionVars sessionVars;

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

	@Autowired
	private ApplicationContext context;

	@Value("${hashes.add.song}")
	private long hashes;

	@PostConstruct
	private void init() {

		setMargin(false);

		songSearch.setWidth(100, Unit.PERCENTAGE);
		musicGrid.setSizeFull();

		Button addSongButton = new Button(VaadinIcons.ARROW_RIGHT);
		addSongButton.setWidth(100, Unit.PERCENTAGE);
		addSongButton.addStyleName(ValoTheme.BUTTON_PRIMARY);

		addComponent(songSearch);
		addComponent(musicGrid);
		addComponent(addSongButton);

		setExpandRatio(musicGrid, 1);

		musicGrid.addItemClickListener(e -> {
			if (sessionVars.isLoggedIn() && e.getMouseEventDetails().isDoubleClick()) {
				addSongConfirm(e.getItem());
			}
		});

		addSongButton.addClickListener(e -> {
			MusicDescription value = musicGrid.asSingleSelect().getValue();
			if (Objects.nonNull(value)) {
				addSongConfirm(value);
			}
		});

	}

	private void addSongConfirm(MusicDescription item) {
		String content = String.format("Please complete the captcha to add \"%s\"", item.getTitle());

		CaptchaDialog dialog = context.getBean(CaptchaDialog.class);

		dialog.setTitle("Confirm add Song");
		dialog.setHashes(hashes);
		dialog.setContent(content);
		dialog.setOnDone(() -> addSong(item));

		getUI().addWindow(dialog);
	}

	private void addSong(MusicDescription item) {
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
