package org.vaadin.example.backend.authentication;

import org.apache.shiro.authc.SaltedAuthenticationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.vaadin.example.backend.entity.Customer;

public class UserAuthenticationInfo implements SaltedAuthenticationInfo {
    private static final long serialVersionUID = -2712083588381423408L;

    public static final String PW_SALT = "5e4989e7b7571323fae65f7abe299f0e";

    private Customer customer;

    public UserAuthenticationInfo(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }

        this.customer = customer;
    }

    @Override
    public PrincipalCollection getPrincipals() {
        SimplePrincipalCollection principalCollection = new SimplePrincipalCollection();
        principalCollection.add(customer.getUsername(), "JPA");

        return principalCollection;
    }

    @Override
    public Object getCredentials() {
        return customer.getPassword();
    }

    @Override
    public ByteSource getCredentialsSalt() {
        return ByteSource.Util.bytes(PW_SALT);
    }
}
