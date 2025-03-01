package com.java.base;

import org.openqa.selenium.support.PageFactory;


public class PageBase{

	    public PageBase() {
			PageFactory.initElements(SetUp.getDriver(), this);   //initialising page factory all other page classes user super() 
	    }
}

	
