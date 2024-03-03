Feature: Database connection checks
  Scenario: Database connection checks
    Given User get data from excel sheet "Database" for health checks
    Then Verify the database connections