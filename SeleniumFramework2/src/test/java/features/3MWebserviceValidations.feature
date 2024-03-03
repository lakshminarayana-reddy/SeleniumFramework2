Feature: 3M WebService validation
  Scenario: 3M Webservice validation
    Given User get data from excel sheet "3MWebservice" for health checks
    Then Verify draft printing for 3MWebservice in claim file view