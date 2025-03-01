package com.java.stepDefinition;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.java.base.SetUp;
import com.java.base.TestBase;
import com.java.dataReader.JsonReader;
import com.java.pages.CoreProductHomePage;
import com.java.pages.CoreProductNewsPage;
import com.java.utils.Utilities;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class VideoFeeds{
	   	   
	    @Given("^I am on the CP home page$")
	    public void i_am_on_the_cp_home_page() throws IOException, ParseException {
				Assert.assertTrue(CoreProductHomePage.getInstance().getCPHomePageTitle().contains(JsonReader.getDataFromJson("CPHomePage", "title")));
	    }

	    @When("^I hover over the menu item$")
	    public void i_hover_over_the_menu_item() throws IOException {
	    	CoreProductHomePage.getInstance().closeWindow();
	    	CoreProductHomePage.getInstance().moveToMenuOptions();	    	
	    }

	    @When("^I click on News & Features$")
	    public void i_click_on_new_features() {
	    	CoreProductHomePage.getInstance().selectNewsAndFeatures();
	    	
	    }

	    @Then("^I count the total number of video feeds$")
	    public void i_count_the_total_number_of_video_feeds() {
	    	int totalVideoFeeds=CoreProductNewsPage.getInstance().getTotalVideoFeeds();
	    	Assert.assertTrue(totalVideoFeeds>0);
	    }

	    @Then("^I count the video feeds that are present for more than Three Days$")
	    public void i_count_the_video_feeds_that_are_present_for_more_than_Three_days() throws IOException, ParseException {
	    	CoreProductNewsPage.getInstance().getVideoFeedsTime();
	       int totalVideoFeedsGreaterToThreeDays=CoreProductNewsPage.getInstance().getVideoFeedsGreaterThanThreeDays();
	       Assert.assertTrue(totalVideoFeedsGreaterToThreeDays>=0);
	    }
	}


