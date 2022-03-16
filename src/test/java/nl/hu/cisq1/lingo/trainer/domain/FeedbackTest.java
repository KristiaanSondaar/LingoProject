package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {

    @Test
    @DisplayName("word counts as guessed if all letters are correct")
    void wordIsGuessed(){
        Feedback feedback = new Feedback("woord", List.of(Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT));
        assertTrue(feedback.isWordGuessed());
    }
    @Test
    @DisplayName("word counts as guessed if all letters are correct")
    void wordIsNotGuessed(){
        Feedback feedback = new Feedback("woord", List.of(Mark.ABSENT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT));
        assertFalse(feedback.isWordGuessed());
    }

    @Test
    @DisplayName("word counts as guessed if all letters are valid")
    void guessIsValid(){
        Feedback feedback = new Feedback("woord", List.of(Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT));
        assertTrue(feedback.isWordValid());
    }
    @Test
    @DisplayName("word counts as guessed if all letters are valid")
    void guessIsInvalid(){
        Feedback feedback = new Feedback("*oord", List.of(Mark.INVALID,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT));
        assertFalse(feedback.isWordValid());
    }

    @ParameterizedTest
    @MethodSource("provideHintExamples")
    @DisplayName("Provides an accurate hint")
    void hintIsAccurate(List<Mark> marks,List<String> previousHint, String wordToGuess, List<String> expected){
        Feedback feedback = new Feedback("woord", marks);
        assertEquals(expected, feedback.giveHint(previousHint, wordToGuess));
    }

    static Stream<Arguments> provideHintExamples(){
        return Stream.of(
                //Test if it detects and adds "CORRECT" letters to hint
                Arguments.of(List.of(Mark.CORRECT,Mark.ABSENT,Mark.ABSENT,Mark.CORRECT,Mark.CORRECT),Arrays.asList("w",".",".",".","."), "woord", Arrays.asList("w",".",".","r","d")),
                Arguments.of(List.of(Mark.CORRECT,Mark.CORRECT,Mark.ABSENT,Mark.CORRECT,Mark.CORRECT),Arrays.asList("w",".",".",".","."), "woord", Arrays.asList("w","o",".","r","d")),
                //Test if it can remember previous hint and keep the correct letters
                Arguments.of(List.of(Mark.CORRECT,Mark.ABSENT,Mark.ABSENT,Mark.ABSENT,Mark.ABSENT),Arrays.asList("w",".",".","r","d"), "woord", Arrays.asList("w",".",".","r","d"))
        );
    }


}
