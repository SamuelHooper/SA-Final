package edu.wctc.Cards;

public enum CardFace {
    ACE("Ace"),
    KING("King"),
    QUEEN("Queen"),
    JACK("Jack"),
    TEN("Ten"),
    NINE("Nine"),
    EIGHT("Eight"),
    SEVEN("Seven"),
    SIX("Six"),
    FIVE("Five"),
    FOUR("Four"),
    THREE("Three"),
    TWO("Two");

    final String name;

    CardFace(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
