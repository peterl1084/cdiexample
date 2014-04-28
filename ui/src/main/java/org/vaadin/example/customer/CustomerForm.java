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
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class CustomerForm extends AbstractForm<Customer> {

    private static final long serialVersionUID = -1684898560662964709L;

    private FormLayout formLayout;

    @PropertyId("username")
    private TextField userName = new MTextField("Username");

    @PropertyId("firstName")
    private TextField firstName = new MTextField("First name");

    @PropertyId("lastName")
    private TextField lastName = new MTextField("Last name");

    @PropertyId("birthDate")
    private DateField birthDate = new DateField("Birth Date");

    @PropertyId("roles")
    private OptionGroup roles = new OptionGroup("Roles");

    private PasswordField passwordField = new PasswordField("Password");
    private PasswordField passwordConfirmationField = new PasswordField(
            "Password confirmation");

    @Inject
    private javax.enterprise.event.Event<CustomerSavedEvent> saveEvent;

    @Inject
    private javax.enterprise.event.Event<CustomerResetEvent> resetEvent;

    private AbstractForm.SavedHandler<Customer> formSaveHandler = new AbstractForm.SavedHandler<Customer>() {
        @Override
        public void onSave(Customer customer) {
            CustomerSavedEvent customerSavedEvent = new CustomerSavedEvent(
                    customer);
            customerSavedEvent.setPassword(passwordField.getValue());
            customerSavedEvent
                    .setPasswordConfirmation(passwordConfirmationField
                            .getValue());
            saveEvent.fire(customerSavedEvent);
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
        roles.setMultiSelect(true);
        roles.setConverter(new CollectionToCommaSeparatedStringConverter());

        roles.setItemCaption("admin", "Admin");
        roles.setItemCaption("user", "User");

        formLayout = new FormLayout(userName, firstName, lastName, birthDate,
                roles);
    }

    @Override
    protected Component createContent() {
        userName.setRequired(true);

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

    @Override
    public void setEntity(Customer entity) {
        super.setEntity(entity);

        if (entity.isPersisted()) {
            formLayout.addComponent(passwordField, 5);
            formLayout.addComponent(passwordConfirmationField, 6);
            userName.setReadOnly(true);
        } else {
            formLayout.addComponent(passwordField, 1);
            formLayout.addComponent(passwordConfirmationField, 2);
        }
    }
}
