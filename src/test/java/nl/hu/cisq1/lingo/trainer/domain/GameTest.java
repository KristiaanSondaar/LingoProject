package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.CannotStartNewRoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;

    @BeforeEach
    void setUp() {
        this.game = new Game();
    }

    @Test
    @DisplayName("Start a new game")
    void startNewGame() {
        game.startNewGame("woord");
        Feedback feedback = new Feedback("koord", List.of(Mark.INVALID,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT));
        List<Feedback> feedbackList = Arrays.asList(feedback);
        List<String> hintList = Arrays.asList("w",".",".",".",".");
        Round round = new Round(1,"woord", hintList,feedbackList);
        Game mockGame = new Game(GameStatus.ACTIVE,0,Arrays.asList(round));

        assertEquals(GameStatus.ACTIVE, game.getGameStatus());
        assertNotNull(game.getRounds());
        assertEquals(mockGame.getCurrentGameProgress().getHint(),game.getCurrentGameProgress().getHint());
    }

    @Test
    @DisplayName("Start new round while game is still active, not lost yet")
    void startNewRound() {
        game.startNewGame("woord");
        game.makeGuess("woord");
        game.startNewRound("koorde");

        assertEquals(10,game.getScore());
        assertEquals(Arrays.asList("k",".",".",".",".","."),game.getCurrentGameProgress().getHint());
        assertEquals(GameStatus.ACTIVE, game.getGameStatus());


    }
    @Test
    @DisplayName("Start new round while game is lost")
    void startNewRoundWhileGameIsAlreadyLost() {
        game.startNewGame("woord");
        game.makeGuess("waard");
        game.makeGuess("waard");
        game.makeGuess("waard");
        game.makeGuess("waard");
        game.makeGuess("waard");

        assertEquals(GameStatus.LOST, game.getGameStatus());

        assertEquals(0,game.getScore());
        assertThrows(
                CannotStartNewRoundException.class,
                () -> game.startNewRound("koorde")
        );

    }

    @Test
    @DisplayName("Make a correct guess when game is active")
    void makeGuess() {
        game.startNewGame("woord");
        game.makeGuess("woord");
        assertEquals(10,game.getScore());
        assertEquals(Arrays.asList("w",".",".",".","."), game.getCurrentGameProgress().getHint());
    }
    @Test
    @DisplayName("Gamestatus is set to Lost after 5 failed tries")
    void make5FailedGuessAttempts() {
        game.startNewGame("woord");
        game.makeGuess("waard");
        game.makeGuess("waard");
        game.makeGuess("waard");
        game.makeGuess("waard");
        game.makeGuess("waard");

        assertEquals(0,game.getScore());
        assertEquals(Arrays.asList("w",".",".",".","."), game.getCurrentGameProgress().getHint());
        assertEquals(GameStatus.LOST,game.getGameStatus());
    }

    @Test
    @DisplayName("Get correct word length")
    void getCurrentWordLength() {
        game.startNewGame("woord");

        assertEquals(5, game.getCurrentWordLength());
    }
    @Test
    @DisplayName("Get correct word length")
    void getCurrentRound() {
        game.startNewGame("woord");
        Feedback feedback = new Feedback("koord", List.of(Mark.INVALID,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT));
        List<Feedback> feedbackList = Arrays.asList(feedback);
        List<String> hintList = Arrays.asList("w",".",".",".",".");
        Round round = new Round(1,"woord", hintList,feedbackList);

        game.getRounds().add(round);

        assertEquals(round, game.getCurrentRound());
    }
}
