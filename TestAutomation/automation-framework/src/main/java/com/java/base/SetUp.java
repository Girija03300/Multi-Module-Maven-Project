package com.java.base;


import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SetUp{
	private static ThreadLocal<SetUp> setUp = new ThreadLocal<>();
	private ChromeOptions chromeOptions;
	private EdgeOptions edgeOptions;
	public static  String BaseDirectory;
	public static final String CURRENTDIRECTORY=System.getProperty("user.dir");
	public static Logger log =LoggerFactory.getLogger(SetUp.class);
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>(); 
	public static ThreadLocal<String> currentBrowserName = new ThreadLocal<>();
	public static Properties objectProp; 	
	public static Properties configProp; 
	public static FileInputStream configPropertiesFile;
	public static FileInputStream objectPropertiesFile;	
	public static String configPropertiesFilePath = "/src/main/java/com/java/config/page-elements.properties";
	public static String objectPropertiesFilePath = "/src/main/java/com/java/config/config.properties";
	
   private SetUp()
   {
	   
   }	
	public static synchronized SetUp getInstance() {
        if (setUp.get() == null) {
        	setUp.set(new SetUp());
        }
        return setUp.get();
    }
			
	public static void setupBrowserproperties()			
	{
		getDriver().manage().window().maximize();
		log.info(getBrowserName() + "Browser was maximized");		
		getDriver().manage().deleteAllCookies();
	}
	
	public static void getPath()
	{
        Path automationFrameworkPath = Paths.get(CURRENTDIRECTORY, "..", "automation-framework").normalize();
        BaseDirectory=automationFrameworkPath.toAbsolutePath().toString();    
	}
	
	public static void readProperties() throws IOException
	{
		objectProp = new Properties();
		configProp = new Properties();
		objectPropertiesFile = new FileInputStream(BaseDirectory + objectPropertiesFilePath);
		configPropertiesFile = new FileInputStream(BaseDirectory + configPropertiesFilePath);
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
	 public static ThreadLocal<WebDriver> get()
	 {
		 return driver;
	 }
	  
}

	
	