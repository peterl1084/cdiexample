package org.vaadin.example;

import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.vaadin.example.login.UserLoggedInEvent;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.ChameleonTheme;
import org.vaadin.cdiviewmenu.ViewMenuUI;
import static org.vaadin.cdiviewmenu.ViewMenuUI.getMenu;
import org.vaadin.example.customer.CustomerView;
import org.vaadin.example.customer.CustomerViewImpl;
import org.vaadin.example.login.LoginView;
import org.vaadin.maddon.label.RichText;
import org.vaadin.maddon.layouts.MVerticalLayout;

@CDIUI("")
@Theme("valo")
public class VaadinUI extends ViewMenuUI {

    private static final long serialVersionUID = 3618386613849364696L;

    private Button logout;

    private final Button.ClickListener logoutClickListener = new Button.ClickListener() {
        private static final long serialVersionUID = -1545988729141348821L;

        @Override
        public void buttonClick(ClickEvent event) {
            SecurityUtils.getSubject().logout();
            VaadinSession.getCurrent().close();
            Page.getCurrent().setLocation("");
        }
    };

    @Override
    protected void init(VaadinRequest request) {
        super.init(request);

        logout = new Button("Logout", logoutClickListener);
        logout.setIcon(FontAwesome.SIGN_OUT);

        if (!isLoggedIn()) {
            ViewMenuUI.getMenu().setVisible(false);
            ViewMenuUI.getMenu().navigateTo(LoginView.ID);
        } else {
            if (getNavigator().getState().isEmpty()) {
                ViewMenuUI.getMenu().navigateTo(CustomerViewImpl.class);
            }
        }
    }

    private boolean isLoggedIn() {
        Subject subject = SecurityUtils.getSubject();
        if (subject == null) {
            System.err.println("Could not find subject");
            return false;
        }

        return subject.isAuthenticated();
    }

    public void userLoggedIn(
            @Observes(notifyObserver = Reception.IF_EXISTS) UserLoggedInEvent event) {
        Notification.show("Welcome back " + event.getUsername());
        getMenu().navigateTo(CustomerView.ID);
        getMenu().addMenuItem(logout);
        getMenu().setVisible(isLoggedIn());
    }

}
