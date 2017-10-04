package org.enoy.awesomeradio.music;

public class MusicUrl {

	private final String url;
	private final boolean isRelative;

	public MusicUrl(String url, boolean isRelative) {
		this.url = url;
		this.isRelative = isRelative;
	}

	public String getUrl() {
		return url;
	}

	public boolean isRelative() {
		return isRelative;
	}

}
