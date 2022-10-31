Feature: Get book by ISBN

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


  @SampleAPITest
  Scenario: User calls gets information from an API 2
    Given the API exists
    When A user gets info from the API 2
    Then the status code is 200
    And response includes values above these thresholds
      | length | 2             |
    And response includes values below these thresholds
      | length | 500             |



