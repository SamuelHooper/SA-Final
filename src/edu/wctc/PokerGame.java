package edu.wctc;

import edu.wctc.Cards.Card;
import edu.wctc.Cards.CardDeck;
import edu.wctc.HandRanking.CardRanker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static edu.wctc.HandRanking.HandRank.*;
import static edu.wctc.PlayerType.*;

/**
 * A Singleton class that handles running the game, creating players, and keeping track of cards and bets.
 * @author Samuel Hooper
 * @version 1.0
 */
public class PokerGame {
    private static final int NUM_CARDS_IN_HAND = 2;

    private static PokerGame pokerGame;
    private List<Player> players;
    private List<Card> communityCards;
    private CardDeck deck;
    private int pot;
    private UI ui;

    private PokerGame(UI ui) {
        players = new ArrayList<>();
        communityCards = new ArrayList<>();
        deck = new CardDeck();
        this.ui = ui;
    }

    public static PokerGame getInstance(UI ui) {
        if (pokerGame == null) {
            pokerGame = new PokerGame(ui);
        }
        return pokerGame;
    }

    /**
     * Main driver method to handle gameplay and scoring.
     */
    public void playGame() {
        setUpPokerGame();
        playRound();
    }

    /**
     * A method to deal cards and handle betting for a single round. After the round is completed it also ranks hands, empties the pot, clears the players hands, and shuffles the deck.
     */
    public void playRound() {
        dealCards();

        do {
            for (Player player : players) {
                if (!player.isFolded()) {
                    pot += player.takeTurn(ui, pot, getMinChips(), communityCards);
                }
            }
        } while(!onePlayerLeft() && !allPlayersCalled());

        rankPlayers();
        payoutPot();

        for (Player player : players) {
            player.clearHand();
        }
        deck.shuffleDeck();
    }

    /**
     * Creates the players and adds them to the players List.
     */
    public void setUpPokerGame() {
        pot = 0;
        createUser();
        createUser();
        createUser();
        //createCPU();
    }

    /**
     * Uses CardRanker to compare and rank the hands of all players who have not folded. Then sorts players by the highest ranked hand.
     */
    public void rankPlayers() {
        for (Player player : players) {
            if (!player.isFolded()) {
                List<Card> finalHand = new ArrayList<>(communityCards);
                finalHand.addAll(player.getHand());

                ui.revealFinalHand(finalHand, player.getName());

                if (CardRanker.isRoyalFlush(finalHand)) {
                    player.rankHand(ROYAL_FLUSH);
                } else if (CardRanker.isStraightFlush(finalHand)) {
                    player.rankHand(STRAIGHT_FLUSH);
                } else if (CardRanker.isFourOfAKind(finalHand)) {
                    player.rankHand(FOUR_OF_A_KIND);
                } else if (CardRanker.isFullHouse(finalHand)) {
                    player.rankHand(FULL_HOUSE);
                } else if (CardRanker.isFlushNotStraight(finalHand)) {
                    player.rankHand(FLUSH);
                } else if (CardRanker.isStraightNotFlush(finalHand)) {
                    player.rankHand(STRAIGHT);
                } else if (CardRanker.isThreeOfAKindOnly(finalHand)) {
                    player.rankHand(THREE_OF_A_KIND);
                } else if (CardRanker.isTwoPair(finalHand)) {
                    player.rankHand(TWO_PAIR);
                } else if (CardRanker.isTwoOfAKindOnly(finalHand)) {
                    player.rankHand(TWO_OF_A_KIND);
                } else if (CardRanker.isHighCard(finalHand)) {
                    player.rankHand(HIGH_CARD);
                }
            }
        }

        Collections.sort(players);
    }

    /**
     * Gets a count of many players have hands that are equal to the highest ranked hand.
     * @return The number of players who share a winning hand.
     */
    public int numberOfTiedHands() {
        int numOfTies = 0;
        for (int i = 0; i < players.size() - 1; i++) {
            if (players.get(i).getHandRank() != players.get(i+1).getHandRank()) {
                break;
            }
            if (players.get(i).getHighestCard() == players.get(i+1).getHighestCard()) {
                numOfTies++;
            }
        }
        return numOfTies;
    }

    /**
     * Pays out the chips in the pot to the winning player. Splits the pot evenly across all winners in the event of a tie. Also adds a win to each player that won.
     */
    public void payoutPot() {
        int ties = numberOfTiedHands();
        int collectedChips = pot / (ties + 1);
        for (int i = 0; i <= ties; i++) {
            ui.collectChips(players.get(i), collectedChips);
            players.get(i).collectChips(collectedChips);
            players.get(i).addWin();
        }
        pot = 0;
    }

    /**
     * Checks to see if all but one player has folded this round.
     * @return True if only one player hasn't folded. False if two or more players have not folded.
     */
    public boolean onePlayerLeft() {
        int foldedPlayers = 0;
        for (Player player : players) {
            if (player.isFolded()) {
                foldedPlayers++;
            }
        }
        return foldedPlayers == players.size()-1;
    }

    /**
     * Checks to see if every player has bet the same amount of chips.
     * @return True if all players that have not folded have bet an equal number of chips. False if any player has not bet an equal number of chips as the rest of the active players.
     */
    public boolean allPlayersCalled() {
        for (Player player : players) {
            if (player.getBetChips() != getMinChips() && !player.isFolded()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Deals all cards for each round of gameplay.
     */
    public void dealCards() {
        dealPlayers();
        dealFlop();
    }

    /**
     * Deals CARDS_IN_FLOP number of cards from the deck to the flop.
     */
    public void dealFlop() {
        final int CARDS_IN_FLOP = 3;

        for (int i = 0; i < CARDS_IN_FLOP; i++) {
            communityCards.add(deck.dealCard());
        }
    }

    /**
     * Deals NUM_CARDS_IN_HAND number of cards from the deck to each player's hand.
     */
    public void dealPlayers() {
        for (Player player : players) {
            for (int i = 0; i < NUM_CARDS_IN_HAND; i++) {
                player.addCardToHand(deck.dealCard());
            }
        }
    }

    /**
     * Creates an instance of the user, adds it to the players List, and gets their name from the ui.
     */
    public void createUser() {
        while(true) {
            try {
                players.add(PlayerFactory.getPlayer(USER, ui.getPlayerName()));
                break;
            } catch (Exception e) {
                ui.outputLine(e.getMessage());
            }
        }
    }

    /**
     * Creates the CPUs and adds them to the players List.
     */
    public void createCPU() {
        players.add(PlayerFactory.getPlayer(BLUFF_CPU, "CPU 1"));
        players.add(PlayerFactory.getPlayer(CHEAP_CPU, "CPU 2"));
        players.add(PlayerFactory.getPlayer(SMART_CPU, "CPU 3"));
    }

    /**
     * Gets the minimum amount of chips needed in order to stay in the game.
     */
    public int getMinChips() {
        int minChips = 0;
        for (Player player : players) {
            if (player.getBetChips() > minChips) {
                minChips = player.getBetChips();
            }
        }
        return minChips;
    }
}
