Feature: Test feature

  Scenario: Navigate to Secure Trading main page - correct version 1
    Given User is on the website 'https://www.securetrading.com/'
    Then The page header contains 'POWERING PAYMENTS.'
    And User visits pet endpoint version 1

  Scenario: Navigate to Secure Trading main page - correct version 2
    Given User is on the website 'https://www.securetrading.com/'
    Then The page header contains 'POWERING PAYMENTS.'
    And User visits pet endpoint version 2

  Scenario: Navigate to Secure Trading main page - correct version 3
    Given User is on the website 'https://www.securetrading.com/'
    Then The page header contains 'POWERING PAYMENTS.'
    And User visits pet endpoint version 1

  Scenario: Navigate to Secure Trading main page - correct version 4
    Given User is on the website 'https://www.securetrading.com/'
    Then The page header contains 'POWERING PAYMENTS.'
    And User visits pet endpoint version 2

#  Scenario: Navigate to Secure Trading main page - error
#    Given User is on the website 'https://www.securetrading.com/'
#    Then The page header contains 'POWERING PAYMENTS ERROR.'