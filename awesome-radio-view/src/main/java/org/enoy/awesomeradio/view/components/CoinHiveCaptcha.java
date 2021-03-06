package org.enoy.awesomeradio.view.components;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.JavaScript;
import org.apache.commons.io.IOUtils;
import org.enoy.awesomeradio.view.CoinHiveTokenChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.vaadin.spring.annotation.PrototypeScope;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

@SpringComponent
@PrototypeScope
public class CoinHiveCaptcha extends CustomLayout {

	private static final String TEMPLATE_HTML = "coinhivecaptcha.html";
	private static final Random R = new Random();

	@Value("${coinhive.key.public}")
	private String coinHiveKey;

	@Autowired
	private CoinHiveTokenChecker coinHiveTokenChecker;

	private String randomCallback = "captcha_" + Math.abs(R.nextLong());
	private String token;
	private long hashes;

	private boolean valid;

	private Runnable onDone;

	@PostConstruct
	private void init() {
		try {
			String text = new String(IOUtils.toByteArray(getClass().getResourceAsStream(TEMPLATE_HTML)), StandardCharsets.UTF_8);

			text = text.replace("%callback%", randomCallback)
					.replace("%key%", coinHiveKey);

			setTemplateContents(text);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	public void setHashes(long hashes) {
		if (hashes % 256 != 0)
			throw new IllegalArgumentException("hashed must be multiple of 256");

		this.hashes = hashes;

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
			if (onDone != null)
				onDone.run();
		});

		setWidthUndefined();
	}

	public boolean isVerified() {
		if (valid)
			return true;

		if (token != null) {
			boolean checkedValid = coinHiveTokenChecker.isTokenValid(token, hashes);

			if (checkedValid) {
				this.valid = true;
				return true;
			}
		}

		return false;
	}

	public void setOnDone(Runnable onDone) {
		this.onDone = onDone;
	}

}
