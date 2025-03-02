package com.java.pages;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.java.base.SetUp;
import com.java.utils.Utilities;

	
public class CoreProductNewsPage extends Utilities {
	private static ThreadLocal<CoreProductNewsPage> CPNewsPage = new ThreadLocal<>();
	private String timeFirstPart=getPropertyValue("timeFirstPart");
	private String timeSecondPart=getPropertyValue("timeSecondPart");
	private int totalVideoFeeds;
	private int videoFeedsGreaterThanThreeDays=0;
	private List<String> videoFeedsTime = new ArrayList<>();
	private WebDriver driver;
	private CoreProductNewsPage(WebDriver driver)
	{
		super();
		this.driver=driver;
	}
	public static CoreProductNewsPage getInstance()
	{
		if(CPNewsPage.get() == null)
		{
			CPNewsPage .set(new CoreProductNewsPage(SetUp.getDriver()));
		}
		return CPNewsPage.get();
	}
	public int  getTotalVideoFeeds()
	{	
		refreshPage();
		waitForPageToLoad(getconfigPropertyValue("pageLoadTime"));
		LoginReport("News and Media Page was displayed");
		scrollTo(videos);
		log("Scrolled to videos section");
		waitForElementToLoad(lastVideo, getconfigPropertyValue("waitTime"));
		LoginReport("Videos section was displayed");
		scrollTo(lastVideo);		
		totalVideoFeeds=getItemsCount(totalVideos);
		log("Total Video Feeds Displayed are "+ totalVideoFeeds);
		return totalVideoFeeds;		
	}
	
	public void getVideoFeedsTime()
	{	
		for(int i=1;i<=totalVideoFeeds;i++)
		{
			if(i == 1)
			{
				videoFeedsTime.add(firstVideo.getText());
				continue;
			}
			if(i==2)
			{
				continue;
			}
			String localXpath=timeFirstPart+i+timeSecondPart;
			String days=getElement(localXpath).getText();
			videoFeedsTime.add(days);

		}
		}
	
	public int getVideoFeedsGreaterThanThreeDays(String expectedDays) throws IOException, ParseException
	{
		for(String time :videoFeedsTime)
		{
			if(time.contains("d"))
			{
				String noOfDays = time.substring(0,1);
				int days=Integer.parseInt(noOfDays);
					if(days>=Integer.parseInt(expectedDays))
					{
						videoFeedsGreaterThanThreeDays++;
					}													
			}
		}
		try {
			LoginReport("Total number of video feeds greater than " + expectedDays + " days are " + videoFeedsGreaterThanThreeDays);
		} catch (Exception e) {
			
		}
		return videoFeedsGreaterThanThreeDays;
		
	}
	
	@FindBy(xpath="//h3[text()='VIDEOS']/parent::div/following-sibling::div//li")
	private List<WebElement> totalVideos;
	
	@FindBy(xpath="//h3[text()='VIDEOS']/parent::div/following-sibling::div//li[2]//iframe")
	private WebElement videoAd;
	
	@FindBy(xpath="//h3[text()='VIDEOS']")
	private WebElement videos;
	
	@FindBy(xpath="//h3[text()='VIDEOS']/parent::div/following-sibling::div//li[last()]")
	private WebElement lastVideo;
	
	@FindBy(xpath="//h3[text()='VIDEOS']/parent::div/following-sibling::div//li[1]//div[@data-testid='tile-featured-article']/div[2]/div[3]//span")
	private WebElement firstVideo;	
}
