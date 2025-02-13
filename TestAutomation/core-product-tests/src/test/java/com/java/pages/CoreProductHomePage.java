package com.java.pages;

import java.io.IOException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.java.base.SetUp;
import com.java.utils.Utilities;

public class CoreProductHomePage extends Utilities{
		
	public CoreProductHomePage()
	{
		super();
	}
		
	public void closeWindow()
	{	
		waitForPageToLoad(getconfigPropertyValue("pageLoadTime"));
		refreshPage();		
		try {
			clickElement(closeHomePageWindow);
		}
		catch(Exception e)
		{
			log.info("Home Page Popup Window not displayed");
		}
		acceptCookies();
		LoginReport("Core Product Home Page was displayed");
	}
	public void acceptCookies()
	{
		try
		{
		clickElement(acceptCookies);
		log.info("Accepted cookies");
		}
		catch(Exception e)
		{
			log.info("No cookie popup got displayed");
		}
	}	
	public void moveToMenuOptions()
	{
		zoomIn();
		moveToElement(MenuItem);
		LoginReport("News and Features option was displayed");
		
	}
	public CoreProductNewsPage selectNewsAndFeatures()
	{
		clickElement(NewsAndFeatures);	
		return new CoreProductNewsPage();
	}
	public String getCPHomePageTitle()
	{		
		return getTitle();		
	}
	
	@FindBy(xpath="//div[text()='x']")
	private WebElement closeHomePageWindow;
	
	
	@FindBy(xpath="//nav[@aria-label='Golden State Warriors navigation']/div//span[text()='Shop']")
	private  WebElement ShopMenu;
	
	
	@FindBy(xpath="//nav[contains(@id,'nav-dropdown-desktop')]//a[contains(@title,'Men')]")
	private WebElement MenOption;
	
	@FindBy(xpath="//button[text()='I Accept']")
	private WebElement acceptCookies;
	
	@FindBy(xpath="//a[contains(@title,'Kaiser')]/following-sibling::div//span[text()='...']")
	private WebElement MenuItem;
	

	@FindBy(xpath="//span[text()='...']/ancestor::nav//nav//a[@title='News & Features']")
	private WebElement NewsAndFeatures;
	
	
	
}
