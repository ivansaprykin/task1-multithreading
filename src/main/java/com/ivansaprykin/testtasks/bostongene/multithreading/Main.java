package com.ivansaprykin.testtasks.bostongene.multithreading;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    public static void main(String[] args) {

        String rulesForUser = "Please enter numerals from one to nine thousand nine hundred ninety nine. To terminate enter 'x'.";
        System.out.println(rulesForUser);

        LinkedList<Numeral> memory = new LinkedList<>();
        AtomicBoolean continueWork = new AtomicBoolean(true);

        Runnable userInputReadTask = () -> {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            UserInputChecker inputChecker = new UserInputChecker();
            WordsToNumbersTransformer transformer = new WordsToNumbersTransformer();

            do {
                try {
                    String userInput = br.readLine();
                    if(userInput.equals("x")) {
                        continueWork.set(false);
                        return;
                    } else if(inputChecker.isCorrect(userInput)) {
                        Numeral newNumeral = transformer.createNumberFromWords(userInput);

                        synchronized (memory) {
                            memory.add(newNumeral);
                            Collections.sort(memory);
                        }
                    } else {
                        System.out.print("Incorrect input!" + rulesForUser + "\n"); //println() - бликуриующий, print() - нет
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } while (continueWork.get());
        };

        Runnable removeFromMemoryTask = () -> {
            do{
                try {
                    Thread.sleep(5000);
                    synchronized (memory) {
                        if( ! memory.isEmpty()) {
                            Numeral removed = memory.getFirst();
                            System.out.print("Removed: " + removed + "\n");
                            memory.remove();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (continueWork.get());
        };

        Thread userInputReaderThread = new Thread(userInputReadTask);
        Thread removeFromMemoryThread = new Thread(removeFromMemoryTask);
        userInputReaderThread.start();
        removeFromMemoryThread.start();

    }
}
