package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class SearchPageTest extends BaseTest {

	@BeforeClass    
	public void searchSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	@DataProvider
	public Object[][] getProductSearchKeyData() {
		return new Object[][] {
			{"Macbook"},                   // if any one  will fail then it will check the rest of test cases
			{"iMac"},
			{"Samsung"}
		};		
	}
	@Test(dataProvider = "getProductSearchKeyData")
	public void searchProductTest(String searchKey) {
		resultsPage = accPage.doSearch(searchKey);
		Assert.assertTrue(resultsPage.getProductResultsCount()>0);
	}
	
	@Test(dataProvider = "getProductSearchKeyData")
	public void searchPageTitleTest(String searchKey) {
		resultsPage = accPage.doSearch(searchKey);
		String actSearchTitle = resultsPage.getResultPageTitle(searchKey);
		System.out.println("Search Page Title : "+ actSearchTitle);
		Assert.assertEquals(actSearchTitle, "Search - "+searchKey);		
	}
	
	@DataProvider
	public Object[][] getProductTestData() {
		return new Object[][] {
			{"Macbook","MacBook Pro"},                   
			{"iMac","iMac"},
			{"Samsung", "Samsung SyncMaster 941BW"},
			{"Samsung", "Samsung Galaxy Tab 10.1"},

		};		
	}
	
	@Test(dataProvider ="getProductTestData")
	public void selectProducTitle(String searchKey, String productName) {
		resultsPage = accPage.doSearch(searchKey);
		productInfoPage = resultsPage.selectProduct(productName);	
		String actProductHeaderName = productInfoPage.getProductHeaderName();
		System.out.println("actual product name"+ actProductHeaderName);
		Assert.assertEquals(actProductHeaderName, productName);	
	}
	
	
	@DataProvider
	public Object[][] getProductImagesTestData() {
		return new Object[][] {
			{"Macbook","MacBook Pro",4},                   
			{"iMac","iMac",3},
			{"Samsung", "Samsung SyncMaster 941BW",1},
			{"Samsung", "Samsung Galaxy Tab 10.1",7},

		};	
	}
	@Test(dataProvider = "getProductImagesTestData")      // this one we can shift to the productPageTest
	public void getProductImagesTestData(String searchKey, String productName, int expImagesCount) {
		resultsPage = accPage.doSearch(searchKey);
		productInfoPage = resultsPage.selectProduct(productName);	
		int actProductImagesCount = productInfoPage.getProductImagesCount();
		System.out.println("actual product images count: "+actProductImagesCount);
		Assert.assertEquals(actProductImagesCount, expImagesCount);
		
	}
	

}
