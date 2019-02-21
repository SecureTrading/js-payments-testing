Feature: Credit Card
  As a user
  I want to use various payment methods using correct and incorrect credentials
  In order to check full payment funcionallity

  Background:
    Given User opens page with payment form 'https://www.securetrading.com/'

  @positive
  Scenario Outline: Successful payment using Credit Card and correct credentials
    When User fills payment form with credit card number <cardNumber>, cvc <cvc> and expiration date <expirationDate>
    And User clicks Pay button
    Then User will see information about successful payment 'Successful Payment!'
    Examples:
      | cardNumber       | cvc | expirationDate | cardType            |
      | 340000000000611  | 123 | 12/22          | AMEX                |
      | 3000000000000111 | 123 | 12/22          | DINERS              |
      | 6011000000000301 | 123 | 12/22          | DISCOVER            |
      | 3528000000000411 | 123 | 12/22          | JCB                 |
      | 5000000000000611 | 123 | 12/22          | MAESTRO             |
      | 5100000000000511 | 123 | 12/22          | MASTERCARD          |
      | 5124990000000101 | 123 | 12/22          | MASTERCARDDEBIT     |
      | 4370000000000061 | 123 | 12/22          | VPAY                |
      | 4111110000000211 | 123 | 12/22          | VISA aka Visa Debit |
      | 3000000000000111 | 123 | 12/22          | DELTA1              |
      | 4245190000000311 | 123 | 12/22          | ELECTRON            |
      | 4484000000000411 | 123 | 12/22          | PURCHASING          |

  Scenario Outline: Credit card recognition and validate date on animated card
    When User fills payment form with credit card number <cardNumber>, cvc <cvc> and expiration date <expirationDate>
    Then User will should see card icon connected to card type
    And User will should see the same provided data on animated credit card <cardNumber>, <cvc> and <expirationDate>
    Examples:
      | cardNumber       | cvc | expirationDate | cardType            |
      | 340000000000611  | 123 | 12/22          | AMEX                |
      | 3000000000000111 | 123 | 12/22          | DINERS              |
      | 6011000000000301 | 123 | 12/22          | DISCOVER            |
      | 3528000000000411 | 123 | 12/22          | JCB                 |
      | 5000000000000611 | 123 | 12/22          | MAESTRO             |
      | 5100000000000511 | 123 | 12/22          | MASTERCARD          |
      | 5124990000000101 | 123 | 12/22          | MASTERCARDDEBIT     |
      | 4370000000000061 | 123 | 12/22          | VPAY                |
      | 4111110000000211 | 123 | 12/22          | VISA aka Visa Debit |
      | 3000000000000111 | 123 | 12/22          | DELTA1              |
      | 4245190000000311 | 123 | 12/22          | ELECTRON            |
      | 4484000000000411 | 123 | 12/22          | PURCHASING          |

  Scenario Outline: Wrong card number cause payment declination
    When User fills payment form with credit card number <cardNumber>, cvc <cvc> and expiration date <expirationDate>
    And User clicks Pay button
    Then User will see information about declined payment
    Examples:
      | cardNumber       | cvc | expirationDate | cardType            |
      | 340000000000512  | 123 | 12/22          | AMEX                |
      | 3000000000000012 | 123 | 12/22          | DINERS              |
      | 6011000000000202 | 123 | 12/22          | DISCOVER            |
      | 3528000000000312 | 123 | 12/22          | JCB                 |
      | 5000000000000512 | 123 | 12/22          | MAESTRO             |
      | 5100000000000412 | 123 | 12/22          | MASTERCARD          |
      | 5124990000000002 | 123 | 12/22          | MASTERCARDDEBIT     |
      | 4370000000000012 | 123 | 12/22          | VPAY                |
      | 4111110000000112 | 123 | 12/22          | VISA aka Visa Debit |
      | 4310720000000042 | 123 | 12/22          | DELTA1              |
      | 4245190000000212 | 123 | 12/22          | ELECTRON            |
      | 4484000000000312 | 123 | 12/22          | PURCHASING          |

  Scenario Outline: Filling payment form form with incomplete data - fields validation
    When User fills payment form with incorrect or missing data: card number <cardNumber>, cvc <cvc> and expiration date <expiration>
    And User clicks Pay button
    Then User should see validation message <message> under <fieldType> field
    Examples:
      | cardNumber       | cvc | expiration | message | fieldType  |
      |                  | 123 | 12/22      | TODO    | number     |
      | 3000000000000012 |     | 12/22      | TODO    | cvc        |
      | 6011000000000202 | 123 |            | TODO    | expiryDate |

  Scenario: Submit payment form without data - fields validation
    When User clicks Pay button
    Then He will see validation message "TODO" under credit card number field

  Scenario: Check CVV/CVC tooltip info
    When I click tooltip icon next to CVV/CVC
    Then I should see information about this field

  #ToDo
  Scenario: Successful payment using ApplePay payment methods
    When User chooses ApplePay as payment method
    And User enter his email on ApplePay popup
    Then User will see information about successful payment 'Successful Payment!'

    #ToDo
  Scenario: Successful payment using VisaCheckout payment methods
    When User chooses VisaCheckout as payment method
    And User enter his email on VisaCheckout popup
    Then User will see information about successful payment 'Successful Payment!'

    #ToDo
  Scenario: Wrong ApplePay credentials cause payment declination
    When User chooses ApplePay as payment method
    And User enter wrong email on ApplePay popup
    Then User will see information about successful payment 'Successful Payment!'

    #ToDo
  Scenario: Wrong VisaCheckout credentials cause payment declination
    When User chooses VisaCheckout as payment method
    And User enter wrong email on VisaCheckout popup
    Then User will see information about declined payment

    #ToDo
  Scenario: Successful payment using PayPal payment methods
    When User chooses PayPal as payment method
    And User enter his email on PayPal popup
    Then User will see information about declined payment

       #ToDo
  Scenario: Wrong PayPal credentials cause payment declination
    When User chooses PayPal as payment method
    And User enter wrong email on VisaCheckout popup
    Then User will see information about declined payment



