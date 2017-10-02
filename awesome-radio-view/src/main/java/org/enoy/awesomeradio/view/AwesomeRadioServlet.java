package org.enoy.awesomeradio.view;

import com.vaadin.server.*;

import javax.servlet.ServletException;

// @SpringComponent
public class AwesomeRadioServlet extends VaadinServlet
		implements SessionInitListener, SessionDestroyListener {

	@Override
	protected void servletInitialized() throws ServletException {
		super.servletInitialized();
		getService().addSessionInitListener(this);
		getService().addSessionDestroyListener(this);
	}

	public void sessionInit(SessionInitEvent sessionInitEvent) throws ServiceException {
		System.out.println("NEW SESSION!");
	}

	public void sessionDestroy(SessionDestroyEvent sessionDestroyEvent) {
		System.out.println("SESSION DESTROYED!");
	}
}
