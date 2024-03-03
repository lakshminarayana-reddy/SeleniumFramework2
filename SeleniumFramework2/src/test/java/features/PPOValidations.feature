Feature: Validations for PPO
  Scenario: Validations for PPO
    Given User get data from excel sheet "Validations" for health checks
    # Verify processor name, bin desc, ppo calculations, ppo name, comment
    Then Verify ppo validations and discount calculations for doc