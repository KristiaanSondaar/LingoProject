package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.GameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.GameProgress;
import nl.hu.cisq1.lingo.trainer.domain.exception.GameNotFoundException;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.springframework.stereotype.Service;

@Service
public class TrainerService {
    private GameRepository gameRepository;
    private WordService wordService;

    public TrainerService(GameRepository gameRepository, WordService wordService) {
        this.gameRepository = gameRepository;
        this.wordService = wordService;
    }

    public GameProgress startNewGame(){
        String newWordToGuess = this.wordService.provideRandomWord(5);
        Game game = new Game();
        game.startNewGame(newWordToGuess);
        gameRepository.save(game);
        return game.getCurrentGameProgress();
    }

    public GameProgress startNewRound(Long gameId){
        Game game = getGameById(gameId);
        int currentWordLength = game.getCurrentWordLength();
        String newWordToGuess = this.wordService.provideRandomWord(currentWordLength + 1);
        game.startNewRound(newWordToGuess);
        this.gameRepository.save(game);
        return game.getCurrentGameProgress();
    }

    private Game getGameById(Long gameId) throws GameNotFoundException{
        return this.gameRepository
                .findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId));

    }
}
