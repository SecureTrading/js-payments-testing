Feature: Credit Card
  As a user
  I want to fill payment form using correct credentials
  In order to make successful payment

  Background:
    Given User opens page with payment form

  @positive
  Scenario Outline: Successful payment using correct credentials
    When User fills payment form with credit card number <cardNumber>, cvc <cvc> and expiration date <expirationDate>
    And User clicks Pay button
    Then User will see information about successful payment
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

  Scenario Outline: Credit card recognition
    When User fills credit card number field with number <cardNumber>
    Then User will should see card icon connected to card type
    Examples:
      | cardNumber       | cardType            |
      | 340000000000611  | AMEX                |
      | 3000000000000111 | DINERS              |
      | 6011000000000301 | DISCOVER            |
      | 3528000000000411 | JCB                 |
      | 5000000000000611 | MAESTRO             |
      | 5100000000000511 | MASTERCARD          |
      | 5124990000000101 | MASTERCARDDEBITv    |
      | 4370000000000061 | VPAY                |
      | 4111110000000211 | VISA aka Visa Debit |
      | 3000000000000111 | DELTA1              |
      | 4245190000000311 | ELECTRON            |
      | 4484000000000411 | PURCHASING          |

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