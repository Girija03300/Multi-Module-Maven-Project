package com.java.runner;
import io.cucumber.testng.CucumberOptions;
         import io.cucumber.testng.AbstractTestNGCucumberTests;
         import org.testng.annotations.BeforeClass;
         import org.testng.annotations.BeforeTest;
         import org.testng.annotations.Parameters;
         import com.java.base.SetUp;
         import com.java.reports.ExtentReportsGenerator;

         @CucumberOptions(
             features = "src/test/java/com/java/features/VideoFeeds.feature",
             glue = {"com.java.base", "com.java.stepDefinition"}
         )
         public class VideoFeedsRunner extends AbstractTestNGCucumberTests {
             public SetUp setUp;

             @Parameters("browser")
             @BeforeTest
             public void setup(String browserName) {
                 setUp = new SetUp();
                 setUp.initialiseBrowser(browserName);
                 SetUp.currentBrowserName.set(browserName);
                 setUp.setupBrowserproperties();
                 ExtentReportsGenerator.initializeReport();
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
         }
