package org.vaadin.example.customer;

import javax.ejb.EJB;

import org.vaadin.example.AbstractPresenter;
import org.vaadin.example.backend.entity.Customer;
import org.vaadin.example.backend.service.customer.CustomerService;

import com.vaadin.cdi.UIScoped;

@UIScoped
public class CustomerViewPresenter extends AbstractPresenter<CustomerView> {

	@EJB
	private CustomerService customerService;

	@Override
	protected void onViewEnter() {
		getView().populateCustomers(customerService.getAllCustomers());
	}

	public void onCustomerSaved(Customer entity) {
		customerService.storeCustomer(entity);
		getView().populateCustomers(customerService.getAllCustomers());
	}
}
