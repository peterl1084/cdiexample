package org.vaadin.example;

public class ViewNavigationEvent {

    private final String viewName;
	private String viewId;

    public ViewNavigationEvent(String viewName, String id) {
        this.viewName = viewName;
        this.viewId = id;
    }

    public String getViewName() {
        return viewName;
    }
    
    public String getViewId() {
		return viewId;
	}
}
