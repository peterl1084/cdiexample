package org.vaadin.example.login;

import org.vaadin.example.ApplicationView;

public interface LoginView extends ApplicationView<LoginViewPresenter> {

    void showInvalidLoginNotification();

}
