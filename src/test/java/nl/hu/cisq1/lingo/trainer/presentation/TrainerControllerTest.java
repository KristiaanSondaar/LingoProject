package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.GameProgress;
import nl.hu.cisq1.lingo.trainer.domain.exception.CannotStartNewRoundException;
import nl.hu.cisq1.lingo.trainer.domain.exception.GameNotFoundException;
import nl.hu.cisq1.lingo.trainer.domain.exception.TurnExeption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainerControllerTest {
    private TrainerController trainerController;
    private TrainerService trainerService;

    @BeforeEach
    void setUp() {
        trainerService = mock(TrainerService.class);
        trainerController = new TrainerController(trainerService);
    }

    @Test
    void startGame() {
        GameProgress mockProgress = mock(GameProgress.class);
        when(trainerService.startNewGame()).thenReturn(mockProgress);

        GameProgress actualGameProgress = trainerController.startGame();

        assertEquals(mockProgress, actualGameProgress);
    }

    @Test
    void makeGuess() {
        GameProgress mockProgress = mock(GameProgress.class);
        when(trainerService.makeGuess("woord", 6L)).thenReturn(mockProgress);

        GameProgress actualGameProgress = trainerController.makeGuess(6L, "woord");
        assertEquals(mockProgress, actualGameProgress);
    }

    @Test
    void newRound() {
        GameProgress mockProgress = mock(GameProgress.class);
        when(trainerService.startNewRound(6L)).thenReturn(mockProgress);

        GameProgress actualGameProgress = trainerController.newRound(6L);
        assertEquals(mockProgress, actualGameProgress);
    }

    //ExeptionTests
    @Test
    void gameNotFoundExeption(){
        when(trainerService.startNewRound(6L)).thenThrow(GameNotFoundException.class);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> trainerController.newRound(6L));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

    }

    @Test
    void cannotStartNewRoundExeption(){
        when(trainerService.startNewRound(6L)).thenThrow(CannotStartNewRoundException.class);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> trainerController.newRound(6L));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

    }
    @Test
    void turnExeption(){
        when(trainerService.makeGuess("woord",6L)).thenThrow(TurnExeption.class);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> trainerController.makeGuess(6L,"woord"));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

    }
}
