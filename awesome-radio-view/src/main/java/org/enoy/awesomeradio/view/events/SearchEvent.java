package org.enoy.awesomeradio.view.events;

import java.io.Serializable;

public class SearchEvent implements Serializable {

	private final String query;

	public SearchEvent(String query) {
		this.query = query;
	}

}
