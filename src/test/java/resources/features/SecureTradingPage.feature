Feature: Test feature

  Scenario: Navigate to Secure Trading main page
    Given I am on the website 'https://www.securetrading.com/'
    Then The page header should contain 'POWERING PAYMENTS.'
    And Visit pet endpoint
    And Make rest assured request