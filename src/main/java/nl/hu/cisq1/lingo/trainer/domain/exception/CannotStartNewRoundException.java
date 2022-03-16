package nl.hu.cisq1.lingo.trainer.domain.exception;

import nl.hu.cisq1.lingo.trainer.domain.GameStatus;

public class CannotStartNewRoundException extends RuntimeException{
    public CannotStartNewRoundException(GameStatus gameStatus) {
        super("Could not start a new game round because you have " + gameStatus);
    }

}
