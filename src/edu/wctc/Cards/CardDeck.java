package edu.wctc.Cards;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A class that stores a list of 52 standard playing cards.
 * @author Samuel
 * @version 1.0
 */
public class CardDeck {
    private final List<Card> allCards;
    private List<Card> deck;

    /**
     * Creates a CardDeck() and populates the cards List and deck List with all cards from CardSuit and CardFace.
     */
    public CardDeck() {
        allCards = new ArrayList<>();
        for (CardSuit suit : CardSuit.values()) {
            for (CardFace face : CardFace.values()) {
                allCards.add(new Card(suit, face));
            }
        }
        shuffleDeck();
    }

    /**
     * Uses Random.nextInt to get a random card from the deck.
     * @return A single card that is removed from the deck and then returned.
     */
    public Card dealCard() {
        Random rand = new Random();
        Card dealtCard = deck.get(rand.nextInt(deck.size()));
        deck.remove(dealtCard);
        return dealtCard;
    }

    /**
     * Creates a new instance of a deck that contains all cards to replace the old deck.
     */
    public void shuffleDeck() {
        deck = new ArrayList<>(allCards);
    }
}
