package org.vaadin.example.login;

import org.vaadin.example.ApplicationView;

public interface LoginView extends ApplicationView<LoginViewPresenter> {

    public static final String ID = "";

    void showInvalidLoginNotification();

}
