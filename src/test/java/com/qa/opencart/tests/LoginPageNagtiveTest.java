package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class LoginPageNagtiveTest extends BaseTest {
	
	
	@DataProvider
	public Object[][] incorrectLoginTestData(){
		return new Object[][] {
			{"aj1234@gmail.com", "12344"},
			{"test@@gmail.com", "y67777"},
			{"auto", "test"},
			{"",""}
		};	
	}
	
	
	@Test(dataProvider = "incorrectLoginTestData")
	public void loginWithWrongCredentialsTest(String userName, String password) {
		Assert.assertTrue(loginPage.doLoginWithWrongCredentials(userName, password));
	}

}
