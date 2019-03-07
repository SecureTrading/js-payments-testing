Feature: Credit and debit card payments
  As a user
  I want to use various payment methods using correct and incorrect credentials
  In order to check full payment functionality

  Background:
    Given User opens page with payment form

  @mockData
  Scenario Outline: Checking payment status for various response code
    When User fills payment form with credit card number "4111110000000211", cvc "123" and expiration date "12/22"
    And User clicks Pay button and set payment code <paymentCode>
    Then User will see information about payment status <paymentStatusMessage>
    Examples:
      | paymentCode | paymentStatusMessage  |
      | success     | "Successful Payment!" |
      | error_1015  | "ToDo"                |
      | error_1035  | "TODo"                |

  @mockData
  Scenario Outline: Successful payment using most popular Credit Cards
    When User fills payment form with credit card number <cardNumber>, cvc <cvc> and expiration date <expirationDate>
    And  User clicks Pay button and set payment code 'success'
    Then User will see information about payment status 'Successful Payment!'
    Examples:
      | cardNumber       | cvc  | expirationDate | cardType   |
      | 340000000000611  | 1234 | 12/22          | AMEX       |
      | 5100000000000511 | 123  | 12/22          | MASTERCARD |
      | 4111110000000211 | 123  | 12/22          | VISA       |

  Scenario Outline: Credit card recognition and validate date on animated card
    When User fills payment form with credit card number <cardNumber>, cvc <cvc> and expiration date <expirationDate>
    Then User will see card icon connected to card type <cardType>
    And User will see the same provided data on animated credit card <cardNumber>, <cvc> and <expirationDate>
    Examples:
      | cardNumber       | cvc  | expirationDate | cardType     |
      | 340000000000611  | 1234 | 12/22          | AMEX         |
      |                  | 123  | 12/22          | ASTROPAYCARD |
      | 3000000000000111 | 123  | 12/22          | DINERS       |
      | 6011000000000301 | 123  | 12/22          | DISCOVER     |
      | 3528000000000411 | 123  | 12/22          | JCB          |
      | 5000000000000611 | 123  | 12/22          | MAESTRO      |
      | 5100000000000511 | 123  | 12/22          | MASTERCARD   |
      |                  | 123  | 12/22          | PIBA         |
      | 4111110000000211 | 123  | 12/22          | VISA         |

  Scenario Outline: Filling payment form with incomplete data - fields validation
    When User fills payment form with incorrect or missing data: card number <cardNumber>, cvc <cvc> and expiration date <expiration>
    And User clicks Pay button
    Then User will see validation message <message> under <fieldType> field
    Examples:
      | cardNumber       | cvc | expiration | message | fieldType  |
      |                  | 123 | 12/22      | TODO    | number     |
      | 3000000000000012 |     | 12/22      | TODO    | cvc        |
      | 6011000000000202 | 123 |            | TODO    | expiryDate |

  Scenario: Submit payment form without data - fields validation
    When User clicks Pay button
    Then User will see validation message "TODO" under all fields
