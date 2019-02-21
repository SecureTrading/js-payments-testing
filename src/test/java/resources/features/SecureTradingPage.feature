Feature: Test feature

  Scenario: Navigate to Secure Trading main page - correct
    Given I am on the website 'https://www.securetrading.com/'
    Then The page header should contain 'POWERING PAYMENTS.'
    And Visit pet endpoint
    And Make rest assured request
    And Make rest assured request on local host

  Scenario: Navigate to Secure Trading main page - error
    Given I am on the website 'https://www.securetrading.com/'
    Then The page header should contain 'WRONG HEADER.'