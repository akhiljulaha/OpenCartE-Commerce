package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.data.ProductDataProvider;
import com.qa.opencart.pojo.Product;

public class SearchDataTest extends BaseTest {

	@BeforeClass
	public void searchSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}


	@Test(dataProvider = "productData",dataProviderClass=ProductDataProvider.class)
	public void searchProductTest(Product product) {   //storing inside thr product referance variable
		resultsPage = accPage.doSearch(product.getSearchKey());
		Assert.assertTrue(resultsPage.getProductResultsCount() > 0);
	}

	@Test(dataProvider = "productData",dataProviderClass=ProductDataProvider.class)
	public void searchPageTitleTest(Product product) {
		resultsPage = accPage.doSearch(product.getSearchKey());
		String actSearchTitle = resultsPage.getResultPageTitle(product.getSearchKey());
		System.out.println("Search Page Title : " + actSearchTitle);
		Assert.assertEquals(actSearchTitle, "Search - " + product.getSearchKey());
	}

	@Test(dataProvider = "productData",dataProviderClass=ProductDataProvider.class)
	public void selectProducTitle(Product product) {
		resultsPage = accPage.doSearch(product.getSearchKey());
		productInfoPage = resultsPage.selectProduct(product.getProductName());
		String actProductHeaderName = productInfoPage.getProductHeaderName();
		System.out.println("actual product name" + actProductHeaderName);
		Assert.assertEquals(actProductHeaderName, product.getProductName());
	}

	@Test(dataProvider = "productData",dataProviderClass=ProductDataProvider.class)
	public void getProductImagesTestData(Product product) {
		resultsPage = accPage.doSearch(product.getSearchKey());
		productInfoPage = resultsPage.selectProduct(product.getProductName());
		int actProductImagesCount = productInfoPage.getProductImagesCount();
		System.out.println("actual product images count: " + actProductImagesCount);
		Assert.assertEquals(actProductImagesCount, product.getProductImages());

	}

}
