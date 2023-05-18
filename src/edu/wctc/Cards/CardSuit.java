package edu.wctc.Cards;

public enum CardSuit {
    SPADES("\u001B[30mSpades\u001B[0m"),
    HEARTS("\u001B[31mHearts\u001B[0m"),
    CLUBS("\u001B[32mClubs\u001B[0m"),
    DIAMONDS("\u001B[34mDiamonds\u001B[0m");

    final String name;

    CardSuit(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
