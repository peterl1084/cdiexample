package org.vaadin.example.backend.authentication;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.vaadin.example.backend.entity.Customer;

public class UserAuthorizationInfo implements AuthorizationInfo {

    private static final long serialVersionUID = 9131325399537928839L;

    private Customer customer;

    public UserAuthorizationInfo(Customer customer) {
        this.customer = customer;
    }

    @Override
    public Collection<String> getRoles() {
        return Collections.unmodifiableCollection(Arrays.asList(customer
                .getRoles().split("\\s*(,)\\s*")));
    }

    @Override
    public Collection<String> getStringPermissions() {
        return Collections.emptySet();
    }

    @Override
    public Collection<Permission> getObjectPermissions() {
        return Collections.emptySet();
    }
}
