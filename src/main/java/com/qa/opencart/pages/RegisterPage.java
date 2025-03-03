package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class RegisterPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By confirmPassword = By.id("input-confirm");
	private By agreeCheckBox = By.name("agree");
	private By continueButton = By.xpath("//input[@type='submit' and @value='Continue']");
	private By subscribeYes = By.xpath("(//label[@class='radio-inline'])[1]/input");
	private By subscribeNo = By.xpath("(//label[@class='radio-inline'])[2]/input");

	private By userRegSuccMessg = By.xpath("//div[@id='content']/h1");
	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");
	
	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(this.driver);
	}
	public String registeruser(String firstName, String lastName, String email, String telephone, String password,
			String subscribe) {
		eleUtil.waitForElementVisible(this.firstName, AppConstants.MEDIUM_DEFAULT_WAIT); // use this here to fetch the locator otherwise it will pick string value
		eleUtil.doSendKeys(this.firstName, firstName);
		eleUtil.doSendKeys(this.lastName, lastName);
		eleUtil.doSendKeys(this.email, email);
		eleUtil.doSendKeys(this.telephone, telephone);
		eleUtil.doSendKeys(this.password, password);
		eleUtil.doSendKeys(this.confirmPassword, password);
		doSubscribe(subscribe);
		eleUtil.doClick(agreeCheckBox);
		eleUtil.doClick(continueButton);
		String userRegSuccessMesg = eleUtil.waitForElementVisible(userRegSuccMessg, AppConstants.MEDIUM_DEFAULT_WAIT).getText(); // we can combine 2 pages also but make sure for small text like here we doing
		System.out.println(userRegSuccessMesg);		
		eleUtil.doClick(logoutLink);   // once 1st is register then we have to logout then only 2nd will work
		eleUtil.doClick(registerLink);  // once it logout again you have to click register
		return userRegSuccessMesg;
		
		
}
	private void doSubscribe(String subscribe) {

		if (subscribe.equalsIgnoreCase("Yes")) {
			eleUtil.doClick(subscribeYes);
		} else {
			eleUtil.doClick(subscribeNo);
		}
	}
}

	
