package org.vaadin.example.login;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.vaadin.example.AbstractView;
import org.vaadin.maddon.button.PrimaryButton;
import org.vaadin.maddon.fields.MTextField;
import org.vaadin.maddon.layouts.MVerticalLayout;

import com.vaadin.cdi.CDIView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

@CDIView
public class LoginViewImpl extends AbstractView<LoginViewPresenter> implements
        LoginView {

    private static final long serialVersionUID = -4954635993174003879L;

    @Inject
    private Instance<LoginViewPresenter> presenterInstance;

    private final Button.ClickListener loginClickListener = new Button.ClickListener() {
        private static final long serialVersionUID = 7742015379096464563L;

        @Override
        public void buttonClick(ClickEvent event) {
            getPresenter().onLoginPressed(username.getValue(),
                    password.getValue());
        }
    };

    private final TextField username = new MTextField()
            .withInputPrompt("User name");
    private final PasswordField password = new PasswordField();

    private final Button login = new PrimaryButton("Login", loginClickListener);

    public LoginViewImpl() {
        setSizeFull();

        // TODO remove these prefilled values used for testing
        username.setValue("admin");
        password.setValue("password");
        password.focus();

        Panel loginPanel = new Panel("Login to application");
        loginPanel.setSizeUndefined();

        loginPanel.setContent(new MVerticalLayout(username, password, login)
                .withAlign(login, Alignment.BOTTOM_RIGHT));

        setCompositionRoot(new MVerticalLayout(loginPanel).withAlign(
                loginPanel, Alignment.MIDDLE_CENTER).withFullHeight());
    }

    @Override
    public void showInvalidLoginNotification() {
        Notification.show("Login failed", "Invalid credentials",
                Type.TRAY_NOTIFICATION);
    }

    @Override
    protected LoginViewPresenter generatePresenter() {
        return presenterInstance.get();
    }

    @Override
    public String getName() {
        return "Login";
    }
}
