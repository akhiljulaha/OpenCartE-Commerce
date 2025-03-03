package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class AccountsPage {
	private WebDriver driver; 
	private ElementUtil eleUtil;

	public AccountsPage(WebDriver driver) { 
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	private By logout = By.linkText("Logout"); 
	private By myAccount = By.linkText("My Account");
	private By accHeaders = By.xpath("//div[@id='content']/h2");
	private By search = By.name("search");
	private By searchIcon = By.xpath("//div[@id='search']//button");
	
	public String getAccPageTitle() {
		return eleUtil.waitForTitleIsAndCapture(AppConstants.ACCOUNTS_PAGE_TITLE, AppConstants.SHORT_DEFAULT_WAIT);
	}
	public boolean isLogoutLinkExist() {
		return eleUtil.checkElementIsDisplayed(logout);
	}
	public boolean isMyAccountLinkExist() {
		return eleUtil.checkElementIsDisplayed(myAccount);
	}
	public List<String> getAccountPageList() {
	    List<WebElement> headersList = eleUtil.waitForElementsVisible(accHeaders, AppConstants.MEDIUM_DEFAULT_WAIT);
	    List<String> headersValList = new ArrayList<String>();
	    for(WebElement e : headersList) {
	    	String text = e.getText();
	    	headersValList.add(text);
	    }
	    return headersValList;
	}
	
	public ResultsPage doSearch(String searchTerm) {
		eleUtil.waitForElementVisible(search, AppConstants.MEDIUM_DEFAULT_WAIT);
		eleUtil.doSendKeys(search, searchTerm);
		eleUtil.doClick(searchIcon);	
		return new ResultsPage(driver);  //IMP: need to return the driver then with the help of the same driver will access the properties of the search page 

	}

}
