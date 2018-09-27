package com.ivansaprykin.testtasks.bostongene.multithreading;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

public class UserInputCheckerTests {

    @DisplayName("checking correct input")
    @ParameterizedTest
    @CsvFileSource(resources = "/wordsToDigitsTestCorrect.csv")
    void checkingCorrectInput(String wordsString) {
        UserInputChecker inputChecker = new UserInputChecker();
        assertTrue(inputChecker.isCorrect(wordsString), wordsString + "is correct input!");
    }

    @DisplayName("checking incorrect input")
    @ParameterizedTest
    @CsvFileSource(resources = "/wordsToDigitsTestIncorrect.csv")
    void checkingIncorrectInput(String wordsString) {
        UserInputChecker inputChecker = new UserInputChecker();
        assertFalse(inputChecker.isCorrect(wordsString), wordsString + " is not correct input!");
    }
}
