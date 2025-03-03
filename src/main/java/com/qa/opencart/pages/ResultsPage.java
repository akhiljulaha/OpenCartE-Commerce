package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.ElementUtil;

public class ResultsPage {
	private WebDriver driver;
	private ElementUtil eleUtil;

	private By resultsProduct = By.xpath("//div[@class='product-layout product-grid col-lg-3 col-md-3 col-sm-6 col-xs-12']");

	public ResultsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	public String getResultPageTitle(String searchKey) {
		return eleUtil.waitForTitleIsAndCapture(searchKey, 5);
	}

	public int getProductResultsCount() {
		int resultCount = eleUtil.waitForElementsVisible(resultsProduct, 5).size();
		System.out.println("Product search result count =====> " + resultCount);
		return resultCount;
	}

	public ProductInfoPage selectProduct(String productName) {
// this is dynamic loactor that's why we not writing above with all private locators, even if you write there is no way to pass the productName value
		By productNameLocator = By.linkText(productName);
		eleUtil.doClick(productNameLocator);
		return new ProductInfoPage(driver);

	}


}
