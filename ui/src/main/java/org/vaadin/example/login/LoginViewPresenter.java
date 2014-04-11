package org.vaadin.example.login;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.vaadin.example.AbstractPresenter;

import com.vaadin.cdi.UIScoped;

@UIScoped
public class LoginViewPresenter extends AbstractPresenter<LoginView> {

    @Inject
    private Event<UserLoggedInEvent> loggedInEvent;

    @Override
    protected void onViewEnter() {

    }

    public void onLoginPressed(String username, String password) {
        UsernamePasswordToken token = new UsernamePasswordToken(username,
                password);

        try {
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);

            if (subject.isAuthenticated()) {
                loggedInEvent.fire(new UserLoggedInEvent(subject.getPrincipal()
                        .toString()));
            }
        } catch (AuthenticationException e) {
            getView().showInvalidLoginNotification();
        }
    }
}
