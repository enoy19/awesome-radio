package org.enoy.awesomeradio.view.components;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Resource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.enoy.awesomeradio.music.MusicDescription;
import org.enoy.awesomeradio.music.MusicUrl;
import org.enoy.awesomeradio.view.SongData;
import org.enoy.awesomeradio.view.UIVars;
import org.enoy.awesomeradio.view.events.SongPlayEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventBus.ApplicationEventBus;
import org.vaadin.spring.events.EventScope;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;

import javax.annotation.PostConstruct;
import java.util.Objects;

@SpringComponent
@UIScope
public class AwesomeRadioHeader extends HorizontalLayout {

	public static final String AWESOME_RADIO_AUDIO_ID = "awesome-radio-audio";

	private static final String LEADING_SLASHES_REGEX = "^\\/+";

	@Autowired
	private ApplicationEventBus eventBus;

	@Autowired
	private SongData songData;

	@Autowired
	private UIVars uiVars;

	private Audio audio;

	@PostConstruct
	private void init() {

		setMargin(false);

		audio = new Audio();
		audio.setId(AWESOME_RADIO_AUDIO_ID);
		audio.setWidth(100, Unit.PERCENTAGE);
		audio.setHeight(50, Unit.PIXELS);

		Button synchronizeButton = new Button(VaadinIcons.HAMMER);
		synchronizeButton.setDescription("Synchronize Audio");
		synchronizeButton.addStyleName(ValoTheme.BUTTON_SMALL);
		synchronizeButton.addClickListener(e -> synchronizeAudio());

		setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		addComponent(synchronizeButton);
		addComponent(audio);

		setExpandRatio(audio, 1);

	}

	@EventBusListenerMethod(scope = EventScope.APPLICATION)
	private void playSong(SongPlayEvent event) {
		MusicDescription musicDescription = event.getMusicDescription();

		Resource resource = getSongResource(event.getSongUrl());

		getUI().access(() -> playSong(musicDescription, resource));
	}

	private Resource getSongResource(MusicUrl songUrl) {
		if (Objects.nonNull(songUrl))
			if (songUrl.isRelative()) {
				String relativeSongUrl = songUrl.getUrl();
				relativeSongUrl = relativeSongUrl.replaceAll(LEADING_SLASHES_REGEX, "");
				return new ExternalResource(relativeSongUrl);
			} else {
				return new ExternalResource(songUrl.getUrl());
			}

		return null;
	}

	private void playSong(MusicDescription musicDescription, Resource song) {
		if (Objects.nonNull(musicDescription) && Objects.nonNull(song)) {
			audio.setCaption(musicDescription.getTitle());
			audio.setSource(song);
			audio.play();
			synchronizeAudio();
		}
	}

	private void synchronizeAudio() {
		long time = System.currentTimeMillis() - songData.getStartedCurrentSongAt();
		double seconds = (double) time / 1000d;
		JavaScript.getCurrent().execute(String.format("syncAwesomeRadio(%f)", seconds));
	}

	@Override
	public void attach() {
		super.attach();
		eventBus.subscribe(this);

		playSong(songData.getCurrentSong(), getSongResource(songData.getCurrentSongUrl()));
	}

	@Override
	public void detach() {
		super.detach();
		eventBus.unsubscribe(this);
	}
}
