package org.vaadin.example.login;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.vaadin.example.AbstractView;

import com.vaadin.cdi.CDIView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@CDIView
public class LoginViewImpl extends AbstractView<LoginViewPresenter> implements
		LoginView {

	private static final long serialVersionUID = -1717018541782513599L;

	private final VerticalLayout layout;

	private final TextField username;
	private final PasswordField password;

	private final Button login;

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

	public LoginViewImpl() {
		setSizeFull();

		layout = new VerticalLayout();
		layout.setSizeFull();

		Panel loginPanel = new Panel("Login to application");
		loginPanel.setWidth(250, Unit.PIXELS);

		VerticalLayout panelContent = new VerticalLayout();
		panelContent.setWidth(100, Unit.PERCENTAGE);
		panelContent.setMargin(true);
		panelContent.setSpacing(true);

		username = new TextField();
		username.setWidth(100, Unit.PERCENTAGE);
		username.setInputPrompt("User name");

		password = new PasswordField();
		password.setWidth(100, Unit.PERCENTAGE);
		password.setInputPrompt("Password");

		login = new Button("Login", loginClickListener);

		panelContent.addComponents(username, password, login);
		panelContent.setComponentAlignment(login, Alignment.BOTTOM_RIGHT);

		loginPanel.setContent(panelContent);

		layout.addComponent(loginPanel);
		layout.setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);

		setCompositionRoot(layout);
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
