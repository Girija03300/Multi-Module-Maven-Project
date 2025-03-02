package com.java.pages;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.java.base.SetUp;
import com.java.dataReader.FileWriterUtil;
import com.java.utils.Utilities;

public class DerivedProduct2HomePage extends Utilities{
			private String linkFirstPart=SetUp.objectProp.getProperty("footerLinksFirstPart");
			private String linkSecondPart=SetUp.objectProp.getProperty("footerLinksSecondPart");
			private final String footerLinksCSVPath=SetUp.configProp.getProperty("footerLinksPath");
			private List<String> hyperlink = new LinkedList<>();
			private Set<String> duplicatehyperlinks = new HashSet<>();
			private FileWriterUtil fw;
			private WebDriver driver;
			private static ThreadLocal<DerivedProduct2HomePage> DP2HomePage = new ThreadLocal<>();
			private DerivedProduct2HomePage(WebDriver driver,String browserName) 
			{
				super();
				this.driver=SetUp.getDriver();
				try {
					fw= new FileWriterUtil(SetUp.CURRENTDIRECTORY+"/FooterLinks/"+browserName+ footerLinksCSVPath);
				} catch (IOException e) {
					SetUp.log.error("Failed to initialize fileWriter: " + e.getMessage());
				}
			}
			public static DerivedProduct2HomePage getInstance()
			{
				if(DP2HomePage.get() == null)
				{
				   DP2HomePage.set(new DerivedProduct2HomePage(SetUp.getDriver(),SetUp.getBrowserName()));
				}
				return DP2HomePage.get();
			}
			public String getDP2HomePageTitle()
			{		
				return getTitle();		
			}
			
			public void scrollDownToFooter()
			{
				waitForPageToLoad(getconfigPropertyValue("pageLoadTime"));
				LoginReport("Derived Product 2 Home Page was displayed");
				scrollTo(Footer);	
				log("Scrolled down to footer section");
				
			}
			public int footerLinksAreDisplayed()
			{
				LoginReport("Different links for various categories are visible");
				return getItemsCount(totalLinks);
			}
			public void getFooterLinks()
			{
				List<WebElement> links = totalLinks;
				for(WebElement link:links)
				{
					String hyperlinks = link.getDomAttribute("href");	
					hyperlink.add(hyperlinks);
				}
			
			}
			public void writeHyperLinksToCSVFile(String browserName) throws IOException
			{
				 try
				 {
				     for(String links:hyperlink)
				      {
				       fw.writeData(links);	
				      }
				log("Hyper Links are written to " + browserName+footerLinksCSVPath+" CSV File");
				 }
				 finally
				 {
				 fw.closeFile();
				 }
			}
			
			public void findDuplicateHyperLinks()
			{						        
				Set<String> seenLinks = new HashSet<>();
			    for (String link : hyperlink) {
			        if (!seenLinks.add(link)) { 
			            duplicatehyperlinks.add(link);
			        }
			    }
			}
			
			public void reportDuplicateHyperLinks()
			{
		        if (duplicatehyperlinks.size() >0)
					 {
				       log("Duplicate hyperlinks are present!");
				        } else {
				        log("No Duplicate hyperlinks found.");
				        }	
		        
		        for(String links:duplicatehyperlinks)
		        {
		        	log("Duplicate hyper links are " + links );
		        }
		        
			}			
			@FindBy(xpath="//footer")
			private WebElement Footer;
			
			@FindBy(xpath="//footer/descendant::a")
			private List<WebElement> totalLinks;
}
