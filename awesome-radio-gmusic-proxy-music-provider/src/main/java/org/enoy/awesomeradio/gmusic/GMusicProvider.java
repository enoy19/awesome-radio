package org.enoy.awesomeradio.gmusic;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;
import org.enoy.awesomeradio.music.MusicDescription;
import org.enoy.awesomeradio.music.MusicProvider;
import org.enoy.awesomeradio.music.MusicUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class GMusicProvider implements MusicProvider {

	private static final Logger LOGGER = LoggerFactory.getLogger(GMusicProvider.class);

	@Autowired
	private GMusicProxyBridge proxyBridge;

	@Value("${awesomeradio.gmusic.music.dir}")
	private String musicDir;

	@Value("${awesomeradio.gmusic.music.mapping}")
	private String musicMapping;

	@Override
	public synchronized List<MusicDescription> search(String query) {

		List<MusicDescription> musicDescriptions = new ArrayList<>(10);

		String[][] result;
		try {
			result = proxyBridge.search(null, query);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
			return musicDescriptions;
		}

		for (String[] musicData : result) {
			MusicDescription description = new MusicDescription();
			description.setArtist(musicData[0]);
			description.setTitle(musicData[1]);
			description.setUniqueIdentifier(musicData[2]);
			musicDescriptions.add(description);
		}

		return musicDescriptions;
	}

	@Override
	public MusicUrl getUrl(MusicDescription musicDescription) {
		try {
			File downloaded = proxyBridge.download(musicDir, musicDescription.getUniqueIdentifier());

			Mp3File mp3File = new Mp3File(downloaded);
			musicDescription.setLength(mp3File.getLengthInMilliseconds());

			String url = String.format("/%s/%s", musicMapping, getEncodedUrlName(downloaded));

			return new MusicUrl(url, true);
		} catch (UnsupportedTagException | InvalidDataException | NotSupportedException | IOException e) {
			throw new IllegalStateException(e);
		}
	}

	private String getEncodedUrlName(File downloaded) throws UnsupportedEncodingException {
		return URLEncoder.encode(downloaded.getName(), StandardCharsets.UTF_8.toString()).replaceAll("\\+", "%20");
	}

}
