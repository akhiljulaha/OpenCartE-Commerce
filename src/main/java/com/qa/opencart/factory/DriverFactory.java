package com.qa.opencart.factory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.qa.opencart.frameworkexception.FrameworkException;

public class DriverFactory {
	WebDriver driver;
	OptionsManager optionsManager;
	 public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();   // Thread local object    1

	public WebDriver initDriver(Properties prop) {//Instead of directly passing values like browser name or incognito mode, it's a good practice to pass a reference variable and use only the required configuration properties from it.

		String browserName = prop.getProperty("browser").trim();
		System.out.println("Browser name is : " + browserName);
		optionsManager = new OptionsManager(prop);   // passing prop because with the help of the this we will read the cofiq file from the optionsManager class

		switch (browserName.toLowerCase()) {
		case "chrome":
			//driver = new ChromeDriver(optionsManager.getChromeOptions());
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));   //2
			break;
		case "edge":
//			driver = new EdgeDriver(optionsManager.getEdgeOptions());
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			break;
		case "firefox":
//			driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			break;
		default:
			System.out.println("Please pass the right browser....." + browserName);
			throw new FrameworkException("NOBROWSERFOUNDEXCEPTION");
		}
		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		getDriver().get(prop.getProperty("url"));
		return getDriver();

	}
//IMP: It will return the thread local copy of the driver, not need to chanage anything in the baseTest
	public synchronized static WebDriver getDriver() {  // synchronized not mandatory to write
		return tlDriver.get();
	}
	public Properties initProp() {
		Properties prop = new Properties();
		try {
			FileInputStream ip = new FileInputStream("./src/main/resources/config/config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
	/**
	 * take screenshot
	 */
	public static String getScreenshot() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);
		try {
			FileUtils.copyFile(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
}
