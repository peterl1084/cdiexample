package org.vaadin.example.backend.service.customer;

import java.util.Collection;

import javax.ejb.Local;

import org.vaadin.example.backend.entity.Customer;

@Local
public interface CustomerService {

	/**
	 * Stores given customer
	 * 
	 * @param customer
	 * @throws RuntimeException
	 *             if customer is already persisted and update would result in
	 *             changing customer's username.
	 */
	void storeCustomer(Customer customer);

	/**
	 * @return retrieves all customers
	 */
	Collection<Customer> getAllCustomers();

	/**
	 * Removes given customer
	 * 
	 * @param customer
	 */
	void removeCustomer(Customer customer);

	/**
	 * Retrieves user from the service with given username
	 * 
	 * @param username
	 * @return user object instance or null if no such user was found.
	 */
	Customer getUserByUsername(String username);
}
