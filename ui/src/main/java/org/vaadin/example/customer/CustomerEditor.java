package org.vaadin.example.customer;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.vaadin.example.backend.entity.Customer;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public class CustomerEditor extends Window {
    private static final long serialVersionUID = -7461517292586081314L;

    @Inject
    private CustomerForm customerForm;

    @PostConstruct
    protected void init() {
        setCaption("Customer editor");
        setCloseShortcut(KeyCode.ESCAPE);
        setModal(true);
        setResizable(false);
        setContent(customerForm);
    }

    public void openForCustomer(Customer customer) {
        customerForm.setEntity(customer);
        UI.getCurrent().addWindow(this);
        customerForm.focusFirst();
    }
}
