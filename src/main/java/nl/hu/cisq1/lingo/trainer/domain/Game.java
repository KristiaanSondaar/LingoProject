package nl.hu.cisq1.lingo.trainer.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.hu.cisq1.lingo.trainer.domain.exception.CannotStartNewRoundException;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Game extends AbstractEntity {
    private GameStatus gameStatus;
    private int score;
    @OneToMany
    private List<Round> rounds = new ArrayList<>();


    public GameProgress getCurrentGameProgressById(Long gameId){
        return new GameProgress(this.score, getCurrentRound().getFirstHint(), gameStatus, getCurrentRound().getFeedbackList());
    }
    public GameProgress getCurrentGameProgress(){
        return new GameProgress(this.score, getCurrentRound().getFirstHint(), gameStatus, getCurrentRound().getFeedbackList());
    }
    public void startNewGame(String wordToGuess){
        setGameStatus(GameStatus.ACTIVE);
        this.score = 0;
        Round newRound = new Round(wordToGuess);
        rounds.add(newRound);
    }
    public void startNewRound(String wordToGuess){
        if(this.gameStatus.equals(GameStatus.ACTIVE)){
            Round newRound = new Round(wordToGuess);
            rounds.add(newRound);
        } else{
            throw new CannotStartNewRoundException(gameStatus);
        }
    }
    public void makeGuess(String attempt){
        this.getCurrentRound().makeAGuess(attempt);
        if(getCurrentRound().getLastFeedbackFromRound().isWordGuessed()){
            this.setScore(score + 10);
        } else if (getCurrentRound().gameIsLost()){
            this.setGameStatus(GameStatus.LOST);
        }
    }


    public int getCurrentWordLength(){
        return getCurrentRound().getWordToGuess().length();
    }
    public Round getCurrentRound(){
        return rounds.get(rounds.size() - 1);
    }
}
