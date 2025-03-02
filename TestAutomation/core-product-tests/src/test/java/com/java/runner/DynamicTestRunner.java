package com.java.runner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class DynamicTestRunner {

	public static final String CURRENT_DIRECTORY = System.getProperty("user.dir");
	public static final String FEATURE_PATH = CURRENT_DIRECTORY +"/src/test/java/com/java/features";
	public static final String RUNNER_PACKAGE = "com.java.runner";
	public static final String TESTNG_PATH = CURRENT_DIRECTORY+"/src/test/resources/testng.xml";
	public static final String RUNNER_PATH = CURRENT_DIRECTORY+"/src/test/java/com/java/runner/";
	
	public static void main(String args[])
	{
		File featureFolder = new File(FEATURE_PATH);
		if(!featureFolder.exists())
		{
			System.out.println("Features folder does not exist");
		}
		File[] FeatureFiles = featureFolder.listFiles();
		List<File> featureFilesList = new ArrayList<>();
		for(File file:FeatureFiles)
		{
			if(file.getName().endsWith(".feature"))
			{
			featureFilesList.add(file);	
			System.out.println("Feature Files are " + file.getName());
			}
		}
		
		StringBuilder testNGSuite =new StringBuilder( """
				<?xml version="1.0" encoding="UTF-8"?>
				<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd">
					<suite name="Suite" parallel="tests" thread-count="2">
					<listeners>
					<listener class-name="com.java.reports.ExtentListener"/>
					</listeners>				
				""");
		for(File featureFile:featureFilesList)
		{
			String className = featureFile.getName().replace(".feature","Runner");
			generateRunnerClass(featureFile.getName(),className);
			testNGSuite.append(String.format("""														
					   <test name="Core Product Tests in Chrome">
					   <parameter name="browser" value="Chrome"></parameter>  
					   <parameter name="cucumber.options" value="@CP2"></parameter>
					  <classes>					  
					  <class name= "%s.%s" ></class>
					  </classes>
					  </test>
					 <test name="Core Product Tests in Edge">
					   <parameter name="browser" value="Edge"></parameter>
					   <parameter name="cucumber.options" value="@CP2"></parameter>
					  <classes>
					  <class name= "%s.%s"></class>
					  </classes>
					  </test>   	
					""",RUNNER_PACKAGE,className,RUNNER_PACKAGE,className));
		}
		testNGSuite.append("</suite>");

			try {
				Files.write(Paths.get(TESTNG_PATH), testNGSuite.toString().getBytes());
				System.out.println("Updated testng.xml file");
			} catch (IOException e) {
				System.out.println("Not able to write to testng.xml file");				
			}				
			
			
		
	}
	public static void generateRunnerClass(String featureFileName, String className)
	{
		String runnerCode = String.format("""
			package %s;
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
             features = "src/test/java/com/java/features/%s",
             glue = {"com.java.base", "com.java.stepDefinition"},
             plugin = {
            			"pretty",
            			 "json:target/cucumber.json",
            			 "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
            			 "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"  
            			    },
      	    monochrome = true
         )
            public class %s extends AbstractTestNGCucumberTests {
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
            """,RUNNER_PACKAGE,featureFileName,className);	
		
		try {
			FileWriter writer = new FileWriter(RUNNER_PATH +className+".java");
			writer.write(runnerCode);
			writer.close();
			System.out.println("Generated " +className+".java");
		} catch (Exception e) {
			System.out.println("Not able to write into Feature runner file");
		}
		
	}
	
	 

}
