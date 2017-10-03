package org.enoy.awesomeradio.view.components;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
import org.enoy.awesomeradio.view.events.SearchEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventBus.UIEventBus;

import javax.annotation.PostConstruct;

@SpringComponent
@UIScope
public class SongSearch extends HorizontalLayout {

	@Autowired
	private UIEventBus eventBus;

	@PostConstruct
	private void init() {
		final TextField searchText = new TextField();
		searchText.setPlaceholder("Search Song");

		Button searchButton = new Button(VaadinIcons.SEARCH);
		searchButton.addStyleName(ValoTheme.BUTTON_SMALL);

		searchText.addShortcutListener(new ShortcutListener("Search", KeyCode.ENTER, null) {
			@Override
			public void handleAction(Object o, Object o1) {
				search(searchText.getValue());
			}
		});

		searchButton.addClickListener(e -> search(searchText.getValue()));

		setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		searchText.setWidth(100, Unit.PERCENTAGE);

		addComponent(searchText);
		addComponent(searchButton);

		setExpandRatio(searchText, 1);

	}

	private void search(String query) {
		if (query == null || (query = query.trim()).isEmpty())
			return;

		eventBus.publish(this, new SearchEvent(query));
	}

}
