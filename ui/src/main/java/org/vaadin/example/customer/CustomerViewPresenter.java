package org.vaadin.example.customer;

import javax.ejb.EJB;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;

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

	public void onCustomerSaved(
			@Observes(notifyObserver = Reception.IF_EXISTS) CustomerSavedEvent event) {
		customerService.storeCustomer(event.getCustomer());
		getView().populateCustomers(customerService.getAllCustomers());
		getView().setCustomer(null);
	}

	public void onNewCustomer(
			@Observes(notifyObserver = Reception.IF_EXISTS) CustomerAddedEvent event) {
		getView().setCustomer(new Customer());
	}

	public void onCustomerSelected(
			@Observes(notifyObserver = Reception.IF_EXISTS) CustomerSelectedEvent event) {
		getView().setCustomer(event.getCustomer());
	}
}
