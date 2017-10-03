package org.enoy.awesomeradio.view.events;

import java.io.Serializable;
import java.util.Objects;

public class SearchEvent implements Serializable {

	private final String query;

	public SearchEvent(String query) {
		Objects.requireNonNull(query);
		this.query = query.trim().toLowerCase();
	}

	public String getQuery() {
		return query;
	}
}
