package org.enoy.awesomeradio.music;

import java.util.List;

public interface MusicProvider {

	List<MusicDescription> search(String query);

	String getUrl(MusicDescription musicDescription);

}
