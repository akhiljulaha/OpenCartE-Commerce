package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class ProductPageInfoTest extends BaseTest{


	@BeforeClass    
	public void searchSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test
	public void productInfoTest() {
		resultsPage = accPage.doSearch("Macbook");
		productInfoPage = resultsPage.selectProduct("MacBook Pro");
		Map<String, String> productInfoMap = productInfoPage.getProductInfo();   // hasmap don't have any order
		System.out.println(productInfoMap);
		
		softAssert.assertEquals(productInfoMap.get("Brand"), "Apple");
		softAssert.assertEquals(productInfoMap.get("Availability"), "In Stock11");
		softAssert.assertEquals(productInfoMap.get("productname"), "MacBook Pro00");
		softAssert.assertEquals(productInfoMap.get("productprice"), "$2,000.00");
		softAssert.assertEquals(productInfoMap.get("Product Code"), "Product 18");
		softAssert.assertAll();        // Imp to write, if not write this it will pass your test case even it should fail 
	
	}

}
/**
* In Hard Assert, if an assertion fails, the test execution stops, and the remaining assertions in the method are not executed.  
* In Soft Assert, even if one assertion fails, the remaining assertions in the method will still be executed.  
* The Assert methods are static in nature so we calling it directly
* SoftAssert methods are non-static, requiring an instance(Object) of SoftAssert to be created.  
* In SoftAssert, it is mandatory to call `softAssert.assertAll();` at the end; this will collect and report all assertion failures at once.  
* SoftAssert is a class in TestNG, so we create an object in the BaseTest and use its reference in test methods.  
*/
//{Brand=Apple, Availability=In Stock, Product Code=Product 18, productname=MacBook Pro, extraprice=$2,000.00, Reward Points=800, productprice=$2,000.00}   >> hashMap does't not maintain a data in a order(Random order)
//{Brand=Apple, Product Code=Product 18, Reward Points=800, Availability=In Stock, productprice=$2,000.00, extraprice=$2,000.00, productname=MacBook Pro}   > LinkedHashMap will maintain the order
//{Availability=In Stock, Brand=Apple, Product Code=Product 18, Reward Points=800, extraprice=$2,000.00, productname=MacBook Pro, productprice=$2,000.00}   >TreeMap will maintain the order and sorted also
// NOTE: we can use the arrayList also but it will not give the data in form of key and value pair
