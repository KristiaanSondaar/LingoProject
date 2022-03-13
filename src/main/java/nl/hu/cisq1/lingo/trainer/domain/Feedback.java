package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Feedback {
    private String attempt;
    List<Mark> marks;
    List<String> hint;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(attempt, feedback.attempt) && Objects.equals(marks, feedback.marks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attempt, marks);
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "attempt='" + attempt + '\'' +
                ", mark=" + marks +
                '}';
    }

    //https://stackoverflow.com/questions/1795402/check-if-a-string-contains-a-special-character
    /*public boolean isWordValid(){
        Pattern pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(attempt);
        boolean bool = matcher.find();
        if(bool){
            return false;
        } else{
            return true;
        }

    }*/
}
