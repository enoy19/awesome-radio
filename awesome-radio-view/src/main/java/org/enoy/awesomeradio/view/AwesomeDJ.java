package org.enoy.awesomeradio.view;

import org.enoy.awesomeradio.music.MusicDescription;
import org.enoy.awesomeradio.music.MusicProvider;
import org.enoy.awesomeradio.music.MusicUrl;
import org.enoy.awesomeradio.view.events.SongPlayEvent;
import org.enoy.awesomeradio.view.events.SongsUpdatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.vaadin.spring.events.EventBus.ApplicationEventBus;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Objects;

@Component
public class AwesomeDJ {

	@Autowired
	private ApplicationEventBus eventBus;

	@Autowired
	private MusicProvider musicProvider;

	@Autowired
	private SongData songData;

	@PostConstruct
	private void init() {
		eventBus.subscribe(this);
	}

	@Scheduled(fixedDelay = 300)
	private void check() {
		boolean done = isCurrentSongDone();

		if (done) {
			setNextSong();
		}

	}

	private void setNextSong() {
		MusicDescription nextSong = songData.removeNextSong();

		if (nextSong != null) {
			MusicUrl nextSongUrl = musicProvider.getUrl(nextSong);
			setupNextSong(nextSong, nextSongUrl);
			eventBus.publish(this, new SongsUpdatedEvent());
		}
	}

	private void setupNextSong(MusicDescription nextSong, MusicUrl nextSongUrl) {
		if (nextSongUrl != null) {
			songData.setCurrentSong(nextSong);
			songData.setStartedCurrentSongAt(System.currentTimeMillis());
			songData.setCurrentSongUrl(nextSongUrl);

			eventBus.publish(this, new SongPlayEvent(nextSongUrl, nextSong));
		}
	}

	private boolean isCurrentSongDone() {
		MusicDescription currentSong = songData.getCurrentSong();
		if (Objects.isNull(currentSong))
			return true;

		long time = System.currentTimeMillis();
		return time >= songData.getStartedCurrentSongAt() + currentSong.getLength();
	}

	@PreDestroy
	private void destroy() {
		eventBus.unsubscribe(this);
	}

}
