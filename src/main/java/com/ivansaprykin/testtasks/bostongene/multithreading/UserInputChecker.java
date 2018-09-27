package com.ivansaprykin.testtasks.bostongene.multithreading;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
Проверка пользовательского ввода.
 Условно разделим ввод на:
    тысячи и сотни
    десятки и единицы.
 Первую часть можно задать 2 способами: nine thousand nine hundred либо ninety nine hundred
 Вторую часть можно задать 3 способами: цифры (0-9), единое числительное (10-19), десятки плюс единицы(которых может не быть)

 Правило проверки такое:
    перед "thousand" может стоять только цифра
    перед "hundred" может стоять:
        если присутвует слово "thousand": только цифра
        если отсутвует слово "thousand": цифра, число от 10 до 19, десяток, вместе десяток и цифра (обозначим эту комбинацию 'ONE_TO_NINETY_NINE')
    после "hundred" может стоять 'ONE_TO_NINETY_NINE' или ноль

    могут быть указаны только тысячи, или только сотни, или только числа до 100.
    между словами должны быть пробелы
 */
class UserInputChecker {

    private static final String ZERO = "zero";
    private static final String DIGIT_PATTERN = "(one|two|three|four|five|six|seven|eight|nine)";
    private static final String TEENS_PATTERN = "(ten|eleven|twelve|thirteen|fourteen|fifteen|sixteen|seventeen|eighteen|nineteen)";
    private static final String TENS_PATTERN = "(twenty|thirty|forty|fifty|sixty|seventy|eighty|eight|ninety)";
    private static final String HUNDRED = "hundred";
    private static final String THOUSAND = "thousand";

    private static final String THOUSANDS_AND_HUNDREDS_PATTERN =
            "(" + DIGIT_PATTERN + "\\s" + THOUSAND + "\\s*(\\s" + DIGIT_PATTERN + "\\s" + HUNDRED + "\\s*)?)" ;
    private static final String ONE_TO_NINETY_NINE =
            "((" + DIGIT_PATTERN +"|"+ TEENS_PATTERN + "|" + TENS_PATTERN + ")|(" + TENS_PATTERN + "\\s" + DIGIT_PATTERN + "))";
    private static final String HUNDREDS_PATTERN = "(" + ONE_TO_NINETY_NINE + "\\s" + HUNDRED + "\\s*)";
    private static final String ZERO_TO_NINETY_NINE = "(" + ONE_TO_NINETY_NINE + "|" + ZERO+ ")?";
    private static final String INPUT_PATTERN = "(" + THOUSANDS_AND_HUNDREDS_PATTERN + "|" + HUNDREDS_PATTERN + ")?"+ ZERO_TO_NINETY_NINE ;


    public boolean isCorrect(String userInput) {
        if(userInput.equals("")) return false;
        Pattern pattern = Pattern.compile(INPUT_PATTERN, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        Matcher matcher = pattern.matcher(userInput);

        return matcher.matches();
    }

}
