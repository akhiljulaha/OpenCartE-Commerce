package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.ls.LSOutput;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {
	private WebDriver driver; 
    private ElementUtil eleUtil;   // IMP===> how taking the elementUtil properties
	public LoginPage(WebDriver driver) { // 2
		this.driver = driver;
		eleUtil = new ElementUtil(this.driver);  // this. using the global driver
	}

	private By emailId = By.id("input-email"); // 3
	private By password = By.id("input-password");
	private By loginButton = By.xpath("//input[@value='Login']");
	private By forgotPWdLink = By.linkText("Forgotten Password");
	private By footerLinks = By.xpath("//footer//a");
	private By loginErrorMessg = By.xpath("//div[@class='alert alert-danger alert-dismissible']");
	private By registerlink = By.linkText("Register");

	
// Public Page Actions/Method -> what exactly you want to do on the particular page-
	@Step("getting login page title.")
	public String getLoginPageTitle() {
		return eleUtil.waitForTitleIsAndCapture(AppConstants.LOGIN_PAGE_TITLE, AppConstants.SHORT_DEFAULT_WAIT);
	}
	@Step("getting login page url")
	public String getLoginPageURL() {
		return eleUtil.waitForURLContainsAndCapture(AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE, AppConstants.SHORT_DEFAULT_WAIT);
		
	}
	@Step("Check-- the login page")
	public boolean isForgotPwdLinkExist() {
		return eleUtil.checkElementIsDisplayed(forgotPWdLink);
	}
	@Step("getting footer links")
	public List<String> getFooterLinksList() {
		List<WebElement> footerLinksList = eleUtil.waitForElementsVisible(footerLinks, AppConstants.MEDIUM_DEFAULT_WAIT);
		List<String> footerTextList = new ArrayList<String>();
		for (WebElement e : footerLinksList) {
			String text = e.getText();
			footerTextList.add(text);
		}
		return footerTextList;
	}
	@Step("Login with username {0} and password {1} ")
	public AccountsPage doLogin(String userName, String pwd) {
		System.out.println("correct creds are : "+ userName + " "+ pwd);
		eleUtil.waitForElementVisible(emailId, AppConstants.MEDIUM_DEFAULT_WAIT);
		eleUtil.doSendKeys(emailId, userName);		
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginButton);
		return new AccountsPage(driver); //Page chaining model
//IMP: need to return the driver then with the help of the same driver will access the properties of the account page 
	}
	
	@Step("Login with wrong username {0} and password {1} ")
	public boolean doLoginWithWrongCredentials(String userName, String pwd) {
		System.out.println("Wrong creds are : "+ userName + " "+ pwd);
		eleUtil.waitForElementVisible(emailId, AppConstants.MEDIUM_DEFAULT_WAIT);   // don't use send key here if using the dataDriven approach, causing the field clear issue 
		eleUtil.doSendKeys(emailId, userName);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginButton);
		String errorMessage = eleUtil.doGetElemntText(loginErrorMessg);
		System.out.println(errorMessage);
		if(errorMessage.contains("Warning: No match for E-Mail Address and/or Password.")) {
			return true;
		}else {
			return false;
		}
	}
	
	public RegisterPage navigateToRegisterPage() {
		eleUtil.doClick(registerlink);
		return new RegisterPage(driver);
	}

}
