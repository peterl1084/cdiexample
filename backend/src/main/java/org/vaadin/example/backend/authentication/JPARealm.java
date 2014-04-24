package org.vaadin.example.backend.authentication;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.vaadin.example.backend.entity.Customer;
import org.vaadin.example.backend.service.customer.CustomerService;

public class JPARealm extends AuthorizingRealm {

    @EJB
    private CustomerService service;

    @PostConstruct
    protected void initialize() {

    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals) {
        Object availablePrincipal = getAvailablePrincipal(principals);

        if (availablePrincipal != null) {
            String principalName = availablePrincipal.toString();
            return service.getUserByUsername(principalName)
                    .getAsAuthorizationInfo();
        }

        return new SimpleAuthorizationInfo();
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken token) throws AuthenticationException {

        // null usernames are invalid
        if (token == null) {
            throw new AuthenticationException(
                    "PrincipalCollection method argument cannot be null.");
        }

        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;

        Customer customer = service.getUserByUsername(usernamePasswordToken
                .getUsername());

        if (customer == null) {
            throw new AuthenticationException("Could not find user");
        }

        if (getCredentialsMatcher().doCredentialsMatch(usernamePasswordToken,
                customer.getAsAuthenticationInfo())) {
            return customer.getAsAuthenticationInfo();
        }

        throw new AuthenticationException("Failed to authenticate");
    }
}
