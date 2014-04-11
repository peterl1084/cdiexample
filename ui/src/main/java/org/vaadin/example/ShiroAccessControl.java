package org.vaadin.example;

import javax.enterprise.inject.Specializes;

import org.apache.shiro.SecurityUtils;

import com.vaadin.cdi.access.JaasAccessControl;

@Specializes
public class ShiroAccessControl extends JaasAccessControl {
    private static final long serialVersionUID = 8372568791751361472L;

    @Override
    public boolean isUserSignedIn() {
        return SecurityUtils.getSubject().isAuthenticated();
    }

    @Override
    public boolean isUserInRole(String role) {
        return SecurityUtils.getSubject().hasRole(role);
    }

    @Override
    public String getPrincipalName() {
        Object principal = SecurityUtils.getSubject().getPrincipal();

        return principal != null ? principal.toString() : null;
    }
}
