package com.java.stepDefinition;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import com.java.pages.DerivedProduct2HomePage;
import com.java.base.SetUp;
import com.java.dataReader.jsonReader;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Footers {

		DerivedProduct2HomePage DP2HomePage;
		
		
	    @Given("^I am on DP2 home page$")
	    public void i_am_on_dp2_home_page() throws IOException, ParseException {
	    	DP2HomePage= new DerivedProduct2HomePage(SetUp.getBrowserName());
	    	Assert.assertTrue(DP2HomePage.getDP2HomePageTitle().contains(jsonReader.getDataFromJson("DP2HomePage", "title")));
	    }

	    @When("^I scroll down to the footer$")
	    public void i_scroll_down_to_the_footer() {
	        // Scroll down to the footer element
	    DP2HomePage.scrollDownToFooter();
	      
	    }

	    @Then("^I should see different links for various categories$")
	    public void i_should_see_different_links_for_various_categories() {
	       Assert.assertTrue(DP2HomePage.footerLinksAreDisplayed()>0 , "Footer Links are not displayed");
	       DP2HomePage.getFooterLinks();
	    }

	    @Then("^I should extract all the hyperlinks in the footer into a CSV file$")
	    public void i_should_extract_all_the_hyperlinks_in_the_footer_into_a_csv_file() throws IOException {
	    	DP2HomePage.writeHyperLinksToCSVFile(SetUp.getBrowserName());
	    }

	    @Then("^I should report if any duplicate hyperlinks are present$")
	    public void i_should_report_if_any_duplicate_hyperlinks_are_present() {
	    	DP2HomePage.findDuplicateHyperLinks();
	    	DP2HomePage.reportDuplicateHyperLinks();
	    }

	}

