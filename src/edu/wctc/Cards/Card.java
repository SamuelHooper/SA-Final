package edu.wctc.Cards;

/**
 * This is a class to store the data for a single standard playing card
 * @author Samuel
 * @version 1.0
 */
public class Card {
    private final CardSuit suit;
    private final CardFace face;

    public Card(CardSuit suit, CardFace face) {
        this.suit = suit;
        this.face = face;
    }

    public CardFace getFace() {
        return face;
    }

    public CardSuit getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return "%s of %s".formatted(face, suit);
    }
}
