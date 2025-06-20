package com.qa.opencart.factory;

import java.util.Properties;

import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager {

	private Properties prop;  // creting the property ref. only, not intializing the object again
	private ChromeOptions co;
	private FirefoxOptions fo;
	private EdgeOptions eo;
	
	public OptionsManager(Properties prop) {
		this.prop = prop;
	}
	
	public ChromeOptions getChromeOptions() {
		co = new ChromeOptions();
		if(Boolean.parseBoolean(prop.getProperty("headless"))) {
			co.addArguments("--headless");
		}
		if(Boolean.parseBoolean(prop.getProperty("incognito"))) {
			co.addArguments("--incognito");
		}
		
		if(Boolean.parseBoolean(prop.getProperty("remote"))) {
			co.setCapability("browserName", "chrome");
		}
		return co;
	}
	public FirefoxOptions getFirefoxOptions() {
		fo = new FirefoxOptions();
		if(Boolean.parseBoolean(prop.getProperty("headless"))) {
			fo.addArguments("--headless");
		}
		if(Boolean.parseBoolean(prop.getProperty("incognito"))) {
			fo.addArguments("--incognito");
		}
// if both are false then it will not have headless or incognito mode, it will just pass the blank co
//		co.addArguments("--window-size=1920,1080");             
//		co.addArguments("--no-sandbox");
//		co.addArguments("--disable-gpu");
//		co.addArguments("--disable-crash-reporter");
//		co.addArguments("--disable-extensions");
//		co.addArguments("--disable-in-process-stack-traces");
//		co.addArguments("--disable-logging");
//		co.addArguments("--disable-dev-shm-usage");
//		co.addArguments("--log-level=3");
//		co.addArguments("--output=/dev/null");
		return fo;
	}
	public EdgeOptions getEdgeOptions() {
		eo = new EdgeOptions();
		if(Boolean.parseBoolean(prop.getProperty("headless"))) {
			eo.addArguments("--headless");
		}
		if(Boolean.parseBoolean(prop.getProperty("incognito"))) {
			eo.addArguments("--incognito");
		}
		return eo;
	}
	
	
}
