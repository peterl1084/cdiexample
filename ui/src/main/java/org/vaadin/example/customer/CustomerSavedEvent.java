package org.vaadin.example.customer;

import org.vaadin.example.backend.entity.Customer;

public class CustomerSavedEvent {

	private final Customer customer;

	public CustomerSavedEvent(Customer customer) {
		this.customer = customer;
	}

	public Customer getCustomer() {
		return customer;
	}
}
