Feature: Credit and debit card payments
  As a user
  I want to use various payment methods using correct and incorrect credentials
  In order to check full payment functionality

  Background:
    Given User opens page with payment form

  @cardinalCommerce @mockData
  Scenario Outline: Checking payment status for response code: <paymentCode>
    When User fills merchant data with name "John Smith", email "john@test.com", phone "48456789987"
    And User fills payment form with credit card number "4000000000000002", expiration date "01/22" and cvc "123"
    And User clicks Pay button - response set to <paymentCode>
    Then User will see information about payment status <paymentStatusMessage>
    And User will see that notification frame has <color> color
    Examples:
      | paymentCode | paymentStatusMessage   | color  |
      | 0           | "OK"                   | green  |
#      | 30000       | "Invalid field"        | yellow |
#      | 50000       | "Socket receive error" | red    |
#      | 60022       | Unauthenticated        | red    |
#      | 70000       | "Decline"              | red    |
#      | 99999       | "Unknown error"        | red    |

  @mockData
  Scenario Outline: Successful payment using most popular Credit Cards: <cardType>
    When User fills payment form with credit card number "<cardNumber>", expiration date "<expirationDate>" and cvc "<cvc>"
    And  User clicks Pay button - response set to 'success'
    Then User will see information about payment status "Payment successful!"
    Examples:
      | cardNumber       | expirationDate | cvc  | cardType   |
      | 340000000000611  | 12/22          | 1234 | AMEX       |
      | 5100000000000511 | 12/22          | 123  | MASTERCARD |
      | 4111110000000211 | 12/22          | 123  | VISA       |

  @animatedCard
  Scenario Outline: Credit card recognition for <cardType> and validate date on animated card
    When User fills payment form with credit card number "<cardNumber>", expiration date "<expirationDate>" and cvc "<cvc>"
    Then User will see card icon connected to card type <cardType>
    And User will see the same provided data on animated credit card <cardNumber>, <expirationDate> and <cvc>
    And User will see that animated card is flipped, except for "AMEX"
    Examples:
      | cardNumber          | expirationDate | cvc  | cardType     |
      | 340000000000611     | 12/22          | 1234 | AMEX         |
      | 1801000000000901    | 12/22          | 123  | ASTROPAYCARD |
      | 3000000000000111    | 12/22          | 123  | DINERS       |
      | 6011000000000301    | 12/22          | 123  | DISCOVER     |
      | 3528000000000411    | 12/22          | 123  | JCB          |
      | 5000000000000611    | 12/22          | 123  | MAESTRO      |
      | 5100000000000511    | 12/22          | 123  | MASTERCARD   |
      | 3089500000000000021 | 12/22          | 123  | PIBA         |
      | 4111110000000211    | 12/22          | 123  | VISA         |

   #ToDo - Confirm validation messages
  Scenario Outline: Filling payment form with incomplete data - validation of <fieldType> field
    When User fills payment form with incorrect or missing data: card number <cardNumber>, expiration date <expiration> and cvc <cvc>
    And User clicks Pay button
    Then User will see validation message "<message>" under "<fieldType>" field

    Examples:
      | cardNumber       | expiration | cvc | message                                       | fieldType  |
      |                  | 12/22      | 123 | "Your card number is incomplete."             | number     |
      | 41111100         | 12/22      | 123 | "Your card number is incomplete."             | number     |
      | 6011000000000307 | 12/22      | 123 | "Your card number is invalid."                | number     |
      | 3000000000000012 | 12/22      |     | "Your card's security code is incomplete."    | cvc        |
      | 3000000000000012 | 12/22      | 12  | "Your card's security code is incomplete."    | cvc        |
      | 6011000000000202 |            | 123 | "Your card's expiration date is incomplete."  | expiryDate |
      | 6011000000000202 | 12         | 123 | "Your card's expiration date is incomplete."  | expiryDate |
      | 6011000000000202 | 10/18      | 123 | "Your card's expiration year is in the past." | expiryDate |

  Scenario: Filling 3-number of cvc code for AMEX card
    When User fills payment form with credit card number "340000000000611", expiration date "12/22" and cvc "123"
    Then User will see validation message "Your card's security code is incomplete." under "number" field

  Scenario: Submit payment form without data - fields validation
    When User clicks Pay button
    Then User will see validation message "TODO" under all fields

  @visaTest @mockData
  Scenario Outline: Visa Checkout - checking payment status for <paymentCode> response code
    When User chooses Visa Checkout as payment method - response set to <paymentCode>
    Then User will see information about payment status <paymentStatusMessage>
    And User will see that notification frame has <color> color
    Examples:
      | paymentCode | paymentStatusMessage                      | color  |
      | Success     | "Payment has been successfully proceeded" | green  |
      | Error       | "An error occurred"                       | red    |
      | Cancel      | "Payment has been canceled"               | yellow |

  @appleTest @mockData
  Scenario Outline: ApplePay - checking payment status for <paymentCode> response code
    When User chooses ApplePay as payment method - response set to <paymentCode>
#    Then User will see information about payment status <paymentStatusMessage>
#    And User will see that notification frame has <color> color
    Examples:
      | paymentCode | paymentStatusMessage                      | color  |
      | Success     | "Payment has been successfully proceeded" | green  |
#      | Error       | "An error occurred"                       | red    |

  @prod @withoutMock
  Scenario: Check if Cardinal Commerce authentication modal is displayed
    When User fills merchant data with name "John Smith", email "john@test.com", phone "48456789987"
    And User fills payment form with credit card number "4000000000000002", expiration date "01/22" and cvc "123"
    And User clicks Pay button
    Then User will see Cardinal Commerce authentication modal
