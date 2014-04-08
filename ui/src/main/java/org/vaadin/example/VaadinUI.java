package org.vaadin.example;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.cdi.CDIUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@CDIUI
public class VaadinUI extends UI {

	@WebServlet(asyncSupported = true, value = "/*")
	@VaadinServletConfiguration(ui = VaadinUI.class, productionMode = false)
	public class VaadinApplicationServlet extends VaadinServlet {
		private static final long serialVersionUID = 4882186678319076880L;

		@Override
		public void init(ServletConfig servletConfig) throws ServletException {
			super.init(servletConfig);
		}
	}

	@Override
	protected void init(VaadinRequest request) {

	}

}
