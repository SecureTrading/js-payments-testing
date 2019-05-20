Feature: Credit and debit card payments
  As a user
  I want to use various payment methods using correct and incorrect credentials
  In order to check full payment functionality

  Background:
    Given User opens page with payment form

  @passingTests @testEnv @cardinalCommerce @mockData
  Scenario Outline: Cardincal Commerce (card enrolled Y) - checking payment status for <paymentCode> response code
    When User fills payment form with credit card number "4111110000000211", expiration date "12/30" and cvc "123"
    And THREEDQUERY response set to "entrolled Y"
    And ACS response set to "OK"
    And User clicks Pay button - AUTH response set to <paymentCode>
    Then User will see information about payment status <paymentStatusMessage>
    And User will see that notification frame has <color> color
    Examples:
      | paymentCode | paymentStatusMessage                      | color |
      | 0           | "Payment has been successfully processed" | green |
      | 30000       | "Invalid field"                           | red   |
      | 50000       | "Socket receive error"                    | red   |
      | 60022       | "Unauthenticated"                         | red   |
      | 70000       | "Decline"                                 | red   |
      | 99999       | "Unknown error"                           | red   |

  @passingTests @testEnv @cardinalCommerce @mockData
  Scenario Outline: Cardincal Commerce (card not-enrolled N) - checking payment status for <paymentCode> response code
    When User fills payment form with credit card number "4000000000001059", expiration date "01/22" and cvc "123"
    And THREEDQUERY response set to "not-entrolled N"
    And User clicks Pay button - AUTH response set to <paymentCode>
    Then User will see information about payment status <paymentStatusMessage>
    And User will see that notification frame has <color> color
    Examples:
      | paymentCode | paymentStatusMessage                      | color |
      | 0           | "Payment has been successfully processed" | green |
      | 60022       | "Unauthenticated"                         | red   |
      | 70000       | "Decline"                                 | red   |

  @passingTests @testEnv @cardinalCommerce @mockData
  Scenario Outline: Cardincal Commerce (card not-enrolled U) - checking payment status for <paymentCode> response code
    And User fills payment form with credit card number "4111110000000401", expiration date "01/22" and cvc "123"
    And THREEDQUERY response set to "not-entrolled U"
    And User clicks Pay button - AUTH response set to <paymentCode>
    Then User will see information about payment status <paymentStatusMessage>
    And User will see that notification frame has <color> color
    Examples:
      | paymentCode | paymentStatusMessage                      | color |
      | 0           | "Payment has been successfully processed" | green |
      | 60022       | "Unauthenticated"                         | red   |
      | 70000       | "Decline"                                 | red   |

  @testEnv @cardinalCommerce @mockData
  Scenario Outline: Cardincal Commerce - check THREEDQUERY response for code: <paymentCode>
    When User fills payment form with credit card number "4111110000000211", expiration date "01/22" and cvc "123"
    And THREEDQUERY response set to "<paymentCode>"
    And User clicks Pay button
    Then User will see information about payment status <paymentStatusMessage>
    And User will see that notification frame has <color> color
    Examples:
      | paymentCode | paymentStatusMessage              | color |
      | 30000       | "Invalid field"                   | red   |
      | 60031       | "Invalid acquirer for 3-D Secure" | red   |

  @testEnv @cardinalCommerce @mockData
  Scenario Outline: Cardincal Commerce (card enrolled Y) - check ACS response for code: <paymentCode>
    When User fills payment form with credit card number "4111110000000211", expiration date "01/22" and cvc "123"
    And THREEDQUERY response set to "entrolled Y"
    And ACS response set to "<paymentCode>"
    And User clicks Pay button
    Then User will see information about payment status <paymentStatusMessage>
    And User will see that notification frame has <color> color
    Examples:
      | paymentCode | paymentStatusMessage | color |
      |             |                      | red   |
      |             |                      | red   |

  @testEnv @mockData
  Scenario Outline: Successful payment using most popular Credit Cards: <cardType>
    When User fills payment form with credit card number "<cardNumber>", expiration date "<expirationDate>" and cvc "<cvc>"
    And THREEDQUERY response set to "not-entrolled N"
    And User clicks Pay button - AUTH response set to <paymentCode>
    And User clicks Pay button
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
    And User will see the same provided data on animated credit card <formattedCardNumber>, <expirationDate> and <cvc>
    And User will see that animated card is flipped, except for "AMEX"
    Examples:
      | cardNumber          | formattedCardNumber    | expirationDate | cvc  | cardType     |
      | 340000000000611     | 3400 000000 00611      | 12/22          | 1234 | AMEX         |
      | 1801000000000901    | 1801 0000 0000 0901    | 12/22          | 123  | ASTROPAYCARD |
      | 3000000000000111    | 3000 000000 000111     | 12/22          | 123  | DINERS       |
      | 6011000000000301    | 6011 0000 0000 0301    | 12/22          | 123  | DISCOVER     |
      | 3528000000000411    | 3528 0000 0000 0411    | 12/22          | 123  | JCB          |
      | 5000000000000611    | 5000 0000 0000 0611    | 12/22          | 123  | MAESTRO      |
      | 5100000000000511    | 5100 0000 0000 0511    | 12/22          | 123  | MASTERCARD   |
      | 3089500000000000021 | 3089 5000 0000 0000021 | 12/22          | 123  | PIBA         |
      | 4111110000000211    | 4111 1100 0000 0211    | 12/22          | 123  | VISA         |

  #ToDo - Confirm validation messages
  @testEnv
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

  @testEnv
  Scenario: Filling 3-number of cvc code for AMEX card
    When User fills payment form with credit card number "340000000000611", expiration date "12/22" and cvc "123"
    Then User will see validation message "Your card's security code is incomplete." under "number" field

  @testEnv
  Scenario: Submit payment form without data - fields validation
    When User clicks Pay button
    Then User will see validation message "TODO" under all fields

  @passingTests @walletTest @testEnv @visaTest @mockData
  Scenario Outline: Visa Checkout - checking payment status for <paymentCode> response code
    When User chooses Visa Checkout as payment method - response set to <paymentCode>
    Then User will see information about payment status <paymentStatusMessage>
    And User will see that notification frame has <color> color
    Examples:
      | paymentCode | paymentStatusMessage                      | color  |
      | Success     | "Payment has been successfully processed" | green  |
      | Error       | "An error occurred"                       | red    |
      | Cancel      | "Payment has been cancelled"              | yellow |

  @passingTests @walletTest @testEnv @appleTest @mockData
  Scenario Outline: ApplePay - checking payment status for <paymentCode> response code
    When User chooses ApplePay as payment method - response set to <paymentCode>
    Then User will see information about payment status <paymentStatusMessage>
    And User will see that notification frame has <color> color
    Examples:
      | paymentCode | paymentStatusMessage                      | color  |
      | Success     | "Payment has been successfully processed" | green  |
      #    | Error       | "An error occurred"                       | red    |
      | Decline     | "Decline"                                 | red    |
      | Cancel      | "Payment has been cancelled"              | yellow |

  @prod @withoutMock
  Scenario: Check if Cardinal Commerce authentication modal is displayed
    When User fills merchant data with name "John Smith", email "john@test.com", phone "48456789987"
    And User fills payment form with credit card number "4000000000000002", expiration date "01/22" and cvc "123"
    And User clicks Pay button
    Then User will see Cardinal Commerce authentication modal
