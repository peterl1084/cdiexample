package org.vaadin.example;

import javax.inject.Inject;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;

public class ApplicationViewArea implements ViewDisplay {
    private static final long serialVersionUID = -3155582495185310845L;

    @Inject
    private javax.enterprise.event.Event<ViewNavigationEvent> navigationEvent;

    private Panel container;

    public ApplicationViewArea() {
        container = new Panel();
        container.setSizeFull();
    }

    @Override
    public void showView(View view) {
        if (view instanceof ApplicationView) {
            ApplicationView applicationView = (ApplicationView) view;
			navigationEvent.fire(new ViewNavigationEvent(applicationView
					.getName(), applicationView.getId()));
        }
        container.setContent((Component) view);
    }

    public Panel getViewContainer() {
        return container;
    }
}
