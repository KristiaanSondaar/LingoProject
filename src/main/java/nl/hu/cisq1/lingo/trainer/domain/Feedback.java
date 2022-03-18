package nl.hu.cisq1.lingo.trainer.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Feedback extends AbstractEntity{
    private String attempt;

    @ElementCollection
    private List<Mark> marks;
    @ElementCollection
    private List<String> hint;

    public Feedback(String attempt, List<Mark> mark) {
        this.attempt = attempt;
        this.marks = mark;
    }

    public boolean isWordGuessed(){
        return !marks.contains(Mark.ABSENT) && !marks.contains(Mark.INVALID) && !marks.contains(Mark.PRESENT);
    }
    public boolean isWordValid(){
        return !marks.contains(Mark.INVALID);
    }

    public List<String> giveHint(List<String> previousHint, String wordToGuess){
        //Word to guess is "woord"
        //previous guess was "baard"
        //So feedback is List.of(Mark.ABSENT,Mark.ABSENT,Mark.ABSENT,Mark.CORRECT,Mark.CORRECT)
        //previous hint was Arrays.asList("w",".",".",".",".")
        //New hint should be Arrays.asList("w",".",".","r","d")
        List<String> newHint = new ArrayList<>();
        for(int i = 0; i < marks.size(); i++){
            if(marks.get(i).equals(Mark.CORRECT) || previousHint.get(i).equals(String.valueOf(wordToGuess.charAt(i)))){
                newHint.add(String.valueOf(wordToGuess.charAt(i)));
            } else {
               newHint.add(".");
            }
        }
        return newHint;
    }
}
