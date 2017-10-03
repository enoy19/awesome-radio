package org.enoy.awesomeradio.gmusic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;
import java.net.MalformedURLException;

@Configuration
public class GMusicProxyMusicProviderConfiguration extends WebMvcConfigurerAdapter {

	@Value("${awesomeradio.gmusic.proxy.url}")
	private String gmusicProxyAddress;

	@Value("${awesomeradio.gmusic.music.dir}")
	private String musicDir;

	@Value("${awesomeradio.gmusic.music.mapping}")
	private String musicMapping;

	@Bean
	GMusicProxyBridge gMusicProxyBridge() {
		return new GMusicProxyBridge(gmusicProxyAddress);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		File musicFile = new File(musicDir);

		if (!musicFile.exists())
			if (!musicFile.mkdirs())
				throw new IllegalStateException(String.format("Could not create: %s", musicFile.getAbsolutePath()));

		String musicUrl = getMusicUrl(musicFile);

		registry.addResourceHandler(String.format("%s/**", musicMapping))
				.addResourceLocations(musicUrl).setCachePeriod(1800);

	}

	private String getMusicUrl(File musicFile) {
		String musicUrl = null;
		try {
			musicUrl = musicFile.toURI().toURL().toExternalForm();
		} catch (MalformedURLException e) {
			throw new IllegalStateException(e);
		}
		return musicUrl;
	}
}
