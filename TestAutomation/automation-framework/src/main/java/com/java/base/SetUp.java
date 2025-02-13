package com.java.base;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.LogManager;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.java.dataReader.jsonReader;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.java.reports.ExtentReportsGenerator;
import com.java.utils.Utilities;

public class SetUp{
	 
	private ChromeOptions chromeOptions;
	private EdgeOptions edgeOptions;
	public static  String BaseDirectory;
	private static final String log4J = "/src/main/java/com/java/logs/log4j.properties";
	public static final String CurrentDirectory=System.getProperty("user.dir");
	public static Logger log=Logger.getLogger(SetUp.class);
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>(); 
	public static ThreadLocal<String> currentBrowserName = new ThreadLocal<>();
	public static Properties objectProp; 	
	public static Properties configProp; 
	public static FileInputStream configPropertiesFile;
	public static FileInputStream objectPropertiesFile;	
	
	@BeforeSuite
	public void initalizeFiles() throws IOException
	{
		getPath();					//returns current directory path
		initializeLogs();			//Initializes log4j
		readProperties();			//Setting up the properties file to read xpaths and configurations from OR.properties and config.properties	
	}
	
	public void initializeLogs()
	{
		
		PropertyConfigurator.configure(BaseDirectory+log4J);
	}

		
	public void setupBrowserproperties()			
	{
		if (getDriver() != null) {
		getDriver().manage().window().maximize();
		log.info(getBrowserName() + "Browser was maximized");		
		getDriver().manage().deleteAllCookies();
		}
	}
	
	public void getPath()
	{
        Path automationFrameworkPath = Paths.get(CurrentDirectory, "..", "automation-framework").normalize();
        BaseDirectory=automationFrameworkPath.toAbsolutePath().toString();    
	}
	
	public static void readProperties() throws IOException
	{
		objectProp = new Properties();
		configProp = new Properties();
		objectPropertiesFile = new FileInputStream(BaseDirectory + "/src/main/java/com/java/config/OR.properties");
		configPropertiesFile = new FileInputStream(BaseDirectory + "/src/main/java/com/java/config/config.properties");
		objectProp.load(objectPropertiesFile);
		log.info("Initialised properties file containing xpaths");
		configProp.load(configPropertiesFile);
		log.info("Initialised properties file containing configurations");
	}

	public synchronized void initialiseBrowser(String browserName)
   {
		switch(browserName)
		{
		case "Chrome":												
			WebDriverManager.chromedriver().setup();	 
			chromeOptions= new ChromeOptions();
			addOptions(chromeOptions);
			driver.set(new ChromeDriver(chromeOptions));	
			log.info("Chrome Browser was initialised");
			break;	
			
		case "Edge":
			WebDriverManager.edgedriver().setup();
			edgeOptions = new EdgeOptions();
			addOptions(edgeOptions);
			driver.set(new EdgeDriver(edgeOptions));
			log.info("Edge Browser was initialised");
			break;		
			
		default:
             throw new IllegalArgumentException("Invalid browser name: " + browserName);
		}
	}
	 public void addOptions(Object browserOptions) {
	        if (browserOptions instanceof ChromeOptions) {
	            ChromeOptions chrome = (ChromeOptions) browserOptions;
	            chrome.addArguments("--remote-allow-origins=*");  
	            chrome.addArguments("--disable-gpu");
	        } else if (browserOptions instanceof EdgeOptions) {
	            EdgeOptions edge = (EdgeOptions) browserOptions;
	            edge.addArguments("--remote-allow-origins=*");  
	            edge.addArguments("--disable-gpu");	         
	        }
	    }
	 public static  WebDriver getDriver() {	       
				return driver.get();
	    }

		public static String getBrowserName() {    
		    return currentBrowserName.get();
		}
	 
	    @AfterSuite    
	    public static void quitDriver() {		
		        driver.remove(); 
	   }
}

	
	