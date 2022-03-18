package nl.hu.cisq1.lingo.trainer.domain.exception;

public class InvalidWordExeption extends RuntimeException{
    public InvalidWordExeption(String word){
        super("The word " + word + " is not a valid word");
    }
}
