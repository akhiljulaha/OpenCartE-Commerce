package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic 100: Login Page Design")
@Story("US 101: design login page for open cart app with title, url, forgot pws links, user is able to login ")
public class LoginPageTest extends BaseTest {


	@Severity(SeverityLevel.MINOR)
	@Description("Checking login page title test....")
	@Feature("title test")
	@Test
	public void loginPageTitleTest() {
		String actTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE);

	}
	@Severity(SeverityLevel.NORMAL)
	@Description("Checking login page url test....")   // optional
	@Feature("url test")
	@Test
	public void loginPageUrlTest() {
		String actURL = loginPage.getLoginPageURL();
		Assert.assertTrue(actURL.contains(AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE));
	}
	@Severity(SeverityLevel.CRITICAL)
	@Description("Checking forgot pwd link test test....")
	@Feature("forgot pwd test")
	@Test
	public void forgotPwdLinkExist() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());
	}
	@Severity(SeverityLevel.BLOCKER)
	@Description("Checking user is able to login with correct username/password test.....")
	@Feature("login test")
	@Test
	public void loginTest() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertTrue(accPage.isLogoutLinkExist());
		Assert.assertTrue(accPage.getAccPageTitle().equals(AppConstants.ACCOUNTS_PAGE_TITLE));
	}

}
