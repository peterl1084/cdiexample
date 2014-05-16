package org.vaadin.example.example;

import org.vaadin.example.ApplicationView;

public interface ExampleView extends ApplicationView<ExampleViewPresenter> {
	
	void setMessage(String message);

}
