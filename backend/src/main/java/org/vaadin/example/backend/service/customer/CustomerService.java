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
}
