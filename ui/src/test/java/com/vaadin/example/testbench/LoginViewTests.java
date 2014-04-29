package com.vaadin.example.testbench;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.vaadin.testbench.TestBench;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.PasswordFieldElement;
import com.vaadin.testbench.elements.TextFieldElement;

public class LoginViewTests extends TestBenchTestCase {

    private static final String LOCALHOST = "http://localhost:8080";

    @Before
    public void initialize() {
        setDriver(TestBench.createDriver(new FirefoxDriver()));
        getDriver().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
    }

    @Test
    public void testLoginAttempt_invalidCrendetials_notificationShown() {
        getDriver().get(concatUrl(LOCALHOST, "/ui"));

        TextFieldElement username = $(TextFieldElement.class).first();

        username.clear();
        username.sendKeys("wrong username");

        ButtonElement loginButton = $(ButtonElement.class).caption("Login")
                .first();

        loginButton.click();

        Assert.assertEquals("Login failed", getNotificationCaption());
        Assert.assertEquals("Invalid credentials", getNotificationContent());
    }

    @Test
    public void testLoginAttempt_correctCredentials_loggedIn() {
        getDriver().get(concatUrl(LOCALHOST, "/ui"));

        TextFieldElement username = $(TextFieldElement.class).first();
        PasswordFieldElement password = $(PasswordFieldElement.class).first();

        username.clear();
        username.sendKeys("admin");
        password.clear();
        password.sendKeys("password");

        ButtonElement loginButton = $(ButtonElement.class).caption("Login")
                .first();

        loginButton.click();

        Assert.assertEquals("Welcome back admin", getNotificationCaption());
    }

    private String getNotificationCaption() {
        return getDriver().findElement(By.cssSelector(".v-Notification"))
                .findElement(By.xpath("./div/div/h1")).getText();
    }

    private String getNotificationContent() {
        return getDriver().findElement(By.cssSelector(".v-Notification"))
                .findElement(By.xpath("./div/div/p")).getText();
    }

    @After
    public void tearDown() {
        getDriver().close();
    }
}
