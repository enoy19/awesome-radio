package org.enoy.awesomeradio.view;

import com.vaadin.server.ExternalResource;
import org.enoy.awesomeradio.music.MusicDescription;
import org.enoy.awesomeradio.music.MusicProvider;
import org.enoy.awesomeradio.view.events.SongPlayEvent;
import org.enoy.awesomeradio.view.events.SongsUpdatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.vaadin.spring.events.EventBus.ApplicationEventBus;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.MalformedURLException;
import java.net.URL;
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
			String nextSongUrl = musicProvider.getUrl(nextSong);

			ExternalResource resource = getResource(nextSongUrl);
			setupNextSong(nextSong, resource);

			eventBus.publish(this, new SongsUpdatedEvent());
		}
	}

	private void setupNextSong(MusicDescription nextSong, ExternalResource resource) {
		if (resource != null) {
			songData.setCurrentSong(nextSong);
			songData.setStartedCurrentSongAt(System.currentTimeMillis());
			songData.setCurrentSongResource(resource);

			eventBus.publish(this, new SongPlayEvent(resource, nextSong));
		}
	}

	private ExternalResource getResource(String nextSongUrl) {
		try {
			return new ExternalResource(new URL(nextSongUrl));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return null;
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
