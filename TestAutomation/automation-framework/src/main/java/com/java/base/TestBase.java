package com.java.base;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import com.java.dataReader.JsonReader;
import com.java.reports.ExtentReportsGenerator;
import com.java.utils.Utilities;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;

public class TestBase  {
	public static String scenarioName;

	//getting scenario name during run time.
	@Before(order = 0)
    public static void beforeScenario(Scenario scenario) {
		ExtentReportsGenerator.startTest(scenario.getName());
		scenarioName=scenario.getName();
		SetUp.log.info("Running Scenario " + scenario.getName() + " in " + SetUp.getBrowserName());
    }
	//Opening core product application
	@Before("@CP2")
	public void openCoreProductWebSite() throws IOException, ParseException
	{
		SetUp.getDriver().get(JsonReader.getDataFromJson("URL", "coreProductURL"));
		Utilities.log("Navigated to " + JsonReader.getDataFromJson("URL", "coreProductURL"));
	}
	//opening derived product 1 application
	@Before("@DP1")
	public void openDerivedProduct1WebSite() throws IOException, ParseException
	{
		SetUp.getDriver().get(JsonReader.getDataFromJson("URL", "deliveredProduct1"));
		Utilities.log("Navigated to " + JsonReader.getDataFromJson("URL", "deliveredProduct1"));
	}
	//opening derived product 2 application
	@Before("@DP2")
	public void DeliveredProductWebSite() throws IOException, ParseException
	{
		SetUp.getDriver().get(JsonReader.getDataFromJson("URL", "deliveredProduct2"));
		Utilities.log("Navigated to " + JsonReader.getDataFromJson("URL", "deliveredProduct2"));				
	}
}
