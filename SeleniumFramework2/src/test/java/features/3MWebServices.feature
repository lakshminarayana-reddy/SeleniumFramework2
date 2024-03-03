Feature: 3M WebServices
  Scenario: 3M Webservices
    Given User get data from excel sheet "3MWebservice" for health checks
    When User search doc and run MTRE then move doc to auto route bin