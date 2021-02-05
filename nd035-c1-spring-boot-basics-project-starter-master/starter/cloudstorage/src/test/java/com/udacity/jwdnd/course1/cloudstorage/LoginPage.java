package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id="inputUsername")
    private WebElement inputUsername;

    @FindBy(id="inputPassword")
    private WebElement inputPassword;

    @FindBy(id="btnLoginSubmit")
    private WebElement btnLoginSubmit;

    @FindBy(id="btnLoginSignupLink")
    private WebElement btnLoginSignupLink;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void login(String username,
                                    String password) throws InterruptedException {
        Thread.sleep(1000);
        this.inputUsername.sendKeys(username);
        this.inputPassword.sendKeys(password);
        this.btnLoginSubmit.click();
    }
}
