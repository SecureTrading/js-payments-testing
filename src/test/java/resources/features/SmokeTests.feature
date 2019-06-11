Feature: Smoke tests

  Background:
    Given User opens page with payment form

  @smokeTest @fullTest
  Scenario Outline: Successful payment using most popular Credit Cards: <cardType>
    When User fills payment form with credit card number "<cardNumber>", expiration date "<expirationDate>" and cvc "<cvc>"
    And THREEDQUERY response set to "not-entrolled N"
    And User clicks Pay button - AUTH response set to "<paymentCode>"
    Then User will see information about payment status "Payment has been successfully processed"
    Examples:
      | paymentCode | cardNumber       | expirationDate | cvc  | cardType   |
      | 0           | 340000000000611  | 12/22          | 1234 | AMEX       |
      | 0           | 4111110000000211 | 12/22          | 123  | VISA       |

  @smokeTest @fullTest
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
      | 70000       | "Decline"                                 | red   |

  @smokeTest @fullTest
  Scenario Outline: Cardincal Commerce (card not-enrolled N) - checking payment status for <paymentCode> response code
    When User fills payment form with credit card number "4000000000001059", expiration date "01/22" and cvc "123"
    And THREEDQUERY response set to "not-entrolled N"
    And User clicks Pay button - AUTH response set to "<paymentCode>"
    Then User will see information about payment status <paymentStatusMessage>
    And User will see that notification frame has "<color>" color
    Examples:
      | paymentCode | paymentStatusMessage                      | color |
      | 60022       | "Unauthenticated"                         | red   |

  @smokeTest @fullTest
  Scenario Outline: Cardincal Commerce (card not-enrolled U) - checking payment status for <paymentCode> response code
    And User fills payment form with credit card number "4111110000000401", expiration date "01/22" and cvc "123"
    And THREEDQUERY response set to "not-entrolled U"
    And User clicks Pay button - AUTH response set to "<paymentCode>"
    Then User will see information about payment status <paymentStatusMessage>
    And User will see that notification frame has "<color>" color
    Examples:
      | paymentCode | paymentStatusMessage                      | color |
      | 0           | "Payment has been successfully processed" | green |

  @smokeTest @fullTest
  Scenario Outline: Cardincal Commerce - check THREEDQUERY response for code: <paymentCode>
    When User fills payment form with credit card number "4111110000000211", expiration date "01/22" and cvc "123"
    And THREEDQUERY response set to "<paymentCode>"
    And User clicks Pay button
    Then User will see information about payment status <paymentStatusMessage>
    And User will see that notification frame has "<color>" color
    Examples:
      | paymentCode | paymentStatusMessage              | color |
      | 60031       | "Invalid acquirer for 3-D Secure" | red   |

  @smokeTest @fullTest
  Scenario Outline: Cardincal Commerce (card enrolled Y) - check ACS response for code: <actionCode>
    When User fills payment form with credit card number "4111110000000211", expiration date "01/22" and cvc "123"
    And THREEDQUERY response set to "entrolled Y"
    And ACS response set to "<actionCode>"
    And User clicks Pay button
    Then User will see information about payment status <paymentStatusMessage>
    And User will see that notification frame has "<color>" color
    Examples:
      | actionCode | paymentStatusMessage                      | color |
      | FAILURE    | "Merchant decline"                        | red   |

  @smokeTest @fullTest
  Scenario Outline: Credit card recognition for <cardType> and validate date on animated card
    When User fills payment form with credit card number "<cardNumber>", expiration date "<expirationDate>" and cvc "<cvc>"
    Then User will see card icon connected to card type <cardType>
    And User will see the same provided data on animated credit card <formattedCardNumber>, <expirationDate> and <cvc>
    And User will see that animated card is flipped, except for "AMEX"
    Examples:
      | cardNumber          | formattedCardNumber    | expirationDate | cvc  | cardType     |
      | 340000000000611     | 3400 000000 00611      | 12/22          | 1234 | AMEX         |
      | 4111110000000211    | 4111 1100 0000 0211    | 12/22          | 123  | VISA         |

  @smokeTest @fullTest
  Scenario: Submit payment form without data - fields validation
    When User clicks Pay button
    Then User will see validation message "Field is required" under all fields
    And User will see that all fields are highlighted

  @smokeTest @fullTest
  Scenario Outline: Filling payment form with empty fields -> cardNumber "<cardNumber>" expiration: "<expiration>", cvv: "<cvv>"
    When User fills payment form with incorrect or missing data: card number <cardNumber>, expiration date <expiration> and cvc <cvc>
    And User clicks Pay button
    And User will see "Field is required" message under field: "<field>"
    And User will see that <field> field is highlighted
    Examples:
      | cardNumber       | expiration | cvc | field      |
      |                  | 12/22      | 123 | number     |

  @smokeTest @fullTest
  Scenario Outline: Filling payment form with incomplete data (frontend validation) -> cardNumber "<cardNumber>", expiration: "<expiration>", cvv: "<cvv>"
    When User fills payment form with incorrect or missing data: card number <cardNumber>, expiration date <expiration> and cvc <cvc>
    And User clicks Pay button
    And User will see "Value mismatch pattern" message under field: "<field>"
    And User will see that <field> field is highlighted
    Examples:
      | cardNumber       | expiration | cvc | field      |
      | 4000000000001000 | 12/22      | 12  | cvc        |

  @smokeTest @fullTest
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

  @smokeTest @fullTest
  Scenario Outline: Visa Checkout - checking payment status for <paymentCode> response code
    When User chooses Visa Checkout as payment method - response set to "<paymentCode>"
    Then User will see information about payment status <paymentStatusMessage>
    And User will see that notification frame has "<color>" color
    Examples:
      | paymentCode | paymentStatusMessage                      | color  |
      | Success     | "Payment has been successfully processed" | green  |

  @smokeTest @fullTest
  Scenario Outline: ApplePay - checking payment status for <paymentCode> response code
    When User chooses ApplePay as payment method - response set to "<paymentCode>"
    Then User will see information about payment status <paymentStatusMessage>
    And User will see that notification frame has "<color>" color
    Examples:
      | paymentCode | paymentStatusMessage                      | color  |
      | Success     | "Payment has been successfully processed" | green  |

    #ToDo - Complete labels translation: Pay button, name, email. phone
  @smokeTest @fullTest
  Scenario Outline: Checking translations for labels and fields error for <language>
    When User changes page language to <language>
    And User clicks Pay button
    Then User will see all labels displayed on page translated into <language>
    And User will see validation message "Field is required" under all fields translated into <language>
    Examples:
      | language |
      | de_DE    |

  @smokeTest @fullTest
  Scenario Outline: Cardincal Commerce - checking translation for "Success" status for <language>
    When User changes page language to <language>
    And User fills payment form with credit card number "4000000000001059", expiration date "01/22" and cvc "123"
    And THREEDQUERY response set to "not-entrolled N"
    And User clicks Pay button - AUTH response set to "0"
    Then User will see information about "Success" payment status translated into <language>
    Examples:
      | language |
      | fr_FR    |

  @smokeTest @fullTest
  Scenario Outline: Visa Checkout - checking translation for "Error" status for <language>
    When User changes page language to <language>
    And User chooses Visa Checkout as payment method - response set to "Error"
    Then User will see information about "Error" payment status translated into <language>
    Examples:
      | language |
      | fr_FR    |

  @smokeTest @immediatePayment @mockData
  Scenario Outline: Immediate payment (card enrolled Y) - checking payment status for <paymentCode> response code
    When THREEDQUERY response set to "entrolled Y"
    And ACS response set to "OK"
    And AUTH response set to "<paymentCode>"
    And User opens immediate payment page
    Then User will see message "<errorMessage>" displayed on page
    And User will see error code: "<paymentCode>"
    Examples:
      | paymentCode | errorMessage |
      | 0           | Ok           |
