package org.enoy.awesomeradio.view.components;

import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.vaadin.spring.events.EventBus.ApplicationEventBus;

@Service
public class CaptchaDialogService {

	@Autowired
	private ApplicationEventBus eventBus;

	@Value("${coinhive.active}")
	private boolean coinHiveActive;

	/**
	 * if ${coinhive.active} property is true a coinhive captcha dialog is shown.
	 * otherwise it will just execute the onDone param
	 *
	 * @see #showCaptchaDialog(UI, String, String, long, Runnable)
	 */
	public void showCaptchaDialogIfCoinHiveActive(UI ui, String title, String content, long hashes, Runnable onDone) {
		if (coinHiveActive) {
			showCaptchaDialog(ui, title, content,hashes, onDone);
		} else {
			onDone.run();
		}
	}

	public void showCaptchaDialog(UI ui, String title, String content, long hashes, Runnable onDone) {
		CaptchaDialog dialog = createCaptchaDialog();

		dialog.setTitle(title);
		dialog.setContent(content);
		dialog.setHashes(hashes);
		dialog.setOnDone(onDone);

		ui.addWindow(dialog);
	}

	@Lookup
	CaptchaDialog createCaptchaDialog() {
		return null;
	}

}
