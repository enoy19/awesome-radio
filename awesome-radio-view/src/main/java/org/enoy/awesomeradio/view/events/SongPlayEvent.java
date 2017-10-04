package org.enoy.awesomeradio.view.events;

import org.enoy.awesomeradio.music.MusicDescription;
import org.enoy.awesomeradio.music.MusicUrl;

public class SongPlayEvent {

	private final MusicUrl songUrl;
	private final MusicDescription musicDescription;

	public SongPlayEvent(MusicUrl songUrl, MusicDescription musicDescription) {
		this.songUrl = songUrl;
		this.musicDescription = musicDescription;
	}

	public MusicUrl getSongUrl() {
		return songUrl;
	}

	public MusicDescription getMusicDescription() {
		return musicDescription;
	}

}
