Feature: Testing Asynchronous APIs

  @GeneralAPIValidationTest
  Scenario: The User has APIs they would like to validate
    Given the API exists
    When A user gets info from the API 1
    Then the status code is 200
    Given the API exists
    When A user gets info from the API 2
    Then the status code is 200
    Given the API exists
    When A user gets info from the API 3
    Then the status code is 200


  @GoogleBooksAPIValidation
  Scenario: User calls web service to get a book by its ISBN
    Given the user can query by isbn 9781451648546
    When A user gets info from the API 1
    Then the status code is 200
    And response includes the following
      | totalItems | 1             |
      | kind       | books#volumes |
    And response includes the following in any order
      | items.volumeInfo.title         | Steve Jobs                |
      | items.volumeInfo.publisher     | Simon and Schuster        |
      | items.volumeInfo.pageCount     | 656                       |
      | items.volumeInfo.printType     | BOOK                      |
      | items.volumeInfo.categories[0] | Biography & Autobiography |
      | items.volumeInfo.averageRating | 4                         |

  @RandomFactAPITest
  Scenario: User gets information from an Random Fact API
    Given the API exists
    When A user gets info from the API 2
    Then the status code is 200
    And response includes values above these thresholds
      | length | 2             |
    And response includes values below these thresholds
      | length | 500             |

  @BitCoinAPIValidation
  Scenario: User gets information from an BitCoin API
    Given the API exists
    When A user gets info from the API 3
    Then the status code is 200
    And the user checks the price

  @BitCoinAPIValidationDynamic
  Scenario: User gets information from an BitCoin API in multiple currencies
    Given the API exists
    When A user gets info from the API 3
    Then the status code is 200
    And the price is checked in the currency USD
    And the price is checked in the currency GBP
    And the price is checked in the currency EUR


  @PostmanPOSTTestStatic
  Scenario: User wants to POST to API 4
    Given the API exists
    When A user gets info from the API 4
    Then the status code is 200
    And the user conducts static post to the API 4
    Then the status code is 200

  @PostmanPOSTTestDynamic
  Scenario: User wants to dynamically POST to API 4
    Given the API exists
    When A user gets info from the API 4
    Then the status code is 200
    And the user is posting to the API 4
    And the user posts to the API with path /ping
    And the user posts to the API with path /field1
    And the user posts to the API with path /field2
    And the user posts to the API with path /field3







