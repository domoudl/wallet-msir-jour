Feature: Transaction

  Scenario: Successful money transfer
    Given a source account with balance 100.0
    And a destination account
    When I transfer 20.0 from source account to destination account
    Then the transfer should be successful
    And the source account balance should be 80.0
    And the destination account balance should be 20.0

  Scenario: Insufficient balance for money transfer
    Given a source account with balance 30.0
    And a destination account
    When I transfer 50.0 from source account to destination account
    Then the transfer should fail with "Solde Insuffisant"
    And the source account balance should remain 30.0
    And the destination account balance should remain 0.0

