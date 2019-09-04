Feature: Animated Card tests
  As a user
  I want to check payment card functionality and
  input fields validation

  Background:
    Given User opens page with payment form

  @animatedCardRepoTest
  Scenario Outline: Credit card recognition for <cardType> and validate date on animated card
    When User fills payment form with data: "<cardNumber>", "<expirationDate>" and "<cvc>"
    Then User will see correct card icon for <cardType>
    And User will see correct data on animated credit card <formattedCardNumber>, <expirationDate> and <cvc>
    And User will see animated card is flipped, except for "AMEX"
    Examples:
      | cardNumber          | formattedCardNumber    | expirationDate | cvc  | cardType     |
      | 340000000000611     | 3400 000000 00611      | 12/23          | 1234 | AMEX         |
      | 4111110000000211    | 4111 1100 0000 0211    | 12/22          | 123  | VISA         |
      | 1801000000000901    | 1801 0000 0000 0901    | 12/23          | 123  | ASTROPAYCARD |
      | 3000000000000111    | 3000 000000 000111     | 12/23          | 123  | DINERS       |
      | 6011000000000301    | 6011 0000 0000 0301    | 12/23          | 123  | DISCOVER     |
      | 3528000000000411    | 3528 0000 0000 0411    | 12/23          | 123  | JCB          |
      | 5000000000000611    | 5000 0000 0000 0611    | 12/23          | 123  | MAESTRO      |
      | 5100000000000511    | 5100 0000 0000 0511    | 12/23          | 123  | MASTERCARD   |
      | 3089500000000000021 | 3089 5000 0000 0000021 | 12/23          | 123  | PIBA         |

  @animatedCardRepoTest
  Scenario Outline: Checking animated card translation for <language>
    When User fills payment form with data: "<cardNumber>", "<expirationDate>" and "<cvc>"
    Then User will see that labels displayed on animated card are translated into <language>
    Examples:
      | language |
      | en_GB    |
#      | en_GB    |
#      | es_ES    |
#      | sv_SE    |

  @animatedCardRepoTest
  Scenario Outline: Filling payment form with empty fields -> cardNumber "<cardNumber>", expiration: "<expiration>", cvv: "<cvv>"
    When User fills payment form with data: "<cardNumber>", "<expirationDate>" and "<cvc>"
    And User changes the field
    Then User will see "Field is required" message under no-iframe-field: <field>
    And User will see that <field> no-iframe-field is highlighted
    Examples:
      | cardNumber       | expiration | cvc | field       |
      |                  | 12/22      | 123 | CARD_NUMBER |
      | 4000000000001000 |            | 123 | EXPIRY_DATE |
      | 4000000000001000 | 12/22      |     | CVC         |

  @animatedCardRepoTest
  Scenario: Filling 3-number of cvc code for AMEX card
    When User fills payment form with credit card number "340000000000611", expiration date "12/22" and cvc "123"
    And User changes the field
    Then User will see "Field is required" message under no-iframe-field: CVC

  @animatedCardRepoTest
  Scenario Outline: Filling payment form with incomplete data (frontend validation) -> cardNumber "<cardNumber>", expiration: "<expiration>", cvv: "<cvv>"
    When User fills payment form with incorrect or missing data: card number <cardNumber>, expiration date <expiration> and cvc <cvc>
    And User clicks Pay button
    Then User will see "Field is required" message under no-iframe-field: <field>
    And User will see that <field> no-iframe-field is highlighted
    Examples:
      | cardNumber       | expiration | cvc | field       |
      | 4000000000001000 | 12/22      | 12  | CVC         |
      | 40000000         | 12/22      | 123 | CARD_NUMBER |
      | 4000000000001000 | 12         | 123 | EXPIRY_DATE |
      | 4000000000009999 | 12/22      | 123 | CARD_NUMBER |
      | 4000000000001000 | 44/22      | 123 | EXPIRY_DATE |