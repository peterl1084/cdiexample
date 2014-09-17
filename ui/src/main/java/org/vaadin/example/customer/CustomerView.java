package org.vaadin.example.customer;

import java.util.Collection;

import org.vaadin.example.ApplicationView;
import org.vaadin.example.backend.entity.Customer;

public interface CustomerView extends ApplicationView<CustomerViewPresenter> {
    
    public static final String ID = "customers";

    void removeTableSelection();

    void populateCustomers(Collection<Customer> customers);

    void openEditorFor(Customer customer);

    void closeEditor();

    void showPasswordDontMatchNotification();
}
