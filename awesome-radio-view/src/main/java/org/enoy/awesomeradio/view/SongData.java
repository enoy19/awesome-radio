package org.enoy.awesomeradio.view;

import com.vaadin.spring.annotation.SpringComponent;
import org.enoy.awesomeradio.music.MusicDescription;
import org.enoy.awesomeradio.music.MusicUrl;
import org.enoy.awesomeradio.view.events.SongAddedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.ApplicationScope;
import org.vaadin.spring.events.EventBus.ApplicationEventBus;
import org.vaadin.spring.events.EventScope;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@SpringComponent
@ApplicationScope
public class SongData {

	@Autowired
	private ApplicationEventBus eventBus;

	private List<MusicDescription> nextSongs;

	private long startedCurrentSongAt;

	private MusicDescription currentSong;

	private MusicUrl currentSongUrl;

	public SongData() {
		nextSongs = new ArrayList<>();
	}

	@PostConstruct
	private void init() {
		eventBus.subscribe(this);
	}

	@EventBusListenerMethod(scope = EventScope.APPLICATION)
	private void songAddedEvent(SongAddedEvent event) {
		addNextSong(event.getSong());
	}

	public void addNextSong(MusicDescription musicDescription) {
		synchronized (nextSongs) {
			nextSongs.add(musicDescription);
		}
	}

	/**
	 * removes MusicDescription at index 0 and returns it
	 *
	 * @return
	 */
	public MusicDescription removeNextSong() {
		synchronized (nextSongs) {
			if (nextSongs.size() > 0)
				return nextSongs.remove(0);
		}
		return null;
	}

	public Collection<MusicDescription> getNextSongs() {
		return Collections.unmodifiableCollection(nextSongs);
	}

	public long getStartedCurrentSongAt() {
		return startedCurrentSongAt;
	}

	public void setStartedCurrentSongAt(long startedCurrentSongAt) {
		this.startedCurrentSongAt = startedCurrentSongAt;
	}

	public MusicDescription getCurrentSong() {
		return currentSong;
	}

	public void setCurrentSong(MusicDescription currentSong) {
		this.currentSong = currentSong;
	}

	public MusicUrl getCurrentSongUrl() {
		return currentSongUrl;
	}

	public void setCurrentSongUrl(MusicUrl currentSongUrl) {
		this.currentSongUrl = currentSongUrl;
	}

	@PreDestroy
	private void destroy() {
		eventBus.unsubscribe(this);
	}

}
