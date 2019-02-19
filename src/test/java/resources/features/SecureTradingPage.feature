Feature: Test feature

  Scenario: Navigate to Secure Trading main page
    Given I am on the website 'https://www.securetrading.com/'
    Then The page header should contain 'POWERING PAYMENTS.'

  Scenario: Navigate to Secure Trading main page 2
    Given I am on the website 'https://www.securetrading.com/'
    Then The page header should contain 'WRONG PAYMENTS.'