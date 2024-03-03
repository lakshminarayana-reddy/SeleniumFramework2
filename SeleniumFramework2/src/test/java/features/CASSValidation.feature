Feature: CASS Validation
  Scenario: CASS Validation
    Given User get data from excel sheet "CASS" for health checks
    Then Verify the address popup for billing provider in data capture