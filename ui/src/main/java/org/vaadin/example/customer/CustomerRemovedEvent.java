package org.vaadin.example.customer;

import org.vaadin.example.backend.entity.Customer;

public class CustomerRemovedEvent {
	private final Customer customer;

	public CustomerRemovedEvent(Customer customer) {
		this.customer = customer;
	}

	public Customer getCustomer() {
		return customer;
	}
}
