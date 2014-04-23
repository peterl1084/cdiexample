package org.vaadin.example.backend.authentication;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.web.env.DefaultWebEnvironment;
import org.apache.shiro.web.env.EnvironmentLoaderListener;
import org.apache.shiro.web.env.WebEnvironment;

public class CDIAwareShiroEnvironmentLoader extends EnvironmentLoaderListener {

    private final static String HASHING_ALGORITHM = "SHA512";

    @Inject
    private JPARealm jpaRealm;

    @Override
    protected WebEnvironment createEnvironment(ServletContext sc) {
        WebEnvironment webEnvironment = super.createEnvironment(sc);

        RealmSecurityManager rsm = (RealmSecurityManager) webEnvironment
                .getSecurityManager();

        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher(
                HASHING_ALGORITHM);
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);

        jpaRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        rsm.setRealm(jpaRealm);

        ((DefaultWebEnvironment) webEnvironment).setSecurityManager(rsm);
        return webEnvironment;
    }
}
