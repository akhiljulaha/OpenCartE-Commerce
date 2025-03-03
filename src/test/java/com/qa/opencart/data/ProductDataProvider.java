package com.qa.opencart.data;

import org.testng.annotations.DataProvider;

import com.qa.opencart.pojo.Product;

public class ProductDataProvider {
	
	
	@DataProvider(name = "productData")
	public Object[][]getProductTestData() {
		return new Object[][] {
			{new Product("Macbook","MacBook Pro",4)}, // calling the contructor of the product class
			{new Product("iMac","iMac",3)},   // this is complete data in the form of object 
			{new Product("Samsung", "Samsung SyncMaster 941BW",1)},
			{new Product ("Samsung", "Samsung Galaxy Tab 10.1",7)}
		};

	}

}
