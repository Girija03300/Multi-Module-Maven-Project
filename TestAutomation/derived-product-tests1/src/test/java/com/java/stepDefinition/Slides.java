package com.java.stepDefinition;

import io.cucumber.java.en.*;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import com.java.dataReader.JsonReader;
import com.java.pages.DerivedProduct1HomePage;

import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Slides {
	DerivedProduct1HomePage DP1HomePage;
	int totalNumberOfSlides;
    @Given("I am on DP1 home page")
    public void i_am_on_dp1_home_page() throws IOException, ParseException {
    	DP1HomePage = new DerivedProduct1HomePage();
    	Assert.assertTrue(DP1HomePage.getDP1HomePageTitle().contains(JsonReader.getDataFromJson("DP1HomePage", "title")));
    }

    @When("I count the number of slides present below the Tickets menu")
    public void i_count_slides_below_tickets_menu() throws NumberFormatException, IOException, ParseException {
    	totalNumberOfSlides = DP1HomePage.getSlidesCount();
    }

    @Then("I should see the expected number of slides")
    public void i_should_see_expected_number_of_slides() throws NumberFormatException, IOException, ParseException {
    	int expectedSlideCount = Integer.parseInt(JsonReader.getDataFromJson("DP1HomePage", "slidesCount"));
    	Assert.assertEquals(totalNumberOfSlides, expectedSlideCount,"Slides count is not as expected");
    }

    @When("I get the title of each slide")
    public void i_get_slide_titles() {
    	DP1HomePage.getTitleOfEachSlide();
    }

    @Then("I validate the slide titles with expected test data")
    public void i_validate_slide_titles() throws IOException, ParseException {
    	Assert.assertTrue(DP1HomePage.validateSlideTitles(), "Slide titles are not matching with expected data");    	
    }

    @When("I measure the duration each slide displayed")
    public void i_measure_slide_duration() throws InterruptedException {
    	DP1HomePage.getDurationOfEachSlide();
    }

    @Then("I validate the duration with the expected duration")
    public void i_validate_slide_durations() throws NumberFormatException, IOException, ParseException {
    	Assert.assertTrue(DP1HomePage.validateSlideDuration(),"Slide durations are not matching");
    	
    }
}
