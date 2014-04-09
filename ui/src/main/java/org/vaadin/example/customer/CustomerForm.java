package org.vaadin.example.customer;

import javax.inject.Inject;

import org.vaadin.example.backend.entity.Customer;
import org.vaadin.maddon.fields.MTextField;
import org.vaadin.maddon.form.AbstractForm;
import org.vaadin.maddon.layouts.MVerticalLayout;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;

public class CustomerForm extends AbstractForm<Customer> {

    private static final long serialVersionUID = -1684898560662964709L;

    private TextField firstName = new MTextField("firstName");
    private TextField lastName = new MTextField("lastName");
    private DateField birthDate = new DateField("birthDate");

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
    }

    @Override
    protected Component createContent() {
        FormLayout formLayout = new FormLayout(firstName, lastName, birthDate);
        formLayout.setSizeFull();
        formLayout.setMargin(false);
        for (Component component : formLayout) {
            component.setWidth(100, Unit.PERCENTAGE);
        }

        return new MVerticalLayout(formLayout)
                .add(getToolbar(), Alignment.BOTTOM_RIGHT)
                .expand(formLayout);
    }

}
