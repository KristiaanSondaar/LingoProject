package nl.hu.cisq1.lingo.trainer.domain.exception;

public class TurnExeption extends RuntimeException{
    public TurnExeption() {
        super("Amount of turns exceeded, start a new game (and git gud)");
    }

}
