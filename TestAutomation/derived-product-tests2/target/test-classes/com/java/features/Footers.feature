@DP2
Feature: Footer Links Verification
Description:Ensure that the hyperlinks in the footer are correct and do not contain duplicates
  Scenario: Verify hyperlinks in the footer and check for duplicates
    Given I am on DP2 home page
    When I scroll down to the footer
    Then I should see different links for various categories
    And I should extract all the hyperlinks in the footer into a CSV file
    And I should report if any duplicate hyperlinks are present
