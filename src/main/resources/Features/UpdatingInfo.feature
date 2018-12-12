Feature: User Updates Info

  Scenario Outline: Updating Info
    Given User is created
    And User navigates to Update info page
    When User updates name with the value "<name>"
    And User updates username with the value "<username>"
    And User updates email with the value "<email>"
    And User updates address with the value "<address>"
    And User updates phone with the value "<phone>"
    And User clicks Save
    Then Data changed

    Examples:
    |name| username | email | address | phone |
    |test1|test1    |test1  |test1    |test   |
    |test2|test2    |test2  |test2    |test2  |