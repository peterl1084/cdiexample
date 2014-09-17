package org.vaadin.example.customer;

import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import javax.inject.Inject;
import org.apache.shiro.SecurityUtils;
import org.vaadin.example.backend.entity.Customer;
import org.vaadin.maddon.MBeanFieldGroup;
import org.vaadin.maddon.fields.MTextField;
import org.vaadin.maddon.form.AbstractForm;
import org.vaadin.maddon.layouts.MVerticalLayout;

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
            CustomerSavedEvent customerSavedEvent = buildCustomerSaveEvent(customer);
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
        // Make the form validate on the fly and enable save cancel buttons intelligently
        setEagarValidation(true);
        setSavedHandler(formSaveHandler);
        setResetHandler(formResetHandler);

        roles.addItem("admin");
        roles.addItem("user");
        roles.setMultiSelect(true);
        roles.setConverter(new CollectionToCommaSeparatedStringConverter());

        roles.setItemCaption("admin", "Admin");
        roles.setItemCaption("user", "User");
        
        userName.setIcon(FontAwesome.USER);
        
        roles.setIcon(FontAwesome.USERS);

        formLayout = new FormLayout(userName, firstName, lastName, birthDate,
                roles);
    }

    @Override
    public MBeanFieldGroup<Customer> setEntity(Customer entity) {
        MBeanFieldGroup<Customer> fg = super.setEntity(entity);

        // Customize the forms password stuff slightly based on its state
        if (entity.isPersisted()) {
            formLayout.addComponent(passwordField);
            formLayout.addComponent(passwordConfirmationField);
            userName.setReadOnly(true);

            passwordField.setCaption("New password");
        } else {
            formLayout.addComponent(passwordField, 1);
            formLayout.addComponent(passwordConfirmationField, 2);
        }
        return fg;
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

    private CustomerSavedEvent buildCustomerSaveEvent(Customer customer) {
        CustomerSavedEvent customerSavedEvent = new CustomerSavedEvent(customer);
        customerSavedEvent.setPassword(passwordField.getValue());
        customerSavedEvent.setPasswordConfirmation(passwordConfirmationField
                .getValue());
        return customerSavedEvent;
    }

    /**
     * Only admin role is allowed to edit
     * 
     * @param formLayout
     * @param toolbar 
     */
    private void hideToolbarFromNonAdminUsers(FormLayout formLayout,
            Layout toolbar) {
        // TODO AbstractForm in Maddon should have overridden setReadOnly
        // method that would do this
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
    protected Button createSaveButton() {
        Button b = super.createSaveButton();
        b.setIcon(FontAwesome.FLOPPY_O);
        return b;
    }

    @Override
    protected Button createCancelButton() {
        Button b = super.createCancelButton();
        b.setIcon(FontAwesome.UNDO);
        return b;
    }
    
    
    
}
