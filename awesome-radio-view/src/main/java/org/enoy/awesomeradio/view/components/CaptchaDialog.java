package org.enoy.awesomeradio.view.components;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.annotation.PrototypeScope;

import javax.annotation.PostConstruct;

@SpringComponent
@PrototypeScope
public class CaptchaDialog extends Window {

	@Autowired
	private CoinHiveCaptcha captcha;

	private Label contentLabel;

	@PostConstruct
	private void init() {
		setClosable(true);
		setDraggable(false);
		setModal(true);
		setResizable(false);

		setWidth(600, Unit.PIXELS);
		setHeight(400, Unit.PIXELS);

		captcha.setOnDone(this::close);

		contentLabel = new Label();
		VerticalLayout wrapper = new VerticalLayout();
		wrapper.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		wrapper.addComponents(contentLabel, captcha);

		setContent(new CenterWrapper<>(wrapper));

		center();
	}

	public void setOnDone(Runnable onDone) {
		captcha.setOnDone(() -> {
			close();
			onDone.run();
		});
	}

	public void setHashes(long hashes) {
		captcha.setHashes(hashes);
	}

	public void setTitle(String title) {
		setCaption(title);
	}

	public void setContent(String content) {
		contentLabel.setValue(content);
	}


}
