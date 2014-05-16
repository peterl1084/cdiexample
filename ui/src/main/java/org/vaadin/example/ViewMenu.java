/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vaadin.example;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

import org.vaadin.example.login.LoginView;
import org.vaadin.example.login.LoginViewImpl;
import org.vaadin.maddon.button.MButton;
import org.vaadin.maddon.label.Header;
import org.vaadin.maddon.layouts.MHorizontalLayout;
import org.vaadin.maddon.layouts.MVerticalLayout;

/**
 * A helper to automatically create a menu from available Vaadin CDI view.
 * 
 * You'll probably want something more sophisticated in your app, but this might 
 * be handy prototyping small CRUD apps.
 * 
 * @author Matti Tahvonen <matti@vaadin.com>
 */
@SessionScoped
public class ViewMenu implements Serializable {
    
    @Inject
    BeanManager beanManager;
    
    public Set<Bean<?>> getAvailableViews() {
        Set<Bean<?>> all = beanManager.getBeans(View.class,
                new AnnotationLiteral<Any>() {
                });
        // TODO check if accessible for current user
        return all;
    }
    
    public Component getBasicMenu() {
    	return new MVerticalLayout(
    			new Header("Navigation").setHeaderLevel(3))
    				.with(getAsLinkButtons(getAvailableViews())).withWidth("200px").withSpacing(false);
    }
    
    
    private Set<Class<?>> hiddenViews = new HashSet<Class<?>>();
    {
    	hiddenViews.add(LoginViewImpl.class);
    }
    
    private Component[] getAsLinkButtons(
            Set<Bean<?>> availableViews) {
        ArrayList<Button> buttons = new ArrayList<>();
        for (Bean<?> viewBean : availableViews) {
        	
        	
            Class<?> beanClass = viewBean.getBeanClass();
            if (beanClass.getAnnotation(CDIView.class) != null 
            		&& !hiddenViews.contains(beanClass)) {
                buttons.add(getButtonFor(beanClass));
            }
        }
        return buttons.toArray(new Button[0]);
    }
    
    private MButton getButtonFor(final Class<?> beanClass) {
        final MButton button = new MButton(getNameFor(beanClass)).withStyleName(
                "link");
        button.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
	            CDIView cdiview = beanClass.getAnnotation(CDIView.class);
	            UI.getCurrent().getNavigator().navigateTo(cdiview.value());
			}
        });
        return button;
    }
    
    protected String getNameFor(Class<?> viewType) {
//    	if (viewType instanceof ApplicationView) {
//			ApplicationView av = (ApplicationView) viewType;
//			return av.getName();
//			
//		}
        return viewType.getSimpleName();
    }
}