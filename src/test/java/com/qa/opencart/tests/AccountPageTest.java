package com.qa.opencart.tests;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.utils.AppConstants;

public class AccountPageTest extends BaseTest {


	@BeforeClass     // it will exececute after the baseTest
	public void accPageSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test
	public void accPageTitleTest() {
		String actTitle = accPage.getAccPageTitle();
		Assert.assertEquals(actTitle, AppConstants.ACCOUNTS_PAGE_TITLE);
	}
	@Test
	public void isLogoutLinkExistTest() {
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}
	@Test
	public void isMyAccLinkExistTest() {
		Assert.assertTrue(accPage.isMyAccountLinkExist());
	}
	@Test
	public void accPageHeadersCountTest() {
		List<String> actAccHeadersList = accPage.getAccountPageList();	
		Assert.assertEquals(actAccHeadersList.size(), 4);
	}
	@Test
	public void accPageHeadersTest() {
		List<String> actAccHeadersList = accPage.getAccountPageList();     // sorted and then compare is beter choice
//		List<String> expAccHeadersList = Arrays.asList("My Account", "My Orders","My Affiliate Account","Newsletter");
		Assert.assertEquals(actAccHeadersList, AppConstants.EXP_ACOUNTS_HEADERS_LIST);
	}
	
}
