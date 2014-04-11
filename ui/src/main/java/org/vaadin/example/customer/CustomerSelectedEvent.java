package org.vaadin.example.customer;

import org.vaadin.example.backend.entity.Customer;

public class CustomerSelectedEvent {

    private final Customer customer;

    public CustomerSelectedEvent(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }
}
