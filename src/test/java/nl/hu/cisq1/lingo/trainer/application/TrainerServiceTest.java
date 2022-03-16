package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.GameRepository;
import nl.hu.cisq1.lingo.trainer.domain.*;
import nl.hu.cisq1.lingo.trainer.domain.exception.CannotStartNewRoundException;
import nl.hu.cisq1.lingo.words.application.WordService;
import nl.hu.cisq1.lingo.words.domain.Word;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TrainerServiceTest {
    private static final GameRepository mockRepository = mock(GameRepository.class);
    private static final WordService mockWordService = mock(WordService.class);


    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Starting a new game")
    void startNewGame(){
        WordService wordService = mock(WordService.class);
        when(wordService.provideRandomWord(anyInt())).thenReturn("woord");



        Game game = new Game();
        /*game.startNewGame("woord");*/
        Round round = new Round(wordService.provideRandomWord(5));


        game.getRounds().add(round);


        GameRepository gameRepository = mock(GameRepository.class);
        when(gameRepository.save(any())).thenReturn(game);

        TrainerService trainerService = new TrainerService(gameRepository, wordService);

        GameProgress gameProgress = trainerService.startNewGame();

        verify(gameRepository).save(any(Game.class));
        assertEquals(0, gameProgress.getScore());
        assertEquals(GameStatus.ACTIVE, gameProgress.getGameStatus());
        assertEquals(Arrays.asList("w",".",".",".","."), gameProgress.getHint());
    }

    @Test
    @DisplayName("Throws exception if game is already lost")
    void startNewRoundWhenGameIsLost(){
        WordService wordService = mock(WordService.class);
        when(wordService.provideRandomWord(anyInt())).thenReturn("woord");



        Game game = new Game();
        game.setId((long)6);
        /*game.startNewGame("woord");*/
        Round round = new Round(wordService.provideRandomWord(5));
        game.setGameStatus(GameStatus.ACTIVE);

        game.getRounds().add(round);


        GameRepository gameRepository = mock(GameRepository.class);
        when(gameRepository.save(any())).thenReturn(game);
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game));

        TrainerService trainerService = new TrainerService(gameRepository, wordService);

        GameProgress gameProgress = trainerService.startNewRound((long)6);
        game.setGameStatus(GameStatus.LOST);

        verify(gameRepository).save(any(Game.class));
        assertThrows(
                CannotStartNewRoundException.class,
                () -> trainerService.startNewRound(6L)
        );
    }
}
