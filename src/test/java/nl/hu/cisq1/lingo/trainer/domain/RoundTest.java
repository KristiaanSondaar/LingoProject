package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {

    @Test
    @DisplayName("Guess counts if number of attempts is < 5, only letters are used and correct number of letter are inputted")
    void makeACorrectGuess(){
        Feedback feedback = new Feedback("woord", List.of(Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT));
        Round round = new Round(1,"woord");
        assertEquals(round.makeAGuess("woord"), feedback);
    }
    @Test
    @DisplayName("Guess counts if number of attempts is < 5, only letters are used and correct number of letter are inputted")
    void makeAFailedGuess(){
        Feedback feedback = new Feedback("woord", List.of(Mark.ABSENT,Mark.ABSENT,Mark.ABSENT,Mark.CORRECT,Mark.CORRECT));
        Round round = new Round(1,"paard");
        assertEquals(round.makeAGuess("woord"), feedback);
    }
}
