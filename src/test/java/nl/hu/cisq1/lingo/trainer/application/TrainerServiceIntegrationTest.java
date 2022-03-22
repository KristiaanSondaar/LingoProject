package nl.hu.cisq1.lingo.trainer.application;


import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.trainer.data.GameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.GameProgress;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;

import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Import(CiTestConfiguration.class)
@ActiveProfiles("ci")
public class TrainerServiceIntegrationTest {

    @Autowired
    TrainerService trainerService;
    @Autowired
    WordService wordService;
    @Autowired
    GameRepository gameRepository;


    @Test
    @DisplayName("Starting the game, succes")
    void startGame(){
        GameProgress newGame = trainerService.startNewGame();

        assertEquals(0, newGame.getScore());
        assertEquals(GameStatus.ACTIVE,newGame.getGameStatus());
    }

    @Test
    @DisplayName("Test if you can guess")
    void guess(){
        //Making a game so the CI test doesnt give back GameNotFound

        GameProgress newGame = trainerService.startNewGame();

        GameProgress gameProgress = trainerService.makeGuess("pizza", newGame.getGameId());

        assertTrue(gameProgress.getScore() > 0);
        assertEquals(GameStatus.ACTIVE,gameProgress.getGameStatus());
        assertTrue(gameProgress.getFeedbackList().size() > 0);
    }

    @AfterEach
    void clearTestData() {
        // Remove test fixtures from test database after each test case
        gameRepository.deleteAll();
    }
}
