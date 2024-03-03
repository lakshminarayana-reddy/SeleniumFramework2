Feature: Application Checks
  Scenario: Web flow with USAA client
    Given User get data from excel sheet "ApplicationChecks" for health checks
    When Navigate to application and login with credentials into client and verify home page