Feature: Payment methods
  As a user
  I want to use various payment methods using correct and incorrect credentials
  In order to check full payment functionality

  Background:
    Given User opens page with payment form

  @fullTest @cardinalCommerce @mockData
  Scenario Outline: Cardincal Commerce (card enrolled Y) - checking payment status for <paymentCode> response code
    When User fills payment form with credit card number "4111110000000211", expiration date "12/30" and cvc "123"
    And THREEDQUERY response set to "entrolled Y"
    And ACS response set to "OK"
    And User clicks Pay button - AUTH response set to "<paymentCode>"
    Then User will see information about payment status <paymentStatusMessage>
    And User will see that notification frame has "<color>" color
    Examples:
      | paymentCode | paymentStatusMessage   | color |
      | 30000       | "Invalid field"        | red   |
      | 50000       | "Socket receive error" | red   |
      | 60022       | "Unauthenticated"      | red   |
      | 99999       | "Unknown error"        | red   |

  @fullTest @cardinalCommerce @mockData
  Scenario Outline: Cardincal Commerce (card not-enrolled N) - checking payment status for <paymentCode> response code
    When User fills payment form with credit card number "4000000000001059", expiration date "01/22" and cvc "123"
    And THREEDQUERY response set to "not-entrolled N"
    And User clicks Pay button - AUTH response set to "<paymentCode>"
    Then User will see information about payment status <paymentStatusMessage>
    And User will see that notification frame has "<color>" color
    Examples:
      | paymentCode | paymentStatusMessage                      | color |
      | 0           | "Payment has been successfully processed" | green |
      | 70000       | "Decline"                                 | red   |

  @fullTest @cardinalCommerce @mockData
  Scenario Outline: Cardincal Commerce (card not-enrolled U) - checking payment status for <paymentCode> response code
    And User fills payment form with credit card number "4111110000000401", expiration date "01/22" and cvc "123"
    And THREEDQUERY response set to "not-entrolled U"
    And User clicks Pay button - AUTH response set to "<paymentCode>"
    Then User will see information about payment status <paymentStatusMessage>
    And User will see that notification frame has "<color>" color
    Examples:
      | paymentCode | paymentStatusMessage | color |
      | 60022       | "Unauthenticated"    | red   |
      | 70000       | "Decline"            | red   |

  @fullTest @cardinalCommerce @mockData
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
      | ERROR      | "An error occurred"                       | red   |

  @fullTest @mockData
  Scenario Outline: Successful payment using most popular Credit Cards: <cardType>
    When User fills payment form with credit card number "<cardNumber>", expiration date "<expirationDate>" and cvc "<cvc>"
    And THREEDQUERY response set to "not-entrolled N"
    And User clicks Pay button - AUTH response set to "<paymentCode>"
    Then User will see information about payment status "Payment has been successfully processed"
    Examples:
      | paymentCode | cardNumber       | expirationDate | cvc | cardType   |
      | 0           | 5100000000000511 | 12/22          | 123 | MASTERCARD |

  @fullTest @animatedCard
  Scenario Outline: Credit card recognition for <cardType> and validate date on animated card
    When User fills payment form with credit card number "<cardNumber>", expiration date "<expirationDate>" and cvc "<cvc>"
    Then User will see card icon connected to card type <cardType>
    And User will see the same provided data on animated credit card <formattedCardNumber>, <expirationDate> and <cvc>
    And User will see that animated card is flipped, except for "AMEX"
    Examples:
      | cardNumber          | formattedCardNumber    | expirationDate | cvc | cardType     |
      | 1801000000000901    | 1801 0000 0000 0901    | 12/22          | 123 | ASTROPAYCARD |
      | 3000000000000111    | 3000 000000 000111     | 12/22          | 123 | DINERS       |
      | 6011000000000301    | 6011 0000 0000 0301    | 12/22          | 123 | DISCOVER     |
      | 3528000000000411    | 3528 0000 0000 0411    | 12/22          | 123 | JCB          |
      | 5000000000000611    | 5000 0000 0000 0611    | 12/22          | 123 | MAESTRO      |
      | 5100000000000511    | 5100 0000 0000 0511    | 12/22          | 123 | MASTERCARD   |
      | 3089500000000000021 | 3089 5000 0000 0000021 | 12/22          | 123 | PIBA         |

  @fullTest @fieldsValidation
  Scenario Outline: Filling payment form with empty fields -> cardNumber "<cardNumber>" expiration: "<expiration>", cvv: "<cvv>"
    When User fills payment form with incorrect or missing data: card number <cardNumber>, expiration date <expiration> and cvc <cvc>
    And User clicks Pay button
    And User will see "Field is required" message under field: "<field>"
    And User will see that <field> field is highlighted
    Examples:
      | cardNumber       | expiration | cvc | field      |
      | 4000000000001000 |            | 123 | expiryDate |
      | 4000000000001000 | 12/22      |     | cvc        |

  @fullTest @fieldsValidation
  Scenario Outline: Filling payment form with incomplete data (frontend validation) -> cardNumber "<cardNumber>", expiration: "<expiration>", cvv: "<cvv>"
    When User fills payment form with incorrect or missing data: card number <cardNumber>, expiration date <expiration> and cvc <cvc>
    And User clicks Pay button
    And User will see "Value mismatch pattern" message under field: "<field>"
    And User will see that <field> field is highlighted
    Examples:
      | cardNumber       | expiration | cvc | field      |
      | 40000000         | 12/22      | 123 | number     |
      | 4000000000001000 | 12         | 123 | expiryDate |
      | 4000000000009999 | 12/22      | 123 | number     |
      | 4000000000001000 | 44/22      | 123 | expiryDate |

  @fullTest @fieldsValidation
  Scenario Outline: Filling payment form with incomplete data (backend validation) -> cardNumber "<cardNumber>", expiration: "<expiration>", cvv: "<cvv>"
    When User fills payment form with incorrect or missing data: card number <cardNumber>, expiration date <expiration> and cvc <cvc>
    And InvalidField response set for "<field>"
    And User clicks Pay button
    Then User will see notification frame with message: "Invalid field"
    And User will see that notification frame has "red" color
    And User will see "Invalid field" message under field: "<field>"
    And User will see that <field> field is highlighted
    Examples:
      | cardNumber       | expiration | cvc | field      |
      | 4000000000001000 | 12/15      | 123 | expiryDate |
      | 4000000000001000 | 12/22      | 000 | cvc        |

  @fullTest @fieldsValidation
  Scenario: Filling 3-number of cvc code for AMEX card
    When User fills payment form with credit card number "340000000000611", expiration date "12/22" and cvc "123"
    And User clicks Pay button
    And User will see "Value mismatch pattern" message under field: "cvc"

  @fullTest @fieldsValidation
  Scenario: Checking merchant field validation - invalid email
    When User fills merchant data with name "John Test", email "test@example", phone "44422224444"
    And User fills payment form with credit card number "4000000000001000", expiration date "12/22" and cvc "123"
    And InvalidField response set for "email"
    And User clicks Pay button
    Then User will see that merchant field "email" is highlighted
    And User will see notification frame with message: "Invalid field"
    And User will see that notification frame has "red" color

  @fullTest @walletTest @visaTest @mockData
  Scenario Outline: Visa Checkout - checking payment status for <paymentCode> response code
    When User chooses Visa Checkout as payment method - response set to "<paymentCode>"
    Then User will see information about payment status <paymentStatusMessage>
    And User will see that notification frame has "<color>" color
    Examples:
      | paymentCode | paymentStatusMessage         | color  |
      | Error       | "An error occurred"          | red    |
      | Cancel      | "Payment has been cancelled" | yellow |

  @fullTest @walletTest @appleTest @mockData
  Scenario Outline: ApplePay - checking payment status for <paymentCode> response code
    When User chooses ApplePay as payment method - response set to "<paymentCode>"
    Then User will see information about payment status <paymentStatusMessage>
    And User will see that notification frame has "<color>" color
    Examples:
      | paymentCode | paymentStatusMessage         | color  |
      #    | Error       | "An error occurred"                       | red    |
      | Decline     | "Decline"                    | red    |
      | Cancel      | "Payment has been cancelled" | yellow |

  @fullTest @unlockPaymentForm
  Scenario: hecking payment form state after payment with Error
    When User fills payment form with credit card number "4000000000001000", expiration date "12/22" and cvc "123"
    And THREEDQUERY response set to "not-entrolled N"
    And User clicks Pay button - AUTH response set to "99999"
    Then User will see that Submit button is enabled after payment
    And User will see that all input fields are enabled

  #ToDo - Complete labels translation: Pay button, name, email. phone
  @fullTest @translations
  Scenario Outline: Checking translations for labels and fields error for <language>
    When User changes page language to <language>
    And User clicks Pay button
    Then User will see all labels displayed on page translated into <language>
    And User will see validation message "Field is required" under all fields translated into <language>
    Examples:
      | language |
      | en_GB    |
      | fr_FR    |
  #      | en_US    |
  #      | cy_GB    |
  #      | da_DK    |
  #      | es_ES    |
  #      | nl_NL    |
  #      | no_NO    |
  #      | sv_SE    |

  #ToDo - Complete translation - "Value mismatch pattern"
  @translations
  Scenario Outline: Checking translation for fields validation translated into <language>
    When User fills payment form with credit card number "4000000000000051 ", expiration date "12" and cvc "123"
    And User clicks Pay button
    Then User will see validation message "Value mismatch pattern" under "number" field translated into <language>
    Examples:
      | language |
      | fr_FR    |
      | de_DE    |

  #ToDo - Complete translation - "Invalid field"
  @translations
  Scenario Outline: Filling payment form with incomplete data (backend validation) -> cardNumber "<cardNumber>", expiration: "<expiration>", cvv: "<cvv>"
    When User fills payment form with incorrect or missing data: card number <cardNumber>, expiration date <expiration> and cvc <cvc>
    #    And InvalidField response set for <field>
    And User clicks Pay button
    Then User will see information about "Invalid field" payment status translated into <language>
    Then User will see validation message "Invalid field" under "number" field translated into <language>
    Examples:
      | language |
      | fr_FR    |
      | de_DE    |

  @fullTest @passingTests @translations
  Scenario Outline: Cardincal Commerce - checking translation for "Success" status for <language>
    When User changes page language to <language>
    And User fills payment form with credit card number "4000000000001059", expiration date "01/22" and cvc "123"
    And THREEDQUERY response set to "not-entrolled N"
    And User clicks Pay button - AUTH response set to "0"
    Then User will see information about "Success" payment status translated into <language>
    Examples:
      | language |
      | de_DE    |

  #ToDo - Complete translation - "Unknown error"
  @translations
  Scenario Outline: Cardincal Commerce - checking translation for "Unknown error" status for <language>
    When User changes page language to <language>
    And User fills payment form with credit card number "4000000000001059", expiration date "01/22" and cvc "123"
    And THREEDQUERY response set to "not-entrolled N"
    And User clicks Pay button - AUTH response set to "99999"
    Then User will see information about "Unknown error" payment status translated into <language>
    Examples:
      | language |
      | fr_FR    |
      | de_DE    |

  @fullTest @translations
  Scenario Outline: Visa Checkout - checking translation for "Error" status for <language>
    When User changes page language to <language>
    And User chooses Visa Checkout as payment method - response set to "Error"
    Then User will see information about "Error" payment status translated into <language>
    Examples:
      | language |
      | de_DE    |

  #ToDo - Complete translation - "Decline"
  @translations
  Scenario Outline: ApplePay - checking translation for "Decline" status for <language>
    When User changes page language to <language>
    When User chooses ApplePay as payment method - response set to "Decline"
    Then User will see information about "Decline" payment status translated into <language>
    Examples:
      | language |
      | fr_FR    |
      | de_DE    |

  @fullTest @immediatePayment @mockData
  Scenario Outline: Immediate payment (card enrolled Y) - checking payment status for <paymentCode> response code
    When THREEDQUERY response set to "entrolled Y"
    And ACS response set to "OK"
    And AUTH response set to "<paymentCode>"
    And User opens immediate payment page
    Then User will see message "<errorMessage>" displayed on page
    And User will see error code: "<paymentCode>"
    Examples:
      | paymentCode | errorMessage |
      | 70000       | Decline      |

  @fullTest @immediatePayment @mockData
  Scenario Outline: Immediate payment (card enrolled N) - checking payment status for <paymentCode> response code
    When THREEDQUERY response set to "enot-entrolled N"
    And AUTH response set to "<paymentCode>"
    And User opens immediate payment page
    Then User will see message "<errorMessage>" displayed on page
    And User will see error code: "<paymentCode>"
    Examples:
      | paymentCode | errorMessage                            |
      | 0           | Payment has been successfully processed |

  #ToDo - Verify ERROR action cody why is "Invalid response" instead "An error occured"
  @fullTest @immediatePayment @mockData
  Scenario Outline: Immediate payment (card enrolled Y) - check ACS response for code: <actionCode>
    When THREEDQUERY response set to "entrolled Y"
    And ACS response set to "<actionCode>"
    And User opens immediate payment page
    Then User will see message "<errorMessage>" displayed on page
    Examples:
      | actionCode | errorMessage      |
#      | ERROR      | An error occurred |
      | FAILURE    | Merchant decline  |