package com.java.reports;
import java.io.File;
import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.java.base.SetUp;
import com.java.base.TestBase;
import com.java.utils.Utilities;
public class ExtentReportsGenerator extends Utilities {
	private static ExtentReports extent;
	private static ExtentSparkReporter htmlReporter;
	public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	private static final String Author="Girija";
	private static final String Gmail="girijakallepalli69gmail.com";
	private static final String extentReportPath=SetUp.CURRENTDIRECTORY+getconfigPropertyValue("extentReportPath");

	public static synchronized void initializeReport() {	
	   extent = new ExtentReports();
       htmlReporter = new ExtentSparkReporter(extentReportPath);
       extent.attachReporter(htmlReporter);
       reportDetails();
       }
	
	public static void reportDetails()
	{		
		extent.setSystemInfo("Author", Author);
		extent.setSystemInfo("Gmail",Gmail);
		htmlReporter.config().setReportName(getconfigPropertyValue("ReportName"));
		htmlReporter.config().setDocumentTitle(getconfigPropertyValue("DocumentTitle"));		
	}

	public static void startTest(String name)
	{
		test.set(extent.createTest(name+" in " + SetUp.getBrowserName()));
	}
	public static void reportTestSuccess() 
	{
		getTest().log(Status.PASS, MarkupHelper.createLabel(TestBase.scenarioName + " in " + SetUp.getBrowserName() +" got passed ", ExtentColor.GREEN));
	    }
	public static  void reportTestFailure(ITestResult result)
	{
		getTest().log(Status.FAIL,result.getThrowable());
		getTest().log(Status.FAIL,TestBase.scenarioName + " in "+ SetUp.getBrowserName() , MediaEntityBuilder.createScreenCaptureFromBase64String(Utilities.captureScreenshot()).build());
	}
	public  static void reportTestSkipped()
	{
		getTest().log(Status.SKIP, MarkupHelper.createLabel(TestBase.scenarioName + " in "+ SetUp.getBrowserName() ,ExtentColor.YELLOW));
	}
	public static  void closeExtentReports()
	{
	        extent.flush();	      
	        SetUp.getDriver().quit();  
	    }	
	  public static ExtentTest getTest() {
	        return test.get(); 
	    }
}

