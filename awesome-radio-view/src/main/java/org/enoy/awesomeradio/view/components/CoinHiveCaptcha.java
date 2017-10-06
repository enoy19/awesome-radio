package org.enoy.awesomeradio.view.components;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.JavaScript;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

@SpringComponent
@UIScope
public class CoinHiveCaptcha extends CustomLayout {

	private static final String TEMPLATE_HTML = "coinhivecaptcha.html";
	private static final Random R = new Random();

	@Value("${coinhive.key.public}")
	private String coinHiveKey;

	private String randomCallback = "captcha_" + R.nextLong();
	private String token;

	@PostConstruct
	private void init() {
		try {
			URI uri = getClass().getResource(TEMPLATE_HTML).toURI();
			Path path = Paths.get(uri);
			String text = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);

			text = text.replace("%callback%", randomCallback)
					.replace("%key%", coinHiveKey);

			setTemplateContents(text);
		} catch (IOException | URISyntaxException e) {
			throw new IllegalStateException(e);
		}
	}

	public void setHashes(long hashes) {
		if (hashes % 256 != 0)
			throw new IllegalArgumentException("hashed must be multiple of 256");

		String contents = getTemplateContents();
		contents = contents.replace("%hashes%", Long.toString(hashes));
		setTemplateContents(contents);
	}

	@Override
	public void attach() {
		super.attach();
		JavaScript.getCurrent().execute("CoinHive.Captcha.CreateElements()");
		JavaScript.getCurrent().addFunction(randomCallback, args -> {
			token = args.getString(0);
		});
	}

	public boolean isVerified() {
		return token != null;
	}
}
