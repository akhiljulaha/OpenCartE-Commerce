package com.qa.opencart.tests;

import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.AppConstants;

public class RegisterPageTest extends BaseTest {

	@BeforeClass
	public void regSetup() {
		registerPage = loginPage.navigateToRegisterPage();
	}

	public String getRandomEmailId() {
		return "testautomation" + System.currentTimeMillis() + "@gmail.com";
//		return "testautomation" + UUID.randomUUID() + "@gmail.com";
		
	}
	
	@DataProvider(name="regData")
		public Object[][] getUserRegTestData() {
			return new Object[][] {
				{"abhi","anand","1234567890","abhi6098", "yes"},   // once 1st is register then we have to logout then only 2nd will work
				{"abhiii","anandiii","1234567899","abhi9629", "no"},
				{"abhiuuuu","anand000","1234567882","abhi7633", "yes"},
			};			
	}
	
	@Test(dataProvider = "regData")
	public void userRegisterTest(String firstName,String lastName,String telephone, String password, String subscribe) {
		String actRegSuccMessg = registerPage.registeruser(firstName, lastName, getRandomEmailId(), telephone,
				password,subscribe);
		Assert.assertEquals(actRegSuccMessg, AppConstants.USER_RESG_SUCCESS_MESSG);
	}
	
	
	
	
	
	
	
	// Use the below one if don't want to use the dataprovider or want to run only 1 time 
//	@Test
//	public void userRegisterTest() {
//		String actRegSuccMessg = registerPage.registeruser("Abhi", "kumar", getRandomEmailId(), "99911199811",
//				"abhi@123", "yes");
//		Assert.assertEquals(actRegSuccMessg, AppConstants.USER_RESG_SUCCESS_MESSG);
//	}
}
