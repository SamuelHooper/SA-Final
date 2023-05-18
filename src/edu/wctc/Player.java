package edu.wctc;

import edu.wctc.Cards.Card;
import edu.wctc.HandRanking.HandRank;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * An abstract class to handle players.
 * @author Samuel Hooper
 * @version 1.0
 */
public abstract class Player implements Comparable<Player> {
    private final static String NAME_ERROR_MESSAGE = "Name cannot be blank!";
    private final static int DEFAULT_CHIPS = 500;

    private List<Card> hand;
    private HandRank handRank;
    private String name;
    private int heldChips;
    private int betChips;
    private int numWins;
    private boolean folded;

    public Player(String name) throws IllegalArgumentException {
        setName(name);
        clearHand();
        heldChips = DEFAULT_CHIPS;
        betChips = 0;
        folded = false;
    }

    /**
     * An abstract method to handle all actions a player takes on their turn.
     * @param ui The UI used by the program to allow the player to prompt for user input.
     * @param currentPot The total number of chips in the current pot.
     * @param minChips The minimum number of chips a player must bet to stay in the round.
     * @param communityCards A list of the cards available to all players.
     * @return The amount of chips added to the pot after the player's turn has finished.
     */
    public abstract int takeTurn(UI ui, int currentPot, int minChips, List<Card> communityCards);

    /**
     * Adds a single win to the player's number of wins.
     */
    public void addWin() {
        numWins++;
    }

    /**
     * @return The number of chips the player has bet this round.
     */
    public int getBetChips() {
        return betChips;
    }

    /**
     * @return The total number of chips in the player's possession.
     */
    public int getHeldChips() {
        return heldChips;
    }

    /**
     * Removes a number of chips from the players heldChips and adds them to the players betChips.
     * @param chips The number of chips the player bet.
     */
    public void betChips(int chips) {
        heldChips -= chips;
        betChips += chips;
    }

    /**
     * Adds the number of chips to the players heldChips.
     * @param chips The number of chips to be added to the players heldChips.
     */
    public void collectChips(int chips) {
        this.heldChips += chips;
    }

    /**
     * @return True if the player has folded this round, otherwise False.
     */
    public boolean isFolded() {
        return folded;
    }

    /**
     * Sets the players folded to true.
     */
    public void fold() {
        folded = true;
    }

    /**
     * @return The current hand of the player.
     */
    public List<Card> getHand() {
        return hand;
    }

    /**
     * Sets hand to an empty ArrayList and sets the hand rank to UNRANKED.
     */
    public void clearHand() {
        hand = new ArrayList<>();
        rankHand(HandRank.UNRANKED);
    }

    /**
     * Adds a single card to the players hand.
     * @param card The card to be added to the players hand.
     */
    public void addCardToHand(Card card) {
        hand.add(card);
    }

    /**
     * Sets the rank of the players hand to the param rank.
     * @param rank The new rank of the players hand.
     */
    public void rankHand(HandRank rank) {
        handRank = rank;
    }

    /**
     * @return The rank of the players current hand.
     */
    public HandRank getHandRank() {
        return handRank;
    }

    /**
     * @return The name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the player to the param name.
     * @param name The name of the player.
     * @throws IllegalArgumentException If the name is null or empty.
     */
    public void setName(String name) throws IllegalArgumentException {
        if (name.isEmpty() || name.isBlank()) {
            throw new IllegalArgumentException(NAME_ERROR_MESSAGE);
        }
        this.name = name;
    }

    /**
     * @return The card with the highest value in the players hand.
     */
    public Card getHighestCard() {
        hand.sort(Comparator.comparing(c -> c.getFace().ordinal()));
        return hand.get(0);
    }

    @Override
    public int compareTo(Player p) {
        if (this.getHandRank().ordinal() > p.getHandRank().ordinal()) {
            return 1;
        } else if (this.getHandRank().ordinal() == p.getHandRank().ordinal()) {
            if (this.getHighestCard().getFace().ordinal() > p.getHighestCard().getFace().ordinal()) {
                return 1;
            } else if (this.getHighestCard().getFace().ordinal() == p.getHighestCard().getFace().ordinal()) {
                return 0;
            }
            return -1;
        }
        return -1;
    }

    @Override
    public String toString() {
        return "%s | Wins: %d".formatted(name, numWins);
    }
}
