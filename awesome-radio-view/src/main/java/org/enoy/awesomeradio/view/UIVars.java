package org.enoy.awesomeradio.view;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class UIVars {

	private VaadinRequest vaadinRequest;

	public VaadinRequest getVaadinRequest() {
		return vaadinRequest;
	}

	public void setVaadinRequest(VaadinRequest vaadinRequest) {
		this.vaadinRequest = vaadinRequest;
	}

}
