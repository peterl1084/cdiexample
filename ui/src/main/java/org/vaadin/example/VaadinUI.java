package org.vaadin.example;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@CDIUI
@Theme("dawn")
public class VaadinUI extends UI {
	private static final long serialVersionUID = 3618386613849364696L;

	@Override
	protected void init(VaadinRequest request) {

	}

}
