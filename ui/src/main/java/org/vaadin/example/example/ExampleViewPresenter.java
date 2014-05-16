package org.vaadin.example.example;

import java.util.Date;

import org.vaadin.example.AbstractPresenter;

import com.vaadin.cdi.UIScoped;

@UIScoped
public class ExampleViewPresenter extends AbstractPresenter<ExampleView> {

    @Override
    protected void onViewEnter() {

    }

	public void doSomething() {
		getView().setMessage("We did something at " + new Date());
	}

}
