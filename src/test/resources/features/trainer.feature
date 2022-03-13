Feature: Lingo trainer
  As a Player,
  I want to be able to guess a random word,
  In order to practice the game of Lingo

  Scenario: Start new game
    When A request is done to start a new game
    Then  A random word will be returned and the first letter of the word shown

