Feature: Test feature

  Scenario: Navigate to Secure Trading main page - correct version 1
    Given User visits pet endpoint version 1
    Then The page content contains '1111'

  Scenario: Navigate to Secure Trading main page - correct version 2
    Given User visits pet endpoint version 2
    Then The page content contains '2222'