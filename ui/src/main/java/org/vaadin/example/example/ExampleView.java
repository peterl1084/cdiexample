package org.vaadin.example.example;

import org.vaadin.example.ApplicationView;

public interface ExampleView extends ApplicationView<ExampleViewPresenter> {
    
    public static final String ID = "example";
	
	void setMessage(String message);

}
