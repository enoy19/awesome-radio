package org.enoy.awesomeradio.view.events;

import org.enoy.awesomeradio.music.MusicDescription;

public class SongAddedEvent extends SongsUpdatedEvent {

	private final MusicDescription song;

	public SongAddedEvent(MusicDescription song) {
		this.song = song;
	}

	public MusicDescription getSong() {
		return song;
	}

}
