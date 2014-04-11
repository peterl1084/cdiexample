package org.vaadin.example;

public class ViewNavigationEvent {

    private final String viewName;

    public ViewNavigationEvent(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }
}
