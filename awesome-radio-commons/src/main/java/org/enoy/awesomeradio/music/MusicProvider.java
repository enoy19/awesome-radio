package org.enoy.awesomeradio.music;

import java.util.List;

public interface MusicProvider {

	List<MusicDescription> search(String query);

	MusicUrl getUrl(MusicDescription musicDescription);

}
