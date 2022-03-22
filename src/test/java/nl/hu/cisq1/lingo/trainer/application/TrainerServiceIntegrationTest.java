package nl.hu.cisq1.lingo.trainer.application;


import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.trainer.data.GameRepository;
import nl.hu.cisq1.lingo.trainer.domain.GameProgress;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@Import(CiTestConfiguration.class)
public class TrainerServiceIntegrationTest {

    @Autowired
    TrainerService trainerService;


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
        GameProgress gameProgress = trainerService.makeGuess("pizza", 6L);

        assertTrue(gameProgress.getScore() > 0);
        assertEquals(GameStatus.ACTIVE,gameProgress.getGameStatus());
    }
}
