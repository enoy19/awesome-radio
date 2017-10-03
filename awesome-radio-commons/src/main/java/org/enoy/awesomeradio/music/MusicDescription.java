package org.enoy.awesomeradio.music;

import java.io.Serializable;

public class MusicDescription implements Serializable {

	private String uniqueIdentifier;
	private String title;
	private String artist;
	private long length;

	public String getUniqueIdentifier() {
		return uniqueIdentifier;
	}

	public void setUniqueIdentifier(String uniqueIdentifier) {
		this.uniqueIdentifier = uniqueIdentifier;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artists) {
		this.artist = artists;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MusicDescription that = (MusicDescription) o;

		return uniqueIdentifier.equals(that.uniqueIdentifier);
	}

	@Override
	public int hashCode() {
		return uniqueIdentifier.hashCode();
	}
}
