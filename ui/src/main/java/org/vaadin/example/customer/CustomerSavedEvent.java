package org.vaadin.example.customer;

import org.vaadin.example.backend.entity.Customer;

public class CustomerSavedEvent {

    private final Customer customer;
    private String password;
    private String passwordConfirmation;

    public CustomerSavedEvent(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public boolean isPasswordMatchingConfirmation() {
        return isNewPasswordGiven() && password.equals(passwordConfirmation);
    }

    public boolean isNewPasswordGiven() {
        return password != null && !password.isEmpty();
    }
}
