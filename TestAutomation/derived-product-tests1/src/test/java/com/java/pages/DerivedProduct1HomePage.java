package com.java.pages;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.java.base.SetUp;
import com.java.dataReader.JsonReader;
import com.java.utils.Utilities;

public class DerivedProduct1HomePage extends Utilities{
	private static ThreadLocal<DerivedProduct1HomePage> DP1HomePage = new ThreadLocal<>();
	private int totalNumberOfSlides;
	private String slideTitleFirstPart=getPropertyValue("slideTitleFirstPart");
	private String slideTitleSecondPart=getPropertyValue("slideTitleSecondPart");
	private List<String> slideTitles;
	private Map<String,Long> slidesData; 
	  private WebDriver driver;
	public DerivedProduct1HomePage(WebDriver driver) {
		super();
		this.driver = driver;
	}
	 public static DerivedProduct1HomePage getInstance() {
		 if (DP1HomePage.get() == null) {
		       DP1HomePage.set(new DerivedProduct1HomePage(SetUp.getDriver()));
		    }
		return DP1HomePage.get();
		    }	
	public String getDP1HomePageTitle()
	{
		return getTitle();
	}
	
	public int getSlidesCount()
	{
		waitForPageToLoad(getconfigPropertyValue("pageLoadTime"));
		scrollTo(slidesSection);
		log("Slides are displayed in Home Page");
		totalNumberOfSlides= getItemsCount(totalSlides);
		LoginReport("Total Number number of Slides present are: " + totalNumberOfSlides);
		return totalNumberOfSlides;
	}
	
	public void getTitleOfEachSlide()
	{
		slideTitles = new ArrayList<>();
		for (int i=1;i<=totalNumberOfSlides;i++)
		{
			String title=getElement(slideTitleFirstPart+i+slideTitleSecondPart).getText();
			log("Slide title: " + title);
			slideTitles.add(title);
		}
	}
	public boolean compareSlideTitles() throws IOException, ParseException
	{
		int count=0;
		
		for(int i=0;i<totalNumberOfSlides;i++)
		{
			String slideTitle = "slideTitle"+String.valueOf(i+1);
			if(slideTitles.get(i).equals(JsonReader.getDataFromJson("DP1HomePage", slideTitle)))
			{
				count++;
			}
		}		
		if(totalNumberOfSlides == count)
		{
			log("Slide titles are matching with the expected data");
			return true;
		}
		
		else
		{
			return false;
		}
	}
	public void getDurationOfEachSlide() throws InterruptedException
	{
		slidesData = new HashMap<>();
		for(int i=1;i<=totalNumberOfSlides;i++)
		{
			WebElement highlightedSlideElement=getElement(slideTitleFirstPart+i+"]");			
			waitForElementAttributeToBeTrue(highlightedSlideElement,"aria-selected",getconfigPropertyValue("pageLoadTime"),"true");
			long startTime = System.currentTimeMillis();
			while(highlightedSlideElement.getAttribute("aria-selected").equals("true"))
				{
				Thread.sleep(100);
				}
			long endTime=System.currentTimeMillis();
			double durationOfEachSlide = (endTime - startTime) / 1000.0;
			slidesData.put(slideTitles.get(i-1),Math.round(durationOfEachSlide));
		}
		
	}
	public boolean compareSlideDuration() throws NumberFormatException, IOException, ParseException
	{
		int count = 0;
		 for (Map.Entry<String, Long> entry : slidesData.entrySet()) {
			 
				log(entry.getKey() + " slide duration is " + entry.getValue());
				if( entry.getValue() >= Long.parseLong(JsonReader.getDataFromJson("DP1HomePage", "slideDurationstart")))
				{
					if(entry.getValue() <= Long.parseLong(JsonReader.getDataFromJson("DP1HomePage", "slideDurationend")))
					{
					count++;
					}
				}
				
			 }
        if(count == totalNumberOfSlides)
        {
        	LoginReport("Slides duration is matching with the expected data");
        	return true;
        }
        else
        {
        	return false;
        }

	}
	

	@FindBy(xpath = "//div[@role='tablist']/button")
	private List<WebElement> totalSlides;
	
	@FindBy(xpath = "//div[@role='tabpanel']")
	private WebElement slidesSection;
}
