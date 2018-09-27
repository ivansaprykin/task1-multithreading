package com.ivansaprykin.testtasks.bostongene.multithreading;

class Numeral implements Comparable<Numeral> {

    private final String initialTextDescription;
    private String textDescription;
    private int number;

    public Numeral(int number) {
        this.number = number;
        this.initialTextDescription = "";
    }

    public Numeral(String textDescription, int number) {
        this.number = number;
        this.initialTextDescription = textDescription;
        this.textDescription = textDescription;
    }

    public String getTextDescription() {
        return textDescription;
    }

    public void setTextDescription(String textDescription) {
        this.textDescription = textDescription;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getInitialTextDescription() { return initialTextDescription; }

    public int compareTo(Numeral that) {
        return Integer.compare(this.number, that.number);
    }

    @Override
    public String toString() {
        return '\'' + initialTextDescription + "' (" + number + ')';
    }
}
