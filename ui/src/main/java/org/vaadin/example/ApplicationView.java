package org.vaadin.example;

public interface ApplicationView<P extends AbstractPresenter> {

    P getPresenter();

    String getName();
}
