Feature: Able to fetch clipboard records
  A publicly accessible endpoint should allow clipboard data to be fetched

  Scenario Outline: Lookup has results
    Given A user id "<id>"
    When Clipboard records are fetched
    Then A list of clipboard records is returned

    Examples:
      | id      |
      | 1       |

  Scenario Outline: Lookup returns not found
    Given A user id "<id>"
    When Clipboard records are fetched
    Then A not found http exception is returned

    Examples:
      | id      |
      | -1      |


