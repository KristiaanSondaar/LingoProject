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
    private TrainerService trainerService;
    @Autowired
    private GameRepository gameRepository;


    @Test
    @DisplayName("Test if you can guess")
    void guess(){
        GameProgress newGame = trainerService.startNewGame();
        GameProgress gameProgress = trainerService.makeGuess("woord", newGame.getId());

        assertEquals(0, gameProgress.getScore());
        assertEquals(GameStatus.ACTIVE,gameProgress.getGameStatus());
    }
}
