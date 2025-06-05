package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.ResultsPage;

public class BaseTest {
	WebDriver driver;
	protected LoginPage loginPage;
	protected AccountsPage accPage;
	protected ResultsPage resultsPage;
	protected ProductInfoPage productInfoPage;
	DriverFactory df;
	protected Properties prop;   // only the child class can access the property whether within a classes or outside clases
	protected SoftAssert softAssert;
	protected RegisterPage registerPage;
	
	@Parameters({"browser"})
	@BeforeTest
	public void setup(String browserName) {
		df = new DriverFactory();
		prop = df.initProp();	
		
		if(browserName!=null) {
			prop.setProperty("browser", browserName);
		}		
		driver = df.initDriver(prop);
		loginPage = new LoginPage(driver);   // we can't do this because login Page don't has to login 
		softAssert = new SoftAssert();
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
