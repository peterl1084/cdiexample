package org.vaadin.example.backend.service.customer;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.vaadin.example.backend.entity.Customer;

@Stateless
public class CustomerServiceBean implements CustomerService {

	@PersistenceContext(unitName = "example")
	private EntityManager entityManager;

	@Override
	public void storeCustomer(Customer customer) {
	}

	@Override
	public Collection<Customer> getAllCustomers() {
		Customer customer = new Customer();
		customer.setFirstName("a");
		customer.setLastName("b");
		customer.setBirthDate(new Date());

		return Collections.singleton(customer);
	}
}
