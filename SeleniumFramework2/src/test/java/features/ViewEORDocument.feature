Feature: Verify EOR Document
  Scenario: Verify EOR Document
    Given User get data from excel sheet "EORDocument" for health checks
    Then Verify EOR document in claim file view