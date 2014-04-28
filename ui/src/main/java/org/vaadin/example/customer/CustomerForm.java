package org.vaadin.example.customer;

import javax.inject.Inject;

import org.apache.shiro.SecurityUtils;
import org.vaadin.example.backend.entity.Customer;
import org.vaadin.maddon.fields.MTextField;
import org.vaadin.maddon.form.AbstractForm;
import org.vaadin.maddon.layouts.MVerticalLayout;

import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class CustomerForm extends AbstractForm<Customer> {

    private static final long serialVersionUID = -1684898560662964709L;

    @PropertyId("username")
    private TextField userName = new MTextField("userName");

    @PropertyId("password")
    private TextField password = new MTextField("password");

    @PropertyId("firstName")
    private TextField firstName = new MTextField("firstName");

    @PropertyId("lastName")
    private TextField lastName = new MTextField("lastName");

    @PropertyId("birthDate")
    private DateField birthDate = new DateField("birthDate");

    @PropertyId("roles")
    private OptionGroup roles = new OptionGroup("role");

    @Inject
    private javax.enterprise.event.Event<CustomerSavedEvent> saveEvent;

    @Inject
    private javax.enterprise.event.Event<CustomerResetEvent> resetEvent;

    private AbstractForm.SavedHandler<Customer> formSaveHandler = new AbstractForm.SavedHandler<Customer>() {
        @Override
        public void onSave(Customer customer) {
            saveEvent.fire(new CustomerSavedEvent(customer));
        }
    };

    private AbstractForm.ResetHandler<Customer> formResetHandler = new AbstractForm.ResetHandler<Customer>() {

        @Override
        public void onReset(Customer entity) {
            resetEvent.fire(new CustomerResetEvent());
        }
    };

    public CustomerForm() {
        setSavedHandler(formSaveHandler);
        setResetHandler(formResetHandler);

        roles.addItem("admin");
        roles.addItem("user");

        roles.setItemCaption("admin", "Admin");
        roles.setItemCaption("user", "User");

        roles.setValue("user");
    }

    @Override
    protected Component createContent() {
        userName.setRequired(true);
        password.setRequired(true);

        FormLayout formLayout = new FormLayout(userName, password, firstName,
                lastName, birthDate, roles);
        formLayout.setSizeFull();
        formLayout.setMargin(false);

        for (Component component : formLayout) {
            component.setWidth(100, Unit.PERCENTAGE);
        }

        Layout toolbar = getToolbar();
        VerticalLayout layout = new MVerticalLayout(formLayout).add(toolbar,
                Alignment.BOTTOM_RIGHT).expand(formLayout);

        hideToolbarFromNonAdminUsers(formLayout, toolbar);

        return layout;
    }

    private void hideToolbarFromNonAdminUsers(FormLayout formLayout,
            Layout toolbar) {
        if (SecurityUtils.getSubject().hasRole("admin")) {
            toolbar.setVisible(true);
            for (Component component : formLayout) {
                component.setReadOnly(false);
            }
        } else {
            toolbar.setVisible(false);
            for (Component component : formLayout) {
                component.setReadOnly(true);
            }
        }
    }
}
