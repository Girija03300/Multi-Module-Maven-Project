package com.java.runner;
   import io.cucumber.testng.CucumberOptions;
         import io.cucumber.testng.AbstractTestNGCucumberTests;
         import org.testng.annotations.BeforeClass;
         import org.testng.annotations.BeforeTest;
         import org.testng.annotations.Parameters;
         import com.java.base.SetUp;
         import com.java.reports.ExtentReportsGenerator;
         import java.io.IOException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

     @CucumberOptions(
          features = "src/test/java/com/java/features/VideoFeeds.feature",
          glue = {"com.java.base", "com.java.stepDefinition"},
          plugin = {
         			"pretty",
         			 "json:target/cucumber.json",
         			 "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
         			 "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
         			    },
   	    monochrome = true
      )
         public class VideoFeedsRunner extends AbstractTestNGCucumberTests {
         @BeforeSuite
      	public void initalizeFiles() throws IOException
      	{
         SetUp.getPath();				//returns current directory path
      	 SetUp.readProperties();			//Setting up the properties file to read xpaths and configurations from OR.properties and config.properties
         ExtentReportsGenerator.initializeReport();
      	}

          @Parameters("browser")
          @BeforeTest
          public void setup(String browserName) {

         	 SetUp.getInstance().initialiseBrowser(browserName);
              SetUp.currentBrowserName.set(browserName);
              SetUp.setupBrowserproperties();
          }
          @BeforeClass
          @Parameters("cucumber.options")
          public void setCucumberTags(String cucumberTags) {
              if (cucumberTags != null && !cucumberTags.isEmpty()) {
                  System.setProperty("cucumber.filter.tags", cucumberTags);
                  SetUp.log.info("Cucumber Tags Set at Runtime as " + cucumberTags + " in " + SetUp.getBrowserName());
              } else {
                  System.out.println("No tags are specified");
              }
          }

          @AfterSuite
        public void quitDriver() {
        	 SetUp.get().remove();
          }
         }
