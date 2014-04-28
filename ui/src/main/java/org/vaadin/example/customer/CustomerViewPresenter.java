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

        if (event.getCustomer().isPersisted()) {
            if (event.isNewPasswordGiven()) {
                // Check that new password matches the confirmation
                if (event.isPasswordMatchingConfirmation()) {
                    event.getCustomer().setHumanReadablePassword(
                            event.getPassword());
                    storeCustomerAndRefreshView(event);
                } else {
                    getView().showPasswordDontMatchNotification();
                }
            } else {
                // Save without changing password
                storeCustomerAndRefreshView(event);
            }
        } else {
            // New customer must always have password
            if (event.isNewPasswordGiven()
                    && event.isPasswordMatchingConfirmation()) {
                event.getCustomer().setHumanReadablePassword(
                        event.getPassword());
                storeCustomerAndRefreshView(event);
            } else {
                getView().showPasswordDontMatchNotification();
            }
        }
    }

    private void storeCustomerAndRefreshView(CustomerSavedEvent event) {
        customerService.storeCustomer(event.getCustomer());
        getView().populateCustomers(customerService.getAllCustomers());
        getView().removeTableSelection();
        getView().closeEditor();
    }

    public void onNewCustomer(
            @Observes(notifyObserver = Reception.IF_EXISTS) CustomerAddedEvent event) {
        getView().removeTableSelection();
        getView().openEditorFor(new Customer());
    }

    public void onCustomerSelected(
            @Observes(notifyObserver = Reception.IF_EXISTS) CustomerSelectedEvent event) {
        if (event.getCustomer() == null) {
            getView().closeEditor();
        } else {
            getView().openEditorFor(event.getCustomer());
        }
    }

    public void onCustomerReset(
            @Observes(notifyObserver = Reception.IF_EXISTS) CustomerResetEvent event) {
        getView().closeEditor();
    }

    public void onCustomerRemoved(
            @Observes(notifyObserver = Reception.IF_EXISTS) CustomerRemovedEvent event) {
        customerService.removeCustomer(event.getCustomer());
        getView().populateCustomers(customerService.getAllCustomers());
    }
}
