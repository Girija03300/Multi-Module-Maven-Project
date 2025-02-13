package com.java.utils;


import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.java.base.SetUp;
import com.java.base.pageBase;
import com.java.reports.ExtentReportsGenerator;

public class Utilities extends pageBase{
	private String parentWindow;
	public Utilities()
	{
		super();
	}
	public void clickElement(WebElement element) {
		waitForElementToLoad(element, getconfigPropertyValue("waitTime"));
		waitForElementTobeClickabale(element,getconfigPropertyValue("waitTime"));
		element.click();
	}
	
	public void sendText(WebElement element,String text)
	{
		waitForElementToLoad(element, getconfigPropertyValue("waitTime"));
		element.sendKeys(text);
	}
	
	public void waitForElementToLoad(WebElement element,String time)
	{
		int waitTime=Integer.parseInt(time);
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(waitTime));
		wait.until(ExpectedConditions.visibilityOf(element));		
	}
	public void waitForElementTobeClickabale(WebElement element,String time)
	{
		int waitTime=Integer.parseInt(time);
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(waitTime));
		wait.until(ExpectedConditions.elementToBeClickable(element));		
	}
	public void waitForElementAttributeToBeTrue(WebElement element,String attribute,String time,String value)
	{
		int waitTime=Integer.parseInt(time);
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(waitTime));
		wait.until(ExpectedConditions.attributeToBe(element, attribute, value));	
	}
	public void acceptAlert()
	{
		Alert alert =getDriver().switchTo().alert();
		alert.accept();
	}
	public void  getParentWindow()
	{
		parentWindow=getDriver().getWindowHandle();		
	}
		
	public  void switchToAnotherWindow(String browserName )
	{	
		Set<String> windows= getDriver().getWindowHandles();
		for(String windowHandle :windows)
		{
			if(!windowHandle.equals(parentWindow))
			getDriver().switchTo().window(windowHandle);
		}
		}
	
	public void moveToElement(WebElement element)
	{
		Actions actions = new Actions(getDriver());
		waitForElementToLoad(element, getconfigPropertyValue("waitTime"));
		scrollTo(element);
		actions.moveToElement(element).perform();
	}
	public void scrollTo(WebElement element)
	{
		waitForElementToLoad(element, getconfigPropertyValue("waitTime"));
		JavascriptExecutor js = (JavascriptExecutor)getDriver();
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	public int getItemsCount(List<WebElement> li)
	{	
		return li.size();		
	}
	
	public WebElement getElement(String path)
	{
		return getDriver().findElement(By.xpath(path));
	}
	public void zoomIn()
	{
		 JavascriptExecutor js = (JavascriptExecutor)getDriver();
	     js.executeScript("document.body.style.zoom='70%'");  		
	}
	public static String getPropertyValue(String key) {
		String value=objectProp.getProperty(key);
		return value;
	}
	
	public static String getconfigPropertyValue(String key) {
		String value=configProp.getProperty(key);
		return value;
	}
	
	public static String captureScreenshot() 
	{
		 try {
		TakesScreenshot  ts =(TakesScreenshot)getDriver();
		String src=ts.getScreenshotAs(OutputType.BASE64);
	    return src;	    
		 }
		 catch (Exception e) {
		        log("Not able to capture the screenshot" + e.getMessage());
		        return "";
		    }
	}
	
	public ExtentTest LoginReport(String text) 
	{			
		log.info(text);
		try {
			ExtentReportsGenerator.test.get().log(Status.PASS,text + " in " + SetUp.getBrowserName(),MediaEntityBuilder.createScreenCaptureFromBase64String(captureScreenshot()).build());
		} catch (IOException e) {
			SetUp.log.error("Error while updating the extent reports" + e.getMessage(),e);
		}		
			return ExtentReportsGenerator.test.get();
	}	
	
	public static void log(String element)
	{		
		log.info(element +" in " + SetUp.getBrowserName());
		ExtentReportsGenerator.test.get().log(Status.PASS,element);
	}
	
	public void refreshPage()
	{
		getDriver().navigate().refresh();
	}
	
	public void waitForPageToLoad(String time)
	{
		int waitTime=Integer.parseInt(time);
		getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(waitTime));
	}
	public String getTitle()
	{
		return getDriver().getTitle();
	}
}
