package com.ivansaprykin.testtasks.bostongene.multithreading;

import java.util.HashMap;
import java.util.Map;

/**
 * Собираем цисло по частям: сначала тысячи, затем сотни, затем десятки и единицы.
 * Получив значения по одной части, удаляем ее описание из строки, и передаем строку с описанием числа далее.
 * К концу получим пустутю строку, описывающую число и полностью ей соответствующее натуральное число.
 *
 * Строка, поступающая на вход считается валидной.
 */
class WordsToNumbersTransformer {

    private static final String ZERO = "zero";
    private static final String[] DIGITS = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
    private static final String[] TENS = {"twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};
    private static final String[] TEENS = {"ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
    private static final String HUNDRED = "hundred";
    private static final String THOUSAND = "thousand";

    private Map<String, Integer> digitsMap;
    private Map<String, Integer> teensMap;
    private Map<String, Integer> tensMap;

    public WordsToNumbersTransformer() {

        digitsMap = new HashMap<>();
        for(int digitValue = 1, i = 0; i < DIGITS.length; i++, digitValue++)
            digitsMap.put(DIGITS[i], digitValue);

        teensMap = new HashMap<>();
        for(int teenValue = 10, i = 0; i < TEENS.length; i++, teenValue++)
            teensMap.put(TEENS[i], teenValue);

        tensMap = new HashMap<>();
        for(int tenValue = 20, i = 0; i < TENS.length; i++, tenValue += 10)  //TODO refactor this
            tensMap.put(TENS[i], tenValue);

    }

    public Numeral createNumberFromWords(String wordsString) {
        return addLessThanHundredValuePart(addHundredPart(addThousandPart(new Numeral(wordsString, 0))));
    }

    private Numeral addThousandPart(Numeral number) {
        String numberDescr = number.getTextDescription();
        if(numberDescr.contains(THOUSAND)) {

            String thousandString = numberDescr.split(THOUSAND)[0].trim(); // берем часть строки соответствующую значению тысячи
            int thousandValue = transformOneWordNumber(thousandString) * 1000; // получаем цифровое значение

            int lastIndexOfThousandPart = numberDescr.lastIndexOf(THOUSAND); // убераем из строки, описывающей число часть с описанием тысяч
            String textDescrWithoutThousandPart = numberDescr.substring(lastIndexOfThousandPart + THOUSAND.length());

            return new Numeral(textDescrWithoutThousandPart, thousandValue);
        } else {
            return number;
        }
    }

    private Numeral addHundredPart(Numeral number) {
        String numberDescr = number.getTextDescription();
        if(numberDescr.contains(HUNDRED)) {

            String hundredString = numberDescr.split(HUNDRED)[0].trim();
            int hundredValue = addLessThanHundredValuePart(new Numeral(hundredString, 0)).getNumber()  * 100;
            int updatedValue = number.getNumber() + hundredValue;

            int lastIndexOfHundredPart = numberDescr.lastIndexOf(HUNDRED);
            String textDescrWithoutHundredPart = numberDescr.substring(lastIndexOfHundredPart + HUNDRED.length());

            return new Numeral(textDescrWithoutHundredPart, updatedValue);
        } else {
            return number;
        }
    }

    private Numeral addLessThanHundredValuePart(Numeral number) {
        String numberDescr = number.getTextDescription().trim();
        String[] tensSplit = numberDescr.split("\\s");

        if(numberDescr.equals("")) {
            return number; // если чило содержало только сотни и тысячи, то после их удаления дальше нет смысла искать числа
        } else if(tensSplit.length == 1) { // 0-19 и десятки
            return new Numeral(number.getNumber() + transformOneWordNumber(tensSplit[0].trim()));
        } else { // десятки и цифра
            return new Numeral(number.getNumber() + transformTwoWordsNumber(numberDescr));
        }
    }

    private int transformTwoWordsNumber(String numberDescr) {
        String[] numberDescrSplit = numberDescr.split("\\s");

        int tensPart = transformOneWordNumber(numberDescrSplit[0]);
        int digitPart = transformOneWordNumber(numberDescrSplit[1]);
        return tensPart + digitPart;
    }

    private int transformOneWordNumber(String numberDescr) {

        if(ZERO.equals(numberDescr)) {
            return 0;
        } else if(digitsMap.containsKey(numberDescr)) {
            return digitsMap.get(numberDescr);
        } else if(teensMap.containsKey(numberDescr)) {
            return teensMap.get(numberDescr);
        } else {
            return tensMap.get(numberDescr);
        }
    }
}
