Feature: Credit and debit card payments
  As a user
  I want to use various payment methods using correct and incorrect credentials
  In order to check full payment functionality

  Background:
    Given User opens page with payment form

  #ToDo - check success paymentCode
  @mockData
  Scenario Outline: Checking payment status for response code: <paymentCode>
    When User fills payment form with credit card number '4111110000000211', cvc '123' and expiration date '12/22'
    And User clicks Pay button - response set to <paymentCode>
    Then User will see information about payment status <paymentStatusMessage>
    Examples:
      | paymentCode | paymentStatusMessage   |
      | success     | "Successful Payment!"  |
      | 30000       | "Field error"          |
      | 50000       | "Decline error"        |
      | 70000       | "Socket receive error" |

  @mockData
  Scenario Outline: Successful payment using most popular Credit Cards: <cardType>
    When User fills payment form with credit card number <cardNumber>, cvc <cvc> and expiration date <expirationDate>
    And  User clicks Pay button - response set to 'success'
    Then User will see information about payment status "Payment successful!"
    Examples:
      | cardNumber       | cvc  | expirationDate | cardType   |
      | 340000000000611  | 1234 | 12/22          | AMEX       |
      | 5100000000000511 | 123  | 12/22          | MASTERCARD |
      | 4111110000000211 | 123  | 12/22          | VISA       |

  Scenario Outline: Credit card recognition for <cardType> and validate date on animated card
    When User fills payment form with credit card number <cardNumber>, cvc <cvc> and expiration date <expirationDate>
    Then User will see card icon connected to card type <cardType>
    And User will see the same provided data on animated credit card <cardNumber>, <cvc> and <expirationDate>
    Examples:
      | cardNumber          | cvc  | expirationDate | cardType     |
      | 340000000000611     | 1234 | 12/22          | AMEX         |
      | 1801000000000901    | 123  | 12/22          | ASTROPAYCARD |
      | 3000000000000111    | 123  | 12/22          | DINERS       |
      | 6011000000000301    | 123  | 12/22          | DISCOVER     |
      | 3528000000000411    | 123  | 12/22          | JCB          |
      | 5000000000000611    | 123  | 12/22          | MAESTRO      |
      | 5100000000000511    | 123  | 12/22          | MASTERCARD   |
      | 3089500000000000021 | 123  | 12/22          | PIBA         |
      | 4111110000000211    | 123  | 12/22          | VISA         |

   #ToDo - Confirm validation messages
  Scenario Outline: Filling payment form with incomplete data - validation of <fieldType> field
    When User fills payment form with incorrect or missing data: card number <cardNumber>, cvc <cvc> and expiration date <expiration>
    And User clicks Pay button
    Then User will see validation message <message> under <fieldType> field

    Examples:
      | cardNumber       | cvc | expiration | message                                       | fieldType  |
      |                  | 123 | 12/22      | "Your card number is incomplete."             | number     |
      | 41111100         | 123 | 12/22      | "Your card number is incomplete."             | number     |
      | 6011000000000307 | 123 | 12/22      | "Your card number is invalid."                | number     |
      | 3000000000000012 |     | 12/22      | "Your card's security code is incomplete."    | cvc        |
      | 3000000000000012 | 12  | 12/22      | "Your card's security code is incomplete."    | cvc        |
      | 6011000000000202 | 123 |            | "Your card's expiration date is incomplete."  | expiryDate |
      | 6011000000000202 | 123 | 12         | "Your card's expiration date is incomplete."  | expiryDate |
      | 6011000000000202 | 123 | 10/18      | "Your card's expiration year is in the past." | expiryDate |

  Scenario: Filling 3-number of cvc code for AMEX card
    When User fills payment form with credit card number '340000000000611', cvc '123' and expiration date '12/22'
    Then User will see validation message "Your card's security code is incomplete." under "number" field

  Scenario: Submit payment form without data - fields validation
    When User clicks Pay button
    Then User will see validation message "TODO" under all fields

  @visaTest
  Scenario Outline: Visa Checkout - checking payment status for <paymentCode> response code
    When User chooses "visaCheckout" as payment method - response set to <paymentCode>
    Then User will see information about payment status <paymentStatusMessage>
    Examples:
      | paymentCode | paymentStatusMessage                      |
      | Success     | "Payment has been successfully proceeded" |
      | Error       | "An error occurred"                       |
      | Cancel      | "Payment has been canceled"               |