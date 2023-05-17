package edu.wctc;

import edu.wctc.Cards.Card;

import java.util.List;

public abstract class Player {
    private final static String NAME_ERROR_MESSAGE = "Name cannot be blank!";

    private List<Card> hand;
    private String name;
    private int numWins;
    private boolean folded;

    public Player(String name) throws IllegalArgumentException {
        setName(name);
        folded = false;
    }

    public abstract void takeTurn(UI ui);

    public int getNumWins() {
        return numWins;
    }

    public void addWin() {
        numWins++;
    }

    public boolean isFolded() {
        return folded;
    }

    public void fold() {
        folded = false;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void addCardToHand(Card card) {
        hand.add(card);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalArgumentException {
        if (name.isEmpty() || name.isBlank()) {
            throw new IllegalArgumentException(NAME_ERROR_MESSAGE);
        }
        this.name = name;
    }

    @Override
    public String toString() {
        return "%s | Wins: %d".formatted(name, numWins);
    }
}
