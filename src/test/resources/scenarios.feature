Feature: calculations

  Background: adding data into the database
    Given calculations table
      | firstNumber | firstNumberRadix | secondNumber | secondNumberRadix | processedAt         | operationType |
      | 10          | 10               | 20           | 10                | 2023-11-11T14:13:22 | +             |
      | 101         | 2                | 11           | 2                 | 2023-12-11T16:13:22 | -             |
      | 15          | 8                | 56           | 8                 | 2023-12-11T17:13:22 | *             |
      | 3242FD      | 16               | 435C         | 16                | 2023-12-11T18:13:22 | /             |

  @Before
  Scenario: get all calculations according to given data
    When I perform search request "http://localhost:8080/search"
    Then I should receive response status code 200
    And Body should contain calculations that are given into the table

  @Before
  Scenario Outline: get all calculations according to given operationType
    When I perform search request "http://localhost:8080/search?operationType=/"
    Then I should receive response status code 200
    And Body should contain <calculationNumber> calculations
    Examples:
      | calculationNumber |
      | 1                 |

  @Before
  Scenario Outline: get the result of calculation
    When I perform get request "http://localhost:8080/add" with parameters <firstNumber> and <secondNumber> and <firstNumberRadix> and <secondNumberRadix>
    Then I see the result as <result>
    Examples:
      | firstNumber | secondNumber | firstNumberRadix | secondNumberRadix | result |
      | 10          | 20           | 10               | 10                | 30     |