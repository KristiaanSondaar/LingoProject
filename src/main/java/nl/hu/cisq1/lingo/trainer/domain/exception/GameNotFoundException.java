package nl.hu.cisq1.lingo.trainer.domain.exception;

public class GameNotFoundException extends RuntimeException{
    public GameNotFoundException(Long gameId) {
        super("Could not find game with Id " + gameId);
    }

}
