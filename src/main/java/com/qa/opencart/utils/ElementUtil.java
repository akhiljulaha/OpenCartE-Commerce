package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.By.ByLinkText;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.frameworkexception.FrameworkException;

public class ElementUtil {

	private WebDriver driver; 
	private final int DEFAULT_TIME_OUT = 5;

	public ElementUtil(WebDriver driver) { // 123
		this.driver = driver; // 123
	}

	public void doSendKeys(By locator, String value) {
		if (value == null) {
			System.out.println("null values are not allowed");
			throw new FrameworkException("VALUECANNOTBENULL");
		}
		doClear(locator); // the best practice is to clear() before entering the value
		getElement(locator).sendKeys(value);
	}

	public void doClick(By loactor) {
		getElement(loactor).click();
	}
	public void doClick(By loactor, int timeOut) {
		checkElementClickable(loactor, timeOut).click();
	}

	public WebElement getElement(By locator, int timeOut) {
		return waitForElementVisible(locator, timeOut);
	}

	/*
	 * why Method Overloading? ***** IMPORTANT ***** - GOOD Example for the method overloading
	 *  Use the above method to set a specific wait
	 *  Use the below method when no timeout is passed; it applies a default wait (5 sec) only If the element is
	 *   not found initially.
	 *   **********IMPORTANT POINT TO REMEMBER FOR THE FRAMEWORK**********
	 *   Always we need to override the getElement method because  If not overridden, every call to `getElement()`
	 *    applies a wait, making it function like an implicit wait. then what is the differance between Implicit and Explicitly
	 *  Separating implicit and explicit waits is a **best practice** to avoid conflicts.   
	 */
	public WebElement getElement(By locator) {
		WebElement element = null; // good practice is to set the default value
		try {
			element = driver.findElement(locator);
		} catch (NoSuchElementException e) {
			System.out.println("Element is not found using this locator..." + locator);
			element = waitForElementVisible(locator, DEFAULT_TIME_OUT);
		}
		return element;
	}

	public void doClear(By locator) {
		getElement(locator).clear();
	}

	public String doGetElemntText(By locator) {
		return getElement(locator).getText();
	}

	public boolean checkElementIsDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}

	public String doGetAttributeValue(By locator, String attrName) {
		return getElement(locator).getAttribute(attrName);
	}

	public int getElemntsCount(By locator) {
		return getElements(locator).size();
	}

// on the basis of attrName, giving the list of the attributes
	public List<String> getElementAttributeValue(By locator, String attrName) {
		List<WebElement> eleList = getElements(locator);
		List<String> eleAttrList = new ArrayList<String>();
		for (WebElement e : eleList) {
			String attrValue = e.getAttribute(attrName);
			eleAttrList.add(attrValue);
		}
		return eleAttrList;
	}
// giving the list of the text

	public List<String> getElementsTextList(By locator) {
		List<String> eleAttrList = new ArrayList<String>();
		List<WebElement> ElementsLinksList = getElements(locator);
		for (WebElement e : ElementsLinksList) {
			String text = e.getText();
			eleAttrList.add(text);
		}
		return eleAttrList;
	}

	public void clickElementFromPageSection(By locator, String eletext) {
		List<WebElement> eleList = getElements(locator);
		for (WebElement e : eleList) {
			String text = e.getText();
			System.out.println(text);
			if (text.equals(eletext)) {
				e.click();
				break;
			}
		}

	}
	// .isDisplayed() > if don't want to use then we can use the below approach -IMP
	// for the interview
// this will work for the single element and as well as multiple elements(driver.findElements works even if the single element is there)

	public boolean isElementDisplayed(By locator) {
		List<WebElement> eleList = getElements(locator); // 1 change
		if (eleList.size() > 0) {
			System.out.println(locator + "Element is present on the page");
			return true;
		} else {
			return false;
		}
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public void search(String searchKey, By searchLocator, String clickSuggName, By suggestions)
			throws InterruptedException {
		doSendKeys(searchLocator, searchKey); // 1 change
		Thread.sleep(2000);
		List<WebElement> suggList = getElements(suggestions); // 2 change
		System.out.println("Total suggestions : " + suggList.size());
		if (suggList.size() > 0) { // it will captured the blank value also
			for (WebElement e : suggList) {
				String text = e.getText();
				if (text.length() > 0) {
					System.out.println(text);
					if (text.contains(clickSuggName)) {
						e.click();
						break;
					}
				} else {
					System.out.println("Blank value -- no suggestions");
					break;
				}
			}
		} else {
			System.out.println("No Search Suggestions Found");
		}

	}

// ********** Drop Down Utils(Only works when the select tag is avaialble) ********************
	public void doSelectDropDownByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}

	public void doSelectDropDownByVisibleText(By locator, String text) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(text);
	}

	public void doSelectDropDownByValueAttribute(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}

	/*
	 * the above utilty will help to selct the particlar drop-down value but if want
	 * to print all drop-down values in the console and want a countthen need to use
	 * the getOption() method
	 */
	public int getDropDownValueCount(By locator) {
		return getAllDropDownOptions(locator).size();
	}

	public List<String> getAllDropDownOptions(By locator) { // fetching value and returning or printing
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();
		List<String> optionsValueList = new ArrayList<String>();
		System.out.println("Total country : " + optionsList.size());
		for (WebElement e : optionsList) {
			String text = e.getText();
//			System.out.println(text);       // we have to validate this list also
			optionsValueList.add(text); // full of list
		}
		return optionsValueList;
	}

	/*
	 * if the drop-down value is not in the list then we need to handle that case
	 * also we will maintain boolean flag
	 * 
	 * selecting the drop-down value with the help of select method
	 */
	public boolean doSelectDropDownValue(By locator, String dropDownvalue) {
		boolean flag = false; // bydefault it will be false
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions(); // It will return all options tag under your path
		System.out.println("Total values : " + optionsList.size());
		for (WebElement e : optionsList) {
			String text = e.getText();
			if (text.equals(dropDownvalue)) {
				flag = true; // if the match happen then we will set the true
				e.click();
				break;
			} // if using else here and country name is not present then it will print 233 or
				// 234 times
		}
		if (flag == false) {
			System.out.println(dropDownvalue + " is not present in the drop-down" + locator);
		}
		return flag;
	}
//	  selecting the drop-down value without the help of select method

	public boolean DoselectValueFromDropDownwithoutSelect(By locator, String value) {
		boolean flag = false;
		List<WebElement> optionList = getElements(locator);
		for (WebElement e : optionList) {
			String text = e.getText();
			if (text.equals(value)) {
				flag = true;
				e.click();
				break;
			}
		}
		if (flag == false) {
			System.out.println(value + " is not present in the drop-down" + locator);
		}
		return flag;
	}

// ********************** Action class utils ************************************

	public void doDragAndDrop(By sourceLocator, By targetLocator) {
		Actions act = new Actions(driver);
		act.dragAndDrop(getElement(sourceLocator), getElement(targetLocator)).build().perform();
	}

	public void doContextClick(By locator) {
		Actions act = new Actions(driver);
		act.contextClick(getElement(locator)).build().perform();
		;
	}
	// only Move to Element concept

	public void doMoveToElement(By locator) {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(locator)).build().perform();
		;
	}

	// Move to Element concept and click
	public void handleTwoLevelMenu(By parentMenu, By childMenu) throws InterruptedException {
		doMoveToElement(parentMenu);
		Thread.sleep(2000);
		doClick(childMenu);
	}

	/*
	 * Why create two overloaded methods for handling two-level menus? - One method
	 * accepts a By locator for the child menu, while the other accepts the link
	 * text. - This allows flexibility depending on how the child menu element is
	 * identified. - Use the appropriate method based on the available information
	 * about the child menu.
	 */
// Move to Element concept and click 
	public void handleTwoLevelMenu(By parentMenu, String childMenuLinkText) throws InterruptedException {
		doMoveToElement(parentMenu);
		Thread.sleep(2000);
		doClick(By.linkText(childMenuLinkText));
	}

	// It will work for 4 menu items
	public void multiLevelMenuChildMenuHandle(By parentMenuLocator, String level2LinkText, String level3LinkText,
			String level4LinkText) throws InterruptedException {
		WebElement level1 = getElement(parentMenuLocator);

		Actions act = new Actions(driver);
		act.moveToElement(level1).click().build().perform();
		Thread.sleep(1000);

		WebElement level2 = getElement(By.linkText(level2LinkText));
		act.moveToElement(level2).build().perform();
		Thread.sleep(1000);

		WebElement level3 = getElement(By.linkText(level3LinkText));
		act.moveToElement(level3).build().perform();
		Thread.sleep(1000);
		doClick(By.linkText(level4LinkText));

	}

	public void doActionsSendKeys(By locator, String value) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator), value).build().perform();
	}

	public void doActionsClick(By locator) {
		Actions act = new Actions(driver);
		act.click(getElement(locator)).build().perform();
	}

	// **************************************** WAIT UTILS **************************************
	/**
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does not necessarily mean that the element is visible on the page.
	 * ***************** will not give surety *******************
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public WebElement waitForElementPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page
	 * and visible on the page. Visibility means that the element is not only
	 * displayed but also has a height and width that isgreater than 0.
	 * ***************** will give surety *********************
	 * 
	 * @param locator
	 * @param timeout
	 */
	//****************** we not calling this dirctly, we calling via getElement, *Rememmber* ***********************
	public WebElement waitForElementVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	/**
	 * An expectation for checking that all elements present on the web page that
	 * match the locator are visible. Visibility means that the elements are not only
	 * displayed but also have a heightand width that is greater than 0. 
	 * ***** Better than the *presenceOfAllElementsLocatedBy*
	 * default timeout = 500ms
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public List<WebElement> waitForElementsVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}
// overalode the above method, passing 1 extra interval time. check this >>>> WaitForElementWithInterval	
	public List<WebElement> waitForElementsVisible(By locator, int timeOut, int intervalTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(intervalTime));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}
/**
 * An expectation for checking that there is at least one element present on a web page.
 * Ex:out of 35 footer if any of the element is visible on the page then it will give the complete list of the footer
 * @param locator
 * @param timeOut
 * @return
 */
	public List<WebElement> waitForElementsPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}
	
/**
 * An expectation for checking an element is visible and enabled such that you can click it.
 * @param locator
 * @param timeOut
 */
	public void clickElementWhenReady(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}	

	/**
	 * An expectation for checking an element is visible and enabled such that you can click it.
	 * it will check element is clickable or not 
	 * @param locator
	 * @param timeOut
	 * don't use directly this method, use via do click
	 */
	public WebElement checkElementClickable(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}
	
	// *********>> Alert wait concept 
	
	// Alert with the fluent wait 
	public Alert waitForAlertPopUpWithFluentWait(int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)                  //4
                .withTimeout(Duration.ofSeconds(timeOut))           
                .ignoring(NoAlertPresentException.class)         
                .pollingEvery(Duration.ofSeconds(pollingTime))        
                .withMessage("--------Time out is done...alert is not found .... ");  
		
		return wait.until(ExpectedConditions.alertIsPresent());
	}
	
	
	// if the alert come it automattically switch to the alert no need to write switch
	public Alert waitForAlertJsPopUp(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public String alertJSGetText(int timeOut) {
		return waitForAlertJsPopUp(timeOut).getText();
	}

	public void alertAccept(int timeOut) {
		waitForAlertJsPopUp(timeOut).accept();
	}

	public void alertDismiss(int timeOut) {
		waitForAlertJsPopUp(timeOut).dismiss();
	}

	public void EnterAlertValue(int timeOut, String value) {
		waitForAlertJsPopUp(timeOut).sendKeys(value);
	}
	
	// *********** Title wait 
	public String waitForTitleIsAndCapture(String titleFraction, int timeOut) { // work like contains
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if (wait.until(ExpectedConditions.titleContains(titleFraction))) { // 2
			String title = driver.getTitle();
			return title;
		} else {
			System.out.println("Title is not present within the given timeout  : " + timeOut);
			return null; // we returning the title is not found
		}
	}

	public String waitForFullTitleAndCapture(String titleVal, int timeOut) { // here you have to write complete title
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut)); // 3
		if (wait.until(ExpectedConditions.titleIs(titleVal))) {
			String title = driver.getTitle();
			return title;
		} else {
			System.out.println("Title is not present within the given timeout  : " + timeOut);
			return null;
		}
	}
	// *********** URL wait 
	public String waitForURLContainsAndCapture(String urlFraction, int timeOut) { // here no need to pass complete URL
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut)); // 4
		if (wait.until(ExpectedConditions.urlContains(urlFraction))) {
			String url = driver.getCurrentUrl();
			return url;
		} else {
			System.out.println("URL is not present within the given timeout  : " + timeOut);
			return null;
		}
	}

	public String waitForURLAndCapture(String urlValue, int timeOut) { // here you have to pass complete URL
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut)); // 5
		if (wait.until(ExpectedConditions.urlToBe(urlValue))) {
			String url = driver.getCurrentUrl();
			return url;
		} else {
			System.out.println("URL is not present within the given timeout  : " + timeOut);
			return null;
		}
	}
	public Boolean waitForTotalWindows(int totalWindowsToBe, int timeOut) {		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));             
		return wait.until(ExpectedConditions.numberOfWindowsToBe(totalWindowsToBe));
	} 
	// frame wait 
	public void waitForFrameAndSwitchToItWithFluentWait(int timeOut, int pollingTime, String idOrName) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)                  //4
                .withTimeout(Duration.ofSeconds(timeOut))           
                .ignoring(NoSuchFrameException.class)         
                .pollingEvery(Duration.ofSeconds(pollingTime))        
                .withMessage("--------Time out is done...frame is not found .... ");  
		
		 wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(idOrName));
	}
	
	public void waitForFrameAndSwitchToItByIDOrName(int timeOut, String idOrName) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(idOrName)); 
		}
		
		public void waitForFrameAndSwitchToItByIndex(int timeOut, int frameIndex) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex)); 
		}
		
		public void waitForFrameAndSwitchToItByFrameElement(int timeOut, WebElement frameElement) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement)); 
		}
		
		public void waitForFrameAndSwitchToItByFrameLocator(int timeOut, By frameLocator) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator)); 
		}
		
		// fluent wait
		public WebElement waitForElementVisibleWithFluentWait(By locator, int timeOut, int pollingTime) {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)                  //4
	                .withTimeout(Duration.ofSeconds(timeOut))           
	                .ignoring(NoSuchElementException.class)         
	                .ignoring(ElementNotInteractableException.class)
	                .pollingEvery(Duration.ofSeconds(pollingTime))        
	                .withMessage("--------Time out is done...element is not found .... ");  
			
			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		}
		public WebElement waitForElementPresenceFluentWait(By locator, int timeOut, int pollingTime) {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)                  //4
	                .withTimeout(Duration.ofSeconds(timeOut))           
	                .ignoring(NoSuchElementException.class)         
	                .ignoring(ElementNotInteractableException.class)
	                .pollingEvery(Duration.ofSeconds(pollingTime))        
	                .withMessage("--------Time out is done...element is not found .... ");  
			
			return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		}
}
