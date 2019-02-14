Feature: Credit Card
  As a user
  I want to fill payment form using correct credentials
  In order to make successful payment

  Background:
    Given I open page with payment form

  @happyPath
  Scenario Outline: Successful payment using correct credentials
    When I fill payment form with credit card number <cardNumber>,  cvv |cvv| and expiration data <expiration>
    And I click Pay button
    Then I should see information about successful payment
    Examples:
      | cardNumber       | cvv | expiration | //Card Type
      | 340000000000611  | 123 | 12/22      | //AMEX
      | 3000000000000111 | 123 | 12/22      | //DINERS
      | 6011000000000301 | 123 | 12/22      | //DISCOVER
      | 3528000000000411 | 123 | 12/22      | //JCB
      | 5000000000000611 | 123 | 12/22      | //MAESTRO
      | 5100000000000511 | 123 | 12/22      | //MASTERCARD
      | 5124990000000101 | 123 | 12/22      | //MASTERCARDDEBIT
      | 4370000000000061 | 123 | 12/22      | //VPAY
      | 4111110000000211 | 123 | 12/22      | //VISA aka Visa Debit
      | 3000000000000111 | 123 | 12/22      | //DELTA1
      | 4245190000000311 | 123 | 12/22      | //ELECTRON
      | 4484000000000411 | 123 | 12/22      | //PURCHASING

  Scenario Outline: Credit card icon recognition
    When I fill credit card number field <cardNumber>
    Then I should see card icon connected to card type
    Examples:
      | cardNumber       | //Card Type
      | 340000000000611  | //AMEX
      | 3000000000000111 | //DINERS
      | 6011000000000301 | //DISCOVER
      | 3528000000000411 | //JCB
      | 5000000000000611 | //MAESTRO
      | 5100000000000511 | //MASTERCARD
      | 5124990000000101 | //MASTERCARDDEBIT
      | 4370000000000061 | //VPAY
      | 4111110000000211 | //VISA aka Visa Debit
      | 3000000000000111 | //DELTA1
      | 4245190000000311 | //ELECTRON
      | 4484000000000411 | //PURCHASING


  Scenario Outline: Declined payment using wrong card number
    When I fill payment form with credit card number <cardNumber>,  cvv |cvv| and expiration data <expiration>
    And I click Pay button
    Then I should see information about declined payment
    Examples:
      | cardNumber       | cvv | expiration | //Card Type
      | 340000000000512  | 123 | 12/22      | //AMEX
      | 3000000000000012 | 123 | 12/22      | //DINERS
      | 6011000000000202 | 123 | 12/22      | //DISCOVER
      | 3528000000000312 | 123 | 12/22      | //JCB
      | 5000000000000512 | 123 | 12/22      | //MAESTRO
      | 5100000000000412 | 123 | 12/22      | //MASTERCARD
      | 5124990000000002 | 123 | 12/22      | //MASTERCARDDEBIT
      | 4370000000000012 | 123 | 12/22      | //VPAY
      | 4111110000000112 | 123 | 12/22      | //VISA aka Visa Debit
      | 4310720000000042 | 123 | 12/22      | //DELTA1
      | 4245190000000212 | 123 | 12/22      | //ELECTRON
      | 4484000000000312 | 123 | 12/22      | //PURCHASING


  //Confirm validation message
  Scenario Outline: Filling payment form form with incomplete data - fields validation
    When I fill payment form with incompleted credentilas: card number <cardNumber>,  cvv |cvv| and expiration data <expiration>
    And I click Pay button
    Then I should see validation message <validation> under empty field
    Examples:
      | cardNumber       | cvv | expiration | validation                  |
      |                  | 123 | 12/22      | Please fill out this field. |
      | 3000000000000012 |     | 12/22      | Please fill out this field. |
      | 6011000000000202 | 123 |            | Please fill out this field. |

  //To confirm
  Scenario: Submiy payment form without data - fields validation
    When I click Pay button
    Then I should see validation message "Please fill out this field." under credit card number field

  //Confirm if we need more examples
  //Validation messsage
  Scenario Outline: Filling payment form with incomplete Credit Card number - field validation
    When I fill payment form with incomplete credit card number <cardNumber>,  cvv |cvv| and expiration data <expiration>
    And I click Pay button
    Then I should see validation message "Your card number is incomplete" under credit card number field
    Examples:
      | cardNumber      | cvv | expiration |
      | 4               | 123 | 12/22      |
      | 4111            | 123 | 12/22      |
      | 41111100        | 123 | 12/22      |
      | 411111000000    | 123 | 12/22      |
      | 411111000000021 | 123 | 12/22      |


  //Confirm if we need more examples
  //Validation messsage
  Scenario Outline: Filling payment form with with outdated expiration date - field validation
    When I fill payment form with credentilas: card number <cardNumber>,  cvv |cvv| and expiration data <expiration>
    And I click Pay button
    Then I should see validation message "Your card's expiration year is in the past." under card number field
    Examples:
      | cardNumber       | cvv | expiration |
      | 4111110000000211 | 123 | 10/18      |
      | 4111110000000211 | 123 | 01/19      |


  Scenario: Check CVV/CVC tooltip info
    When I click tooltip icon next to CVV/CVC
    Then I should see information about this field
















