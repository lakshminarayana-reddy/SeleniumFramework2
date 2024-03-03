Feature: Draft printing checks
  Scenario:
    Given User get data from excel sheet "DraftPrintingChecks" for health checks
    Then Verify draft printing in claim file view