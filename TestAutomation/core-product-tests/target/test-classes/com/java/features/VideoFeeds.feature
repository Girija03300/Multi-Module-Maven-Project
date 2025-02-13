@CP2
Feature: Validate Video Feeds on CP Home Page

  Scenario: Verify Video Feeds in the New & Features section
    Given I am on the CP home page
    When I hover over the menu item
    And I click on News & Features
    Then I count the total number of video feeds
    And I count the video feeds that are present for more than Three Days
    