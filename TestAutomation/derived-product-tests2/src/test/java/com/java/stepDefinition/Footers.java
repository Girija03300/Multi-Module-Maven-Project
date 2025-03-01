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
import com.java.dataReader.JsonReader;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Footers {
		
	    @Given("^I am on DP2 home page$")
	    public void i_am_on_dp2_home_page() throws IOException, ParseException {	
	    	Assert.assertTrue(DerivedProduct2HomePage.getInstance().getDP2HomePageTitle().contains(JsonReader.getDataFromJson("DP2HomePage", "title")));
	    }

	    @When("^I scroll down to the footer$")
	    public void i_scroll_down_to_the_footer() {
	        // Scroll down to the footer element
	    	DerivedProduct2HomePage.getInstance().scrollDownToFooter();
	      
	    }

	    @Then("^I should see different links for various categories$")
	    public void i_should_see_different_links_for_various_categories() {
	       Assert.assertTrue(DerivedProduct2HomePage.getInstance().footerLinksAreDisplayed()>0 , "Footer Links are not displayed");
	       DerivedProduct2HomePage.getInstance().getFooterLinks();
	    }

	    @Then("^I should extract all the hyperlinks in the footer into a CSV file$")
	    public void i_should_extract_all_the_hyperlinks_in_the_footer_into_a_csv_file() throws IOException {
	    	DerivedProduct2HomePage.getInstance().writeHyperLinksToCSVFile(SetUp.getBrowserName());
	    }

	    @Then("^I should report if any duplicate hyperlinks are present$")
	    public void i_should_report_if_any_duplicate_hyperlinks_are_present() {
	    	DerivedProduct2HomePage.getInstance().findDuplicateHyperLinks();
	    	DerivedProduct2HomePage.getInstance().reportDuplicateHyperLinks();
	    }

	}

