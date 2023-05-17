package edu.wctc.Cards;

public class Card {
    private final CardSuit suit;
    private final CardFace face;

    public Card(CardSuit suit, CardFace face) {
        this.suit = suit;
        this.face = face;
    }

    @Override
    public String toString() {
        return "%s of %s".formatted(face, suit);
    }
}
