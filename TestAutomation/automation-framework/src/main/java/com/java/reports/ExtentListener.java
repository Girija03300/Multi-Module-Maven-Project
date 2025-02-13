package com.java.reports;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.java.base.SetUp;


public class ExtentListener implements ITestListener {

	@Override
	public void onTestSuccess(ITestResult result) {		
		ExtentReportsGenerator.reportTestSuccess();
	}
	@Override
	public void onTestFailure(ITestResult result) {		
		ExtentReportsGenerator.reportTestFailure(result);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		ExtentReportsGenerator.reportTestSkipped();
	}

	@Override
	public void onFinish(ITestContext context) {		
		ExtentReportsGenerator.closeExtentReports();		
	}		
	
	}
