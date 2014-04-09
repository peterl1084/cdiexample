package org.vaadin.example.customer;

import java.util.Collection;

import org.vaadin.example.ApplicationView;
import org.vaadin.example.backend.entity.Customer;

public interface CustomerView extends ApplicationView<CustomerViewPresenter> {

	void populateCustomers(Collection<Customer> customers);

	void setCustomer(Customer customer);
}
