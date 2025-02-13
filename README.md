# Test Automation Framework

## Overview
This is a Selenium-based Test Automation Framework developed using Java, TestNG, and Cucumber framework.

## features

- Selenium WebDriver used for browser automation
- TestNG & Cucumber framework for test execution and BDD support
- Multi-module Maven project for modular and scalable architecture
- Page Object Model Design Pattern
- Page Factory for initialsing the web elements
- Log4j for logging
- Extent reports for reporting
- Parallel execution for faster test runs
- Test Data in JSON for better data managament
- Configuration and locators are in properties

## Prerequisites
- Java 
- Maven
- TestNG
- Selenium WebDriver
- Cucumber

## Project Structure
```
Project Root

|---TestAutomation                              #Parent Maven module
|------Automation Framework                     #Child Module 1 #Shared Module
│---------- src/main/java
|-----------------com/java/base      
|-------------------------pageBase.java         #Intialises Page Factory.
|-------------------------SetUp.java            #Intialises Properties files and drivers.
|-------------------------testBase.java         #Cucumber Hooks Methods navigating to application home page.
|------------------com/java/config
|--------------------------config.properties    #contains data related to configurations
|--------------------------OR.properties        #dynamic locators
|------------------com/java/data
|--------------------------TestData.json        #Test Data
|------------------com/java/dataReader
|--------------------------fileWriter.java      #Writing the data to files
|--------------------------jsonReader.java      #reading data from json file
|------------------com/java/logs
|--------------------------log4j.properties     #logging the data
|------------------com/java/reports
|--------------------------ExtentListener.java          #ITestListener for listening the events
|--------------------------ExtentReportsGenerator.java  #Extent reports generation
|------------------com/java/utils
|--------------------------Utilities.java       #Common methods(click,sendKeys,actions etc....)
|--------reports/extent.html                    #extent.html reports
|--------logs                                   #logs
|---------pom.xml                               #Maven dependencies

|------core-product-test                         #Child Module 1
│---------- src/test/java
|-----------------com/java/features      
|-------------------------VideoFeeds.feature             #CP Test Case Feature File
|------------------com/java/pages                     #Page Object Model   
|--------------------------CoreProductHomePage.java    #Methods related to only Core Product Home Page
|--------------------------CoreProductNewsPage.java    #Methods related to only Core Product News Page
|------------------com/java/runner
|--------------------------DynamicTestRunner         #Creates runner file for each feature and updates testng.xml
|--------------------------VideoFeedsRunner            #Generated runner class during runtime
|------------------com/java/stepDefinition
|--------------------------VideoFeeds.java                 #StepDefintion file 
│---------- src/test/resources
|-------------testng.xml                #Execution of the Tests created during runtime by DynamicTestRunner Class
|--------reports/extent.html                    #Extent reports
|--------logs                                   #logs
|----pom.xml                                    #Maven Dependencies 
|------derived-product-test1                    #Child Module 2
│---------- src/test/java
|-----------------com/java/features      
|-------------------------Slides.feature             #DP1 Test Case Feature File
|------------------com/java/pages                    #Page Object Model   
|--------------------------DeliveredProduct1HomePage #Methods related to only Derived Product1 Home Page
|------------------com/java/runner
|--------------------------DynamicTestRunner         #Creates runner file for each feature and updates testng.xml
|--------------------------SlidesRunner              #Generated runner class during runtime
|------------------com/java/stepDefinition
|--------------------------Slides.java               #StepDefintion file 
│---------- src/test/resources
|-------------testng.xml                #Execution of the Tests created during runtime by DynamicTestRunner Class
|--------reports/extent.html                         #Extent reports
|--------logs                                        #logs
|----pom.xml                                         #Maven Dependencies 
|------derived-product-test2                          #Child Module 3
│---------- src/test/java
|-----------------com/java/features      
|-------------------------Footers.feature             #DP2 Test Case Feature File
|------------------com/java/pages                     #Page Object Model   
|--------------------------DeliveredProduct2HomePage  #Methods related to only DP2 Home Page
|------------------com/java/runner
|--------------------------DynamicTestRunner         #Creates runner file for each feature and updates testng.xml
|--------------------------FootersRunner               #Generated runner class during runtime
|------------------com/java/stepDefinition
|--------------------------Footers.java                 #StepDefintion file 
│---------- src/test/resources
|-------------testng.xml                #Execution of the Tests created during runtime by DynamicTestRunner Class
|--------reports/extent.html                    #Extent reports
|--------logs                                   #logs
|--------FooterLinks/EdgefooterLinks.csv        #Output csv file for DP2 test executed in Edge
|--------FooterLinks/ChromefooterLinks.csv      #Output csv file for DP2 test executed in Chrome
|----pom.xml                                    #Maven Dependencies 
```

## Parallel Execution
- Run DynamicTestRunner class in com.java.runner package --------------> testng.xml file gets generated in src/test/resources
- Run generated testng.xml as testNG Suite
```

## Reports & Logging
- **Extent Reports**: Generated in the `reports` folder after execution in each maven module
- **TestNG Reports**: Available in the `test-output` folder.
- **Logs**: Stored in `logs` directory for debugging.


##Execution

use mvn clean test command to run the test cases
