Feature: PIP logs checks
  Scenario: Verify the pip logs
    Given User get data from excel sheet "PIPLogChecks" for health checks
    Then User navigates to Claim File View in medical bill review solution and verify claim document