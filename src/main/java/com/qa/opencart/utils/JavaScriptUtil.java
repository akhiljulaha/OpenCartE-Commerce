package com.qa.opencart.utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;

public class JavaScriptUtil {

	private WebDriver driver;
	private JavascriptExecutor js;
	
	public JavaScriptUtil(WebDriver driver) {             
		this.driver = driver;
		js = (JavascriptExecutor) this.driver; // convert the driver into the JavascriptExecutor and with the help of ref. call the methods
	}
	
	
	public void flash(WebElement element) {
		String bgcolor = element.getCssValue("backgroundColor");// purple
		for(int i = 0; i < 5; i++) {
			changeColor("rgb(0,200,0)", element);  //1
			changeColor(bgcolor, element);       //2
		}
	}
	
	private void changeColor(String color, WebElement element) {
		js.executeScript("arguments[0].style.backgroundColor = '" + color + "'", element);  // green -> purple>G>p>g....
		
		try {
			Thread.sleep(20);
		}catch (InterruptedException e){
		}
	}
	
	public void goBackByJS() {
		js.executeScript("history.go(-1)");
	}
	
	public void goForwardByJS() {
		js.executeScript("history.go(1)");
	}
	
	public void refreshBrowserByJS() {
		js.executeScript("history.go(0);");
	}
	
	public String getTitleByJS() {
		return js.executeScript("return document.title").toString();
	}
	
	public void generateAlert(String message) {
		js.executeScript("alert('"+ message +"')");           // Just generating the alert no need of return keyword
	}
	
	public void generateConfirmPopUp(String message) {
		js.executeScript("confirm('"+ message + "')");	
	}
	
	public String getPageInnerText() {             // It Will give the text of the entire page without html tag (This method is not available in the selenium)
		return js.executeScript("return document.documentElement.innerText;").toString();
	}
	
	public void clickElementByJS(WebElement element) {
		js.executeScript("arguments[0].click();", element);
	}
	
	public void sendKeysUsingWithId(String id, String value) {          //NOTE: This will applicable only when ID is available
		js.executeScript("document.getElementById('" + id +"').value='" + value + "'");
		//		document.getElementById('login-username').value="aj"; 
	}
	
	public void scrollPageDown() {                              // It will go footer
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");     // window means complete page, 
	}
	
	public void scrollPageUp() {                              // It will go Top of the page 
		js.executeScript("window.scrollTo(document.body.scrollHeight, 0)");     // window means complete page, 
	}
	
	public void scrollPageDown(String height) {                                        //For specific height 
		js.executeScript("window.scrollTo(0, '" + height + "')");
	}
	
	public void scrollPageDownMiddlepage() {                            // It will redirect to the middle of the page 
		js.executeScript("window.scrollTo(0, document.body.scrollHeight/2)");
	}
	
	public void scrollIntoView(WebElement element) {                        // Scroll upto to the specific element 
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	public void horizontalScrollBy(String x, String y) {
		//window.scrollBy(0,-1000);
		String script = "window.scrollBy('"+x+"' + , + '"+y+"')" ;                                     // x should be behaving variable that why mentioned with single ''
        js.executeScript(script);
	}
	
	/**
	 * example: "document.body.style.zoom = '400.0%'"         // this script only work for chrome and edge (for fireFox we have to use diff script)
	 * @param zoomPercentage
	 */
	public void zoomChromeEdge(String zoomPercentage) {
		String zoom = "document.body.style.zoom = '"+zoomPercentage+"%'";
		js.executeScript(zoom);
	}
	
	/**
	 * example: document.body.style.MozTransform = 'scale(0.5)' 
	 * @param zoomPercentage
	 */
	public void zoomFirefox(String zoomPercentage) {
		String zoom = "document.body.style.MozTransform = 'scale("+zoomPercentage+")'";
		js.executeScript(zoom);
	}
	
	public void drawBorder(WebElement element) {
		js.executeScript("arguments[0].style.border='3px solid red'", element);
	}

	
	

}
