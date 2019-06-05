Feature: Credit and debit card payments
  As a user
  I want to use various payment methods using correct and incorrect credentials
  In order to check full payment functionality

  Background:
    Given User opens page with payment form

  @passingTests @cardinalCommerce @mockData
  Scenario Outline: Cardincal Commerce (card enrolled Y) - checking payment status for <paymentCode> response code
    When User fills payment form with credit card number "4111110000000211", expiration date "12/30" and cvc "123"
    And THREEDQUERY response set to "entrolled Y"
    And ACS response set to "OK"
    And User clicks Pay button - AUTH response set to "<paymentCode>"
    Then User will see information about payment status <paymentStatusMessage>
    And User will see that notification frame has "<color>" color
    Examples:
      | paymentCode | paymentStatusMessage                      | color |
      | 0           | "Payment has been successfully processed" | green |
      | 30000       | "Invalid field"                           | red   |
      | 50000       | "Socket receive error"                    | red   |
      | 60022       | "Unauthenticated"                         | red   |
      | 70000       | "Decline"                                 | red   |
      | 99999       | "Unknown error"                           | red   |

  @passingTests @cardinalCommerce @mockData
  Scenario Outline: Cardincal Commerce (card not-enrolled N) - checking payment status for <paymentCode> response code
    When User fills payment form with credit card number "4000000000001059", expiration date "01/22" and cvc "123"
    And THREEDQUERY response set to "not-entrolled N"
    And User clicks Pay button - AUTH response set to "<paymentCode>"
    Then User will see information about payment status <paymentStatusMessage>
    And User will see that notification frame has "<color>" color
    Examples:
      | paymentCode | paymentStatusMessage                      | color |
      | 0           | "Payment has been successfully processed" | green |
      | 60022       | "Unauthenticated"                         | red   |
      | 70000       | "Decline"                                 | red   |

  @passingTests @cardinalCommerce @mockData
  Scenario Outline: Cardincal Commerce (card not-enrolled U) - checking payment status for <paymentCode> response code
    And User fills payment form with credit card number "4111110000000401", expiration date "01/22" and cvc "123"
    And THREEDQUERY response set to "not-entrolled U"
    And User clicks Pay button - AUTH response set to "<paymentCode>"
    Then User will see information about payment status <paymentStatusMessage>
    And User will see that notification frame has "<color>" color
    Examples:
      | paymentCode | paymentStatusMessage                      | color |
      | 0           | "Payment has been successfully processed" | green |
      | 60022       | "Unauthenticated"                         | red   |
      | 70000       | "Decline"                                 | red   |

  @passingTests @cardinalCommerce @mockData
  Scenario Outline: Cardincal Commerce - check THREEDQUERY response for code: <paymentCode>
    When User fills payment form with credit card number "4111110000000211", expiration date "01/22" and cvc "123"
    And THREEDQUERY response set to "<paymentCode>"
    And User clicks Pay button
    Then User will see information about payment status <paymentStatusMessage>
    And User will see that notification frame has "<color>" color
    Examples:
      | paymentCode | paymentStatusMessage              | color |
      | 30000       | "Invalid field"                   | red   |
      | 60031       | "Invalid acquirer for 3-D Secure" | red   |

  @passingTests @cardinalCommerce @mockData
  Scenario Outline: Cardincal Commerce (card enrolled Y) - check ACS response for code: <actionCode>
    When User fills payment form with credit card number "4111110000000211", expiration date "01/22" and cvc "123"
    And THREEDQUERY response set to "entrolled Y"
    And ACS response set to "<actionCode>"
    And User clicks Pay button
    Then User will see information about payment status <paymentStatusMessage>
    And User will see that notification frame has "<color>" color
    Examples:
      | actionCode | paymentStatusMessage                      | color |
      | NOACTION   | "Payment has been successfully processed" | green |
      | FAILURE    | "Merchant decline"                        | red   |
      | ERROR      | "An error occurred"                       | red   |

  @passingTests @mockData
  Scenario Outline: Successful payment using most popular Credit Cards: <cardType>
    When User fills payment form with credit card number "<cardNumber>", expiration date "<expirationDate>" and cvc "<cvc>"
    And THREEDQUERY response set to "not-entrolled N"
    And User clicks Pay button - AUTH response set to "<paymentCode>"
    Then User will see information about payment status "Payment has been successfully processed"
    Examples:
      | paymentCode | cardNumber       | expirationDate | cvc  | cardType   |
      | 0           | 340000000000611  | 12/22          | 1234 | AMEX       |
      | 0           | 5100000000000511 | 12/22          | 123  | MASTERCARD |
      | 0           | 4111110000000211 | 12/22          | 123  | VISA       |

  @passingTests @animatedCard
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

  @passingTests @fieldsValidation
  Scenario: Submit payment form without data - fields validation
    When User clicks Pay button
    Then User will see validation message "Field is required" under all fields
    And User will see that all fields are highlighted

  @passingTests @fieldsValidation
  Scenario Outline: Filling payment form with empty fields -> cardNumber "<cardNumber>" expiration: "<expiration>", cvv: "<cvv>"
    When User fills payment form with incorrect or missing data: card number <cardNumber>, expiration date <expiration> and cvc <cvc>
    And User clicks Pay button
    And User will see "Field is required" message under field: "<field>"
    And User will see that <field> field is highlighted
    Examples:
      | cardNumber       | expiration | cvc | field      |
      |                  | 12/22      | 123 | number     |
      | 4000000000001000 |            | 123 | expiryDate |
      | 4000000000001000 | 12/22      |     | cvc        |

  @passingTests @fieldsValidation
  Scenario Outline: Filling payment form with incomplete data (frontend validation) -> cardNumber "<cardNumber>", expiration: "<expiration>", cvv: "<cvv>"
    When User fills payment form with incorrect or missing data: card number <cardNumber>, expiration date <expiration> and cvc <cvc>
    And User clicks Pay button
    And User will see "Value mismatch pattern" message under field: "<field>"
    And User will see that <field> field is highlighted
    Examples:
      | cardNumber       | expiration | cvc | field      |
      | 40000000         | 12/22      | 123 | number     |
      | 4000000000001000 | 12         | 123 | expiryDate |
      | 4000000000001000 | 12/22      | 12  | cvc        |
      | 4000000000009999 | 12/22      | 123 | number     |
      | 4000000000001000 | 44/22      | 123 | expiryDate |

  @passingTests @fieldsValidation
  Scenario Outline: Filling payment form with incomplete data (backend validation) -> cardNumber "<cardNumber>", expiration: "<expiration>", cvv: "<cvv>"
    When User fills payment form with incorrect or missing data: card number <cardNumber>, expiration date <expiration> and cvc <cvc>
    And InvalidField response set for <field>
    And User clicks Pay button
    Then User will see notification frame with message: "Invalid field"
    And User will see that notification frame has "red" color
    And User will see "Invalid field" message under field: "<field>"
    And User will see that <field> field is highlighted
    Examples:
      | cardNumber       | expiration | cvc | field      |
      | 4000000000001000 | 12/22      | 123 | number     |
      | 4000000000001000 | 12/15      | 123 | expiryDate |
      | 4000000000001000 | 12/22      | 000 | cvc        |

  @passingTests @fieldsValidation
  Scenario: Filling 3-number of cvc code for AMEX card
    When User fills payment form with credit card number "340000000000611", expiration date "12/22" and cvc "123"
    And User clicks Pay button
    And User will see "Value mismatch pattern" message under field: "cvc"

  @passingTests @walletTest @visaTest @mockData
  Scenario Outline: Visa Checkout - checking payment status for <paymentCode> response code
    When User chooses Visa Checkout as payment method - response set to <paymentCode>
    Then User will see information about payment status <paymentStatusMessage>
    And User will see that notification frame has "<color>" color
    Examples:
      | paymentCode | paymentStatusMessage                      | color  |
      | Success     | "Payment has been successfully processed" | green  |
      | Error       | "An error occurred"                       | red    |
      | Cancel      | "Payment has been cancelled"              | yellow |

  @passingTests @walletTest @appleTest @mockData
  Scenario Outline: ApplePay - checking payment status for <paymentCode> response code
    When User chooses ApplePay as payment method - response set to <paymentCode>
    Then User will see information about payment status <paymentStatusMessage>
    And User will see that notification frame has "<color>" color
    Examples:
      | paymentCode | paymentStatusMessage                      | color  |
      | Success     | "Payment has been successfully processed" | green  |
      #    | Error       | "An error occurred"                       | red    |
      | Decline     | "Decline"                                 | red    |
      | Cancel      | "Payment has been cancelled"              | yellow |

  #ToDo - unblocking button after payment functionality required
  Scenario: Checking submit button state during and after payment
    When User fills payment form with credit card number "4000000000001000", expiration date "12/22" and cvc "123"
    And THREEDQUERY response set to "not-entrolled N"
    And User clicks Pay button - AUTH response set to "0"
    Then User will see that Submit button is disabled during payment process
#    And User will see that Submit button is enabled after payment