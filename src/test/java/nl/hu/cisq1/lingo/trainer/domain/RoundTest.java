package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidWordExeption;
import nl.hu.cisq1.lingo.trainer.domain.exception.TurnExeption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {
    private Game game;
    @BeforeEach
    void setUp() {
        this.game = new Game();
    }

    @Test
    @DisplayName("Gives the first hint based on given word")
    void firstHintIsCorrect(){
        Round round = new Round();
        round.makeFirstHint("woord");
        //Test to see if it works with 6 or 7 letter words aswell
        assertEquals(Arrays.asList("w",".",".",".","."),round.getFirstHint());
    }


    @Test
    @DisplayName("Guess counts if number of attempts is < 5, only letters are used and correct number of letter are inputted")
    void makeAValidGuess(){
        game.startNewGame("woord");

        /*Round round = new Round("woord");
        game.getRounds().add(round);*/
        Round currentRound = game.getCurrentRound();
        currentRound.makeAGuess("wraad");



        assertTrue(currentRound.getLastFeedbackFromRound().isWordValid());
        assertEquals(Arrays.asList("w",".",".",".","d"),currentRound.getLastFeedbackFromRound().getHint());
        assertEquals(List.of(Mark.CORRECT,Mark.PRESENT,Mark.ABSENT,Mark.ABSENT,Mark.CORRECT), currentRound.getLastFeedbackFromRound().getMarks());
        assertEquals(1,currentRound.getTurn());
    }

    @Test
    @DisplayName("Make a guess but the turn is higher then 5")
    void guessButToManyTurns(){
        Round round = new Round("woord");
        round.makeAGuess("woord");
        round.setTurn(5);
        assertThrows(TurnExeption.class,
                () -> round.makeAGuess("woord"));
    }
    @Test
    @DisplayName("get last feedback from round")
    void getLastFeedbackFromRound() {
        game.startNewGame("woord");
        Round currentRound = game.getCurrentRound();
        game.makeGuess("waard");
        Feedback feedback = new Feedback("waard", List.of(Mark.CORRECT,Mark.ABSENT,Mark.ABSENT,Mark.CORRECT,Mark.CORRECT));

        assertEquals(feedback.getAttempt(), currentRound.getLastFeedbackFromRound().getAttempt());
        assertEquals(feedback.getMarks(), currentRound.getLastFeedbackFromRound().getMarks());



    }


}
