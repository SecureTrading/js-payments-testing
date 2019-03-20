Feature: Credit and debit card payments
  As a user
  I want to use various payment methods using correct and incorrect credentials
  In order to check full payment functionality

  Background:
    Given User opens page with payment form

  Scenario: Test scenario
    When User fills merchant data name "John Test", email "test@test.pl", phone "654456654"
    When User fills payment form with credit card number '4111110000000211', cvc '123' and expiration date '12/22'
    And User clicks Pay button
