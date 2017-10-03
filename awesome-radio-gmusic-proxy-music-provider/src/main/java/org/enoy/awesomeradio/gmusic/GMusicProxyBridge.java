package org.enoy.awesomeradio.gmusic;

import com.mpatric.mp3agic.*;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GMusicProxyBridge {

	private static final String G_MUSIC_BASE_URL = "https://play.google.com/music/m/";

	private static final String REGEX_URL = "https:\\/\\/play.google.com\\/music\\/m\\/([A-z0-9]+)";
	private static final Pattern PATTERN_URL = Pattern.compile(REGEX_URL);

	private static final String REGEX_PLAYLIST_FILE = "^#EXTINF:[0-9]+,(.*) - (.*)$\\n^http.\\/\\/.*\\/get_song\\?id=(.*)$";
	private static final Pattern PATTERN_PLAYLIST_FILE = Pattern.compile(REGEX_PLAYLIST_FILE, Pattern.MULTILINE);

	private static final String REGEX_LEGAL_FILENAME = "[^ A-z0-9_\\-\\.\\(\\)ÖöÄäÜü]";

	private String proxyBaseUrl;

	public GMusicProxyBridge(String proxyBaseUrl) {
		this.proxyBaseUrl = proxyBaseUrl;
	}

	/**
	 * @see #search(String, String, int)
	 */
	public String[][] search(String artist, String title) throws IOException {
		return search(artist, title, 10);
	}

	/**
	 * @param artist
	 * @param title
	 * @param numTracks
	 * @return String[][] 1=Artist 2=Title 3=ID
	 * @throws IOException
	 */
	public String[][] search(String artist, String title, int numTracks) throws IOException {
		String path = "/get_by_search";

		String params = "?type=matches&num_tracks=" + numTracks;

		if (artist != null) {
			artist = URLEncoder.encode(artist, "UTF-8");
			params += "&artist=" + artist;
		}

		if (title != null) {
			title = URLEncoder.encode(title, "UTF-8");
			params += "&title=" + title;
		}

		String urlString = proxyBaseUrl + path + params;

		Response response = Jsoup.connect(urlString)//
				.ignoreContentType(true)//
				.execute();

		if (response.statusCode() == 200) {
			List<String> data = new ArrayList<>();

			String contentData = response.body();
			Matcher matcher = PATTERN_PLAYLIST_FILE.matcher(contentData);

			while (matcher.find()) {
				// Artist
				data.add(matcher.group(1));
				// Title
				data.add(matcher.group(2));
				// ID
				data.add(matcher.group(3));
			}

			String[][] result = new String[data.size() / 3][3];

			for (int i = 0; i < result.length; i++) {
				result[i][0] = data.get(i * 3);
				result[i][1] = data.get(i * 3 + 1);
				result[i][2] = data.get(i * 3 + 2);
			}

			return result;

		} else {
			throw new IOException("Statuscode != 200");
		}

	}

	public File downloadWithLink(String downloadDir, String link) throws UnsupportedTagException, InvalidDataException, NotSupportedException, IOException {
		return downloadWithLink(new File(downloadDir), link);
	}

	public File downloadWithLink(File downloadDir, String link) throws UnsupportedTagException, InvalidDataException, NotSupportedException, IOException {

		Matcher matcher = PATTERN_URL.matcher(link);

		String id;
		if (matcher.find()) {
			id = matcher.group(1);
		} else {
			return null;
		}

		return download(downloadDir, id);
	}

	public File download(String downloadDir, final String id)
			throws UnsupportedTagException, InvalidDataException, NotSupportedException, IOException {
		return download(new File(downloadDir), id);
	}

	public File download(File downloadDir, final String id)
			throws IOException, UnsupportedTagException, InvalidDataException, NotSupportedException {

		if (!downloadDir.exists()) {
			downloadDir.mkdirs();
		}

		String urlString = G_MUSIC_BASE_URL + id;

		String path = "/get_song";
		String params = "?id=" + id;
		String gmusicUrl = proxyBaseUrl + path + params;

		Document doc = Jsoup.connect(urlString).get();

		String imageUrl = doc.select("body img").get(0).attr("src");
		String songTitle = doc.select("body h1").get(0).html();
		songTitle = songTitle.replaceAll(REGEX_LEGAL_FILENAME, "_");

		File songFile = new File(downloadDir, songTitle + ".mp3");
		if (songFile.exists()) {
			return songFile;
		}

		File rawSongFile = Files.createTempFile(null, null).toFile();

		try {
			downloadMusic(gmusicUrl, rawSongFile);
			URL url = new URL(gmusicUrl);
		} catch (SocketException e) {
			throw new IOException("Failed to get song data", e);
		}

		try {
			Response response = Jsoup.connect(imageUrl)//
					.ignoreContentType(true)//
					.execute();

			if (response.statusCode() != 200) {
				throw new IOException("Failed to get album art");
			}

			Mp3File mp3File = new Mp3File(rawSongFile);
			ID3v2 id3v2Tag;
			if (mp3File.hasId3v2Tag()) {
				id3v2Tag = mp3File.getId3v2Tag();
			} else {
				id3v2Tag = new ID3v24Tag();
				mp3File.setId3v2Tag(id3v2Tag);
			}

			id3v2Tag.setAlbumImage(response.bodyAsBytes(), response.contentType());

			mp3File.save(songFile.getAbsolutePath());
		} catch (SocketException e) {
			throw new IOException("Failed to get album art", e);
		}

		return songFile;

	}

	private void downloadMusic(String gmusicUrl, File rawSongFile) throws IOException {
		URL website = new URL(gmusicUrl);
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		FileOutputStream fos = new FileOutputStream(rawSongFile);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
	}

}
