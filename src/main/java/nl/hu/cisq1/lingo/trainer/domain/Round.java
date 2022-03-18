package nl.hu.cisq1.lingo.trainer.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidWordExeption;
import nl.hu.cisq1.lingo.trainer.domain.exception.TurnExeption;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Round extends AbstractEntity {
    //5 turns total
    private int turn;
    private String wordToGuess;
    @ElementCollection
    private List<String> firstHint;
    @OneToMany
    private List<Feedback> feedbackList = new ArrayList<>();

    public Round(String wordToGuess){
        this.wordToGuess = wordToGuess;
        makeFirstHint(wordToGuess);
    }

    public void makeFirstHint(String wordToGuess){
        List<String> firstHint = Arrays.asList(wordToGuess.split(""));
        for (int i = 0; i < wordToGuess.length(); i++) {
                if(i!=0){
                    firstHint.set(i, ".");
                }
            }
        this.firstHint = firstHint;
    }

    //Making a guess
    //First up the turn by 1
    //Check if the word is valid (NO NEED ALREADY DONE IN WORDSERVICE)
    //then we loop through the wordToGuess and check if there are letters that are the same, if so they will be CORRECT
    //if the letter is not in the same place, check if its in the word at all, set it to PRESENT
    //else set it to absent
    //Then we update the feedback and save it to the feedbacklist for progess later on
    public void makeAGuess(String guessedWord) {
            List<Mark> marks = new ArrayList<>();
         if (turn < 5) {
                for (int i = 0; i < wordToGuess.length(); i++) {
                    if (String.valueOf(guessedWord.charAt(i)).equals(String.valueOf(wordToGuess.charAt(i)))) {
                        marks.add(Mark.CORRECT);
                    } else if (wordToGuess.contains(String.valueOf(guessedWord.charAt(i)))) {
                        marks.add(Mark.PRESENT);
                    } else {
                        marks.add(Mark.ABSENT);
                    }
                }
                turn++;

                Feedback feedback = new Feedback(guessedWord, marks);
                feedback.setHint(feedback.giveHint(firstHint,wordToGuess));
                feedbackList.add(feedback);
            } else {
                throw new TurnExeption();
            }
    }
    public boolean gameIsLost(){
        List<Mark> lastMarks = getLastFeedbackFromRound().getMarks();
        return this.turn == 5 && lastMarks.contains(Mark.ABSENT) || (lastMarks.contains(Mark.PRESENT));
    }

    public Feedback getLastFeedbackFromRound(){
        return feedbackList.get(feedbackList.size() - 1);
    }
}
