package nl.hu.cisq1.lingo.trainer.domain;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Feedback {
    private String attempt;
    List<Mark> mark;

    public Feedback(String attempt, List<Mark> mark) {
        this.attempt = attempt;
        this.mark = mark;
    }

    public boolean isWordGuessed(){
        return !mark.contains(Mark.ABSENT) && !mark.contains(Mark.INVALID) && !mark.contains(Mark.PRESENT);
    }
    public boolean isWordValid(){
        return !mark.contains(Mark.INVALID);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(attempt, feedback.attempt) && Objects.equals(mark, feedback.mark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attempt, mark);
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "attempt='" + attempt + '\'' +
                ", mark=" + mark +
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
