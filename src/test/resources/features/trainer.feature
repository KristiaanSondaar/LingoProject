Feature: Lingo trainer
  As a Player,
  I want to be able to guess a random word,
  In order to practice the game of Lingo

  Scenario: Start new game
    When A request is done to start a new game
    Then  A random word will be returned and the first letter of the word shown

  Scenario Outline: Start a new round
    Given I am playing a game
    And the round was won
    And the last word had "<previous length>" letters
    When  I start a new round
    Then the word to guess has "<next length>" letters

    Examples:
    | previous length | next length |
    |5 | 6 |
    |6 | 7 |
    |7 | 5 |

    #failure path
    Given I am playing a game
      And the round was lost
      Then i cannot start a new round


  Scenario Outline: Guessing a word
    Given I am playing a game
    And The number of guesses is < 5
    When I guess a word
    Then The "<guess>" is checked against the "<word>" and "<feedback>" is given back

    Examples:
    | guess | word | feedback |
    | BERGEN | BAARD | INVALID, INVALID, INVALID, INVALID, INVALID, INVALID |
    | BONJE  | BAARD | CORRECT, ABSENT, ABSENT, ABSENT, ABSENT              |
    | BARST  | BAARD | CORRECT, CORRECT, PRESENT, ABSENT, ABSENT            |
    | BEDDE  | BAARD | CORRECT, ABSENT, PRESENT, ABSENT, ABSENT             |
    | BAARD  | BAARD | CORRECT, CORRECT, CORRECT, CORRECT, CORRECT          |

    #failure path
    Given I am guessing a word
      And the number of guesses > 5
      Then  the game is over

