Feature: Move Doc to Nurse Review
  Scenario: Move doc to nurse review
    Given User get data from excel sheet "3MWebservice" for health checks
    Then User search doc and move doc to nurse review bin
    When User get data from excel sheet "MTRECalculations" for health checks
    Then User search doc and move doc to nurse review bin