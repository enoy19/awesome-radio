package org.enoy.awesomeradio.view.components;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.function.Consumer;

public class ConfirmDialog extends Window {

	private Consumer<Boolean> listener;

	public ConfirmDialog(String title, String content, Consumer<Boolean> listener) {
		this.listener = listener;
		setCaption(title);
		setClosable(false);
		setDraggable(false);
		setModal(true);
		setResizable(false);

		setContent(new ConfirmDialogContent(content));
	}

	private synchronized void action(boolean result) {
		close();
		listener.accept(result);
	}

	private class ConfirmDialogContent extends VerticalLayout {

		public ConfirmDialogContent(String content) {

			setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);

			Label contentLabel = new Label(content);

			Button ok = new Button(VaadinIcons.CHECK);
			ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
			ok.addClickListener(e -> action(true));

			Button no = new Button(VaadinIcons.CLOSE);
			no.addClickListener(e -> action(false));

			HorizontalLayout buttonLayout = new HorizontalLayout();
			buttonLayout.addComponents(ok, no);

			addComponent(new CenterWrapper(contentLabel));
			addComponent(buttonLayout);
		}

	}

}
