@CP2
Feature: Validate Video Feeds on CP Home Page
As a user,  
  I want to count the toatl number of video feeds on the CP home page,  
  and i wanted to count the number of video feeds that are present greater than 3 days.
  Scenario: Verify Video Feeds in the New & Features section
    Given I am on the CP home page with title "Golden State Warriors"
    When I hover over the menu item
    And I click on News & Features
    Then I count the total number of video feeds
    And I count the video feeds that are present for more than "3" Days
    