package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {
	private WebDriver driver;
	private ElementUtil eleUtil;

	private By productHeader = By.xpath("//div[@id='content']//h1");
	private By productImages = By.xpath("//ul[@class='thumbnails']//img");
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class=\"list-unstyled\"])[1]/li");
	private By productPriceData = By.xpath("(//div[@id='content']//ul[@class=\"list-unstyled\"])[2]/li");
	private By quantity = By.id("input-quantity");
	private By addToCartBtn = By.id("button-cart");
	private Map<String, String> productInfoMap;

	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	public String getProductHeaderName() {
		return eleUtil.doGetElemntText(productHeader);
	}
	
	public int getProductImagesCount() {
		return eleUtil.waitForElementsVisible(productImages, AppConstants.MEDIUM_DEFAULT_WAIT).size();
	}
	
	public Map<String, String> getProductInfo() {
//		productInfoMap = new HashMap<String, String>();      // Does't not maintain the order 
//		productInfoMap = new LinkedHashMap<String, String>();  // it will maintain the order
		productInfoMap = new TreeMap<String, String>();    // it will maintain the order and sorted also(capital letters > small leters > numeric key)
		getProductMetaData();
		getProductPriceData();
		productInfoMap.put("productname", getProductHeaderName());// want to print the product name along with meta data and price
		return productInfoMap;
	}
	
//	Brand: Apple
//	Product Code: Product 17
//	Reward Points: 700
//	Availability: Out Of Stock
	private void getProductMetaData() {
		List<WebElement> metaList = eleUtil.getElements(productMetaData);
		for(WebElement e : metaList) {
			String metaText = e.getText();
			String metaInfo[] = metaText.split(":");
			String key = metaInfo[0].trim();
			String value = metaInfo[1].trim();
			productInfoMap.put(key, value);
		}
	}
	
//	$2,000.00
//	Ex Tax: $2,000.00
	private void getProductPriceData() {
		List<WebElement> priceList = eleUtil.getElements(productPriceData);    // 
		String priceValue = priceList.get(0).getText();
		String exTaxPrice = priceList.get(1).getText();
		String exTaxPriceValue = exTaxPrice.split(":")[1].trim();
		productInfoMap.put("productprice", priceValue);  // if the key is not avialable then we can over custom key also
		productInfoMap.put("extraprice", exTaxPriceValue);
		}
}



//{Brand=Apple, Availability=In Stock, Product Code=Product 18, productname=MacBook Pro, extraprice=$2,000.00, Reward Points=800, productprice=$2,000.00}   >> hash does't not maintain a data in a order

	
	


