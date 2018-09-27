package com.ivansaprykin.testtasks.bostongene.multithreading;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordsToNumbersTransformerTests {

//"zero thousand one hundred, 100",

    @ParameterizedTest
    @CsvFileSource(resources = "/wordsToDigitsTestCorrect.csv")
    @DisplayName("checkingCorrectWordsToNumbersTransformation")
    void checkingCorrectWordsToNumbersTransformation(String wordsString, int numericalValueExpectedResult) {
        WordsToNumbersTransformer transformer = new WordsToNumbersTransformer();

        assertEquals(numericalValueExpectedResult, transformer.createNumberFromWords(wordsString).getNumber(),
                wordsString + " should equal " + numericalValueExpectedResult);
    }
}
