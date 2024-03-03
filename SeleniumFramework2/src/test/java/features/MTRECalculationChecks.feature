Feature: MTRE Calculations Checks
  Scenario: MTRE Calculations
    Given User get data from excel sheet "MTRECalculations" for health checks
    When User search doc and run MTRE then move doc to auto route bin