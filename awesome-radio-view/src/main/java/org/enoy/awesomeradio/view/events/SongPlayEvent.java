package org.enoy.awesomeradio.view.events;

import com.vaadin.server.Resource;
import org.enoy.awesomeradio.music.MusicDescription;

public class SongPlayEvent {

	private final Resource song;
	private final MusicDescription musicDescription;

	public SongPlayEvent(Resource song, MusicDescription musicDescription) {
		this.song = song;
		this.musicDescription = musicDescription;
	}

	public Resource getSong() {
		return song;
	}

	public MusicDescription getMusicDescription() {
		return musicDescription;
	}

}
