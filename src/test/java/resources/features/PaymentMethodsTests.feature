Feature: Payment methods
  As a user
  I want to use various payment methods using correct and incorrect credentials
  In order to check full payment functionality

  Background:
    Given JavaScript configuration is set for scenario based on scenario's @config tag
    And User opens page with payment form

  @baseConfig @fullTest @cardinalCommerce
  Scenario Outline: Cardinal Commerce (card enrolled Y) - checking payment status for <actionCode> response code
    When User fills payment form with credit card number "4111110000000211", expiration date "12/30" and cvc "123"
    And THREEDQUERY response set to ENROLLED_Y
    And ACS response set to OK
    And User clicks Pay button - AUTH response set to <actionCode>
    Then User will see payment status information: <paymentStatusMessage>
    And User will see that notification frame has "<color>" color
  @smokeTest
    Examples:
      | actionCode | paymentStatusMessage                    | color |
      | OK         | Payment has been successfully processed | green |
      | DECLINE    | Decline                                 | red   |
    Examples:
      | actionCode      | paymentStatusMessage | color |
#      | INVALID_FIELD   | Invalid field        | red   |
      | SOCKET_ERROR    | Socket receive error | red   |
      | UNAUTHENTICATED | Unauthenticated      | red   |
#      | UNKNOWN_ERROR   | Unknown error        | red   |

  @baseConfig @fullTest @cardinalCommerce
  Scenario Outline: Cardinal Commerce (card not-enrolled N) - checking payment status for <actionCode> response code
    When User fills payment form with credit card number "4000000000001059", expiration date "01/22" and cvc "123"
    And THREEDQUERY response set to NOT_ENROLLED_N
    And User clicks Pay button - AUTH response set to <actionCode>
    Then User will see payment status information: <paymentStatusMessage>
    And User will see that notification frame has "<color>" color
  @smokeTest
    Examples:
      | actionCode      | paymentStatusMessage | color |
      | UNAUTHENTICATED | Unauthenticated      | red   |
    Examples:
      | actionCode | paymentStatusMessage                    | color |
      | OK         | Payment has been successfully processed | green |
      | DECLINE    | Decline                                 | red   |

  @baseConfig @fullTest @cardinalCommerce
  Scenario Outline: Cardinal Commerce (card not-enrolled U) - checking payment status for <actionCode> response code
    And User fills payment form with credit card number "4111110000000401", expiration date "01/22" and cvc "123"
    And THREEDQUERY response set to NOT_ENROLLED_U
    And User clicks Pay button - AUTH response set to <actionCode>
    Then User will see payment status information: <paymentStatusMessage>
    And User will see that notification frame has "<color>" color
  @smokeTest
    Examples:
      | actionCode | paymentStatusMessage                    | color |
      | OK         | Payment has been successfully processed | green |
    Examples:
      | actionCode      | paymentStatusMessage | color |
      | UNAUTHENTICATED | Unauthenticated      | red   |
#      | DECLINE         | Decline            | red   |

  @baseConfig @smokeTest @fullTest
  Scenario: Cardinal Commerce - check THREEDQUERY response for code: "INVALID_ACQUIRER"
    When User fills payment form with credit card number "4111110000000211", expiration date "01/22" and cvc "123"
    And THREEDQUERY response set to INVALID_ACQUIRER
    And User clicks Pay button
    Then User will see payment status information: Invalid acquirer for 3-D Secure
    And User will see that notification frame has "red" color

  @baseConfig @fullTest @cardinalCommerce @mockData
  Scenario Outline: Cardinal Commerce (card enrolled Y) - check ACS response for code: <actionCode>
    When User fills payment form with credit card number "4111110000000211", expiration date "01/22" and cvc "123"
    And THREEDQUERY response set to ENROLLED_Y
    And ACS response set to <actionCode>
    And User clicks Pay button
    Then User will see payment status information: <paymentStatusMessage>
    And User will see that notification frame has "<color>" color
  @smokeTest
    Examples:
      | actionCode | paymentStatusMessage | color |
      | FAILURE    | Merchant decline     | red   |
    Examples:
      | actionCode | paymentStatusMessage                    | color |
      | NOACTION   | Payment has been successfully processed | green |
#      | ERROR      | Invalid response                        | red   |

  @baseConfig @fullTest @cardinalCommerce
  Scenario Outline: Successful payment using most popular Credit Cards: <cardType>
    When User fills payment form with credit card number "<cardNumber>", expiration date "<expirationDate>" and cvc "<cvc>"
    And THREEDQUERY response set to NOT_ENROLLED_N
    And User clicks Pay button - AUTH response set to OK
    Then User will see payment status information: Payment has been successfully processed
  @smokeTest
    Examples:
      | cardNumber       | expirationDate | cvc  | cardType |
      | 340000000000611  | 12/22          | 1234 | AMEX     |
      | 4111110000000211 | 12/22          | 123  | VISA     |
    Examples:
      | cardNumber       | expirationDate | cvc | cardType   |
      | 5100000000000511 | 12/22          | 123 | MASTERCARD |

  @configAnimatedCardTrue @animatedCard @fullTest
  Scenario Outline: Credit card recognition for <cardType> and validate date on animated card
    When User fills payment form with credit card number "<cardNumber>", expiration date "<expirationDate>" and cvc "<cvc>"
    Then User will see card icon connected to card type <cardType>
    And User will see the same provided data on animated credit card "<formattedCardNumber>", "<expirationDate>" and "<cvc>"
    And User will see that animated card is flipped, except for "AMEX"
    @smokeTest
    Examples:
      | cardNumber       | formattedCardNumber | expirationDate | cvc  | cardType |
      | 4111110000000211 | 4111 1100 0000 0211 | 12/22          | 123  | VISA     |
    Examples:
      | cardNumber       | formattedCardNumber | expirationDate | cvc  | cardType |
      | 340000000000611  | 3400 000000 00611   | 12/23          | 1234 | AMEX     |
 #     | 6011000000000301 | 6011 0000 0000 0301 | 12/23          | 123  | DISCOVER   |
#      | 3528000000000411 | 3528 0000 0000 0411 | 12/23          | 123  | JCB        |
#      | 5000000000000611 | 5000 0000 0000 0611 | 12/23          | 123  | MAESTRO    |
#      | 5100000000000511 | 5100 0000 0000 0511 | 12/23          | 123  | MASTERCARD |
#      | 3089500000000000021 | 3089 5000 0000 0000021 | 12/23          | 123 | PIBA         |
#      | 1801000000000901    | 1801 0000 0000 0901    | 12/23          | 123 | ASTROPAYCARD |
#      | 3000000000000111    | 3000 000000 000111     | 12/23          | 123 | DINERS       |

  @baseConfig @fullTest
  Scenario: Disabled CVC field for PIBA card type
    When User fills payment form with credit card number "3089500000000000021", expiration date "12/23"
    Then User will see that CVC field is disabled

  @baseConfig @smokeTest @fullTest
  Scenario: Submit payment form without data - fields validation
    When User clicks Pay button
    Then User will see validation message "Field is required" under all fields
    And User will see that all fields are highlighted

  @baseConfig @fullTest @fieldsValidation
  Scenario Outline: Filling payment form with empty fields -> cardNumber "<cardNumber>" expiration: "<expiration>", cvv: "<cvv>"
    When User fills payment form with incorrect or missing data: card number <cardNumber>, expiration date <expiration> and cvc <cvc>
    And User clicks Pay button
    And User will see "Field is required" message under field: <field>
    And User will see that <field> field is highlighted
  @smokeTest
    Examples:
      | cardNumber | expiration | cvc | field       |
      |            | 12/22      | 123 | CARD_NUMBER |
    Examples:
      | cardNumber       | expiration | cvc | field       |
      | 4000000000001000 |            | 123 | EXPIRY_DATE |
      | 4000000000001000 | 12/22      |     | CVC         |

  @baseConfig @fullTest @fieldsValidation
  Scenario Outline: Filling payment form with incomplete data (frontend validation) -> cardNumber "<cardNumber>", expiration: "<expiration>", cvv: "<cvv>"
    When User fills payment form with incorrect or missing data: card number <cardNumber>, expiration date <expiration> and cvc <cvc>
    And User clicks Pay button
    And User will see "Value mismatch pattern" message under field: <field>
    And User will see that <field> field is highlighted
  @smokeTest
    Examples:
      | cardNumber       | expiration | cvc | field |
      | 4000000000001000 | 12/22      | 12  | CVC   |
    Examples:
      | cardNumber       | expiration | cvc | field       |
      | 40000000         | 12/22      | 123 | CARD_NUMBER |
      | 4000000000001000 | 12         | 123 | EXPIRY_DATE |
      | 4000000000009999 | 12/22      | 123 | CARD_NUMBER |
      | 4000000000001000 | 44/22      | 123 | EXPIRY_DATE |

  @baseConfig @fullTest @fieldsValidation
  Scenario Outline: Filling payment form with incomplete data (backend validation) -> cardNumber "<cardNumber>", expiration: "<expiration>", cvv: "<cvv>"
    When User fills payment form with incorrect or missing data: card number <cardNumber>, expiration date <expiration> and cvc <cvc>
    And InvalidField response set for <field>
    And User clicks Pay button
    Then User will see notification frame with message: "Invalid field"
    And User will see that notification frame has "red" color
    And User will see "Invalid field" message under field: <field>
    And User will see that <field> field is highlighted
  @smokeTest
    Examples:
      | cardNumber       | expiration | cvc | field       |
      | 4000000000001000 | 12/22      | 123 | CARD_NUMBER |
    Examples:
      | cardNumber       | expiration | cvc | field       |
      | 4000000000001000 | 12/15      | 123 | EXPIRY_DATE |
      | 4000000000001000 | 12/22      | 000 | CVC         |

  @baseConfig @fullTest @fieldsValidation
  Scenario: Filling 3-number of cvc code for AMEX card
    When User fills payment form with credit card number "340000000000611", expiration date "12/22" and cvc "123"
    And User clicks Pay button
    And User will see "Value mismatch pattern" message under field: CVC

#  @fullTest @fieldsValidation
#  Scenario: Checking merchant field validation - invalid email
#    When User fills merchant data with name "John Test", email "test@example", phone "44422224444"
#    And User fills payment form with credit card number "4000000000001000", expiration date "12/22" and cvc "123"
#    And InvalidField response set for EMAIL
#    And User clicks Pay button
#    Then User will see that merchant field EMAIL is highlighted
#    And User will see notification frame with message: "Invalid field"
#    And User will see that notification frame has "red" color

  @baseConfig @fullTest @walletTest @visaTest
  Scenario Outline: Visa Checkout - checking payment status for <actionCode> response code
    When User chooses Visa Checkout as payment method - response set to <actionCode>
    Then User will see payment status information: <paymentStatusMessage>
    And User will see that notification frame has "<color>" color
  @smokeTest
    Examples:
      | actionCode | paymentStatusMessage                    | color |
      | SUCCESS    | Payment has been successfully processed | green |
    Examples:
      | actionCode | paymentStatusMessage       | color  |
      | CANCEL     | Payment has been cancelled | yellow |

  @baseConfig @fullTest @walletTest @appleTest @mockData
  Scenario Outline: ApplePay - checking payment status for <actionCode> response code
    When User chooses ApplePay as payment method - response set to <actionCode>
    Then User will see payment status information: <paymentStatusMessage>
    And User will see that notification frame has "<color>" color
  @smokeTest
    Examples:
      | actionCode | paymentStatusMessage                    | color |
      | SUCCESS    | Payment has been successfully processed | green |
    Examples:
      | actionCode | paymentStatusMessage       | color  |
#      | ERROR       | "Invalid response"          | red    |
      | DECLINE    | Decline                    | red    |
      | CANCEL     | Payment has been cancelled | yellow |

  @baseConfig @fullTest @unlockPaymentForm
  Scenario Outline: Payment form accessibility after payment process
    When User fills payment form with credit card number "4000000000001000", expiration date "12/22" and cvc "123"
    And THREEDQUERY response set to NOT_ENROLLED_N
    And User clicks Pay button - AUTH response set to <actionCode>
    Then User will see that Submit button is <formStatus> after payment
    And User will see that all input fields are <formStatus>
  @smokeTest
    Examples:
      | actionCode | formStatus   |
      | OK         | disabled |
    Examples:
      | actionCode | formStatus |
      | DECLINE    | enabled |

  @baseConfig @fullTest @translations
  Scenario Outline: Checking translations of labels and fields error for <language>
    When User changes page language to <language>
    And User clicks Pay button
    Then User will see all labels displayed on page translated into <language>
    And User will see validation message "Field is required" under all fields translated into <language>
  @smokeTest
    Examples:
      | language |
      | de_DE    |
    Examples:
      | language |
      | en_GB    |
#      | fr_FR    |
#      | en_US    |
#      | cy_GB    |
#      | da_DK    |
#      | es_ES    |
#      | nl_NL    |
#      | no_NO    |
#      | sv_SE    |

  @configAnimatedCardTrue @animatedCard @translations
  Scenario Outline: Checking animated card translation for <language>
    When User changes page language to <language>
    And User fills payment form with credit card number "340000000000611", expiration date "12/22" and cvc "123"
    Then User will see that labels displayed on animated card are translated into <language>
    Examples:
      | language |
      | de_DE    |

  @baseConfig @fullTest @translations
  Scenario Outline: Checking translation of fields validation for <language>
    When User changes page language to <language>
    And User fills payment form with credit card number "4000000000000051 ", expiration date "12/22" and cvc "12"
    And User clicks Pay button
    Then User will see validation message "Value mismatch pattern" under CVC field translated into <language>
    Examples:
      | language |
      | fr_FR    |
#      | de_DE    |

  @baseConfig @fullTest @translations
  Scenario Outline: Checking translation of backend fields validation for <language>
    When User changes page language to <language>
    And User fills payment form with credit card number "4000000000001059", expiration date "01/22" and cvc "123"
    And InvalidField response set for CARD_NUMBER
    And User clicks Pay button
    Then User will see information about "Invalid field" payment status translated into <language>
    Then User will see validation message "Invalid field" under CARD_NUMBER field translated into <language>
    Examples:
      | language |
      | es_ES    |
#      | de_DE    |

  @baseConfig @fullTest @translations
  Scenario Outline: Cardinal Commerce - checking "Success" status translation for <language>
    When User changes page language to <language>
    And User fills payment form with credit card number "4000000000001059", expiration date "01/22" and cvc "123"
    And THREEDQUERY response set to NOT_ENROLLED_N
    And User clicks Pay button - AUTH response set to OK
    Then User will see information about "Success" payment status translated into <language>
  @smokeTest
    Examples:
      | language |
      | no_NO    |
#    Examples:
#      | language |
#      | de_DE    |

  @baseConfig @fullTest @translations
  Scenario Outline: Visa Checkout - check translation overwriting mechanism
    When User changes page language to <language>
    And User chooses Visa Checkout as payment method - response set to ERROR
    Then User will see notification frame with message: "Wystąpił błąd"
    And User will see that notification frame has "red" color
  @smokeTest
    Examples:
      | language |
      | fr_FR    |

  @baseConfig @fullTest @translations
  Scenario Outline: ApplePay - checking translation for "Payment has been cancelled" status for <language>
    When User changes page language to <language>
    When User chooses ApplePay as payment method - response set to CANCEL
    Then User will see information about "Cancel" payment status translated into <language>
    Examples:
      | language |
      | es_ES    |
#      | no_NO    |

  @configImmediatePayment @fullTest @mockData
  Scenario Outline: Immediate payment (card enrolled Y) - checking payment status for <actionCode> response code
    When THREEDQUERY response set to ENROLLED_Y
    And ACS response set to OK
    And AUTH response set to "<actionCode>"
    And User opens payment page
    Then User will see payment status information: <paymentStatusMessage>
    Examples:
      | actionCode | paymentStatusMessage                    | paymentCode |
      | OK         | Payment has been successfully processed | 0           |
      | DECLINE    | Decline                                 | 70000       |

  @configImmediatePayment @smokeTest @mockData
  Scenario: Immediate payment (card enrolled N) - checking payment status for OK response code
    When THREEDQUERY response set to NOT_ENROLLED_N
    And AUTH response set to "OK"
    And User opens payment page
    Then User will see payment status information: Payment has been successfully processed

  @configImmediatePayment @fullTest @mockData
  Scenario Outline: Immediate payment (card enrolled Y) - check ACS response for code: <actionCode>
    When THREEDQUERY response set to ENROLLED_Y
    And ACS response set to <actionCode>
    And User opens payment page
    Then User will see payment status information: <paymentStatusMessage>
    Examples:
      | actionCode | paymentStatusMessage |
#      | ERROR      | Invalid response |
      | FAILURE    | Merchant decline     |

  @configSkipJSinit @smokeTest @fullTest @cardinalCommerce
  Scenario: Successful payment with skipped JSINIT process
    When User fills payment form with credit card number "4111110000000211", expiration date "12/30" and cvc "123"
    And THREEDQUERY response set to NOT_ENROLLED_N
    And User clicks Pay button - AUTH response set to OK
    Then User will see payment status information: Payment has been successfully processed
    And User will see that notification frame has "green" color

  @configSubmitOnSuccessTrue @smokeTest @fullTest
  Scenario: Cardinal Commerce - successful payment with enabled 'submit on success' process
    When User fills payment form with credit card number "4111110000000211", expiration date "12/30" and cvc "123"
    And THREEDQUERY response set to ENROLLED_Y
    And ACS response set to OK
    And User clicks Pay button - AUTH response set to OK
    Then User will see payment status information included in url

  @configSubmitOnSuccessTrue @smokeTest @fullTest
  Scenario: Visa Checkout - successful payment with enabled 'submit on success' process
    When User chooses Visa Checkout as payment method - response set to SUCCESS
    Then User will see payment status information included in url

  @configFieldStyle @smokeTest @fullTest
  Scenario: Checking style of individual fields
    Then User will see that CARD_NUMBER field has correct style
    And User will see that CVC field has correct style

  @configUpdateJwtTrue @smokeTest @fullTest
  Scenario: Successful payment with updated JWT
    When User fills payment form with credit card number "4111110000000211", expiration date "12/30" and cvc "123"
    And THREEDQUERY response set to ENROLLED_Y
    And ACS response set to OK
    And User clicks Pay button - AUTH response set to OK
    Then User will see payment status information: Payment has been successfully processed
    And User will see that notification frame has "green" color

  @configDeferInitAndStartOnLoadTrue @fullTest
  Scenario: Successful payment with updated JWT and StartOnLoad
    When THREEDQUERY response set to NOT_ENROLLED_N
    And AUTH response set to "OK"
    And User opens payment page
    Then User will see payment status information: Payment has been successfully processed

  @configSubmitCvvOnly @smokeTest @fullTest
  Scenario: Successful payment when cvv field is selected to submit
    When User fills CVC field "123"
    And THREEDQUERY response set to NOT_ENROLLED_N
    And User clicks Pay button - AUTH response set to OK
    Then User will see payment status information: Payment has been successfully processed
    And User will not see card number and expiration date fields

  @configBypassCards @bypassCards @smokeTest @fullTest
  Scenario: Successful payment using non-3d "PIBA" card type
    When User fills payment form with credit card number "3089500000000000021", expiration date "12/23"
    And User clicks Pay button - AUTH response set to OK
    Then User will see payment status information: Payment has been successfully processed
    And User will see that notification frame has "green" color
