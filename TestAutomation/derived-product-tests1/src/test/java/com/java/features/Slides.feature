@DP1
Feature: Validate Slides on DP1 Home Page

Scenario: Validate slide count, titles, and duration
    Given I am on DP1 home page with title "Sixers - The official site of the NBA for the latest NBA Scores"
    When I count the number of slides present below the Tickets menu
    Then I should see the expected number of slides are "5"
    When I get the title of each slide
    Then I validate the slide titles with expected test data
    When I measure the duration each slide displayed
    Then I validate the duration with the expected duration