package edu.wctc;

import edu.wctc.Cards.Card;
import edu.wctc.Cards.CardDeck;
import edu.wctc.HandRanking.CardRanker;
import edu.wctc.HandRanking.HandRank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static edu.wctc.PlayerType.*;

/**
 * A Singleton class that handles running the game, creating players, and keeping track of cards and bets.
 * @author Samuel Hooper
 * @version 1.0
 */
public class PokerGame {
    private static final int NUM_CARDS_IN_HAND = 2;
    private static final int NUM_ROUNDS = 3;
    private static final int NUM_CPU = 5;
    private static List<String> CPU_NAMES;

    private final UI ui;
    private static PokerGame pokerGame;
    private List<Player> players;
    private List<Card> communityCards;
    private CardDeck deck;
    private int pot;

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
     * Main driver method to display poker menu and run poker game.
     */
    public void playGame() {
        int result;
        do {
            result = ui.getPokerMenuInput();

            if (result == 1) {
                setUpPokerGame();
                do {
                    playRound();
                } while (ui.getPokerRoundInput(players) == 1);
            }
        } while (result == 1 || result == 2);

    }

    /**
     * A method to deal cards and handle betting for a single round. After the round is completed it ranks hands and resets the game for the next round.
     */
    private void playRound() {
        dealCards();

        for (int i = 0; i < NUM_ROUNDS; i++) {
            dealCard();
            ui.outputNewRound(i+1);
            takeBets();
        }

        rankPlayers();
        payoutPot();

        for (Player player : players) {
            if (player.getHeldChips() == 0) {
                ui.removePlayer(player);
                players.remove(player);
            }
            player.clearHand();
        }
        communityCards = new ArrayList<>();
        deck.shuffleDeck();
    }

    /**
     * Creates the players and adds them to the players List.
     */
    private void setUpPokerGame() {
        populateCPUNames();
        pot = 0;
        createUser();
        createCPUs();
    }

    /**
     * Uses CardRanker to compare and rank the hands of all players who have not folded. Then sorts players by the highest ranked hand and output their placement to the ui.
     */
    private void rankPlayers() {
        for (Player player : players) {
            if (player.isFolded()) {
                player.rankHand(HandRank.UNRANKED);
            } else {
                CardRanker.rankHand(player, communityCards);
            }
        }

        Collections.sort(players);
        ui.revealFinalHands(players);
    }

    /**
     * Gets a count of many players have hands that are equal to the highest ranked hand.
     * @return The number of players who share a winning hand.
     */
    private int numberOfTiedHands() {
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
    private void payoutPot() {
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
     * Loops through each player and lets them take a turn until either only one player has not folded, or all players have called.
     */
    private void takeBets() {
        do {
            for (Player player : players) {
                int betChips = 0;
                if (!player.isFolded()) {
                    betChips = player.takeTurn(ui, pot, getMinChips(), communityCards);
                    pot += betChips;
                }
                if (allPlayersCalled() && pot > 0 && betChips > 0) {
                    break;
                }
            }
        } while(!onePlayerLeft() && !allPlayersCalled());
    }

    /**
     * Checks to see if all but one player has folded this round.
     * @return True if only one player hasn't folded. False if two or more players have not folded.
     */
    private boolean onePlayerLeft() {
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
    private boolean allPlayersCalled() {
        for (Player player : players) {
            if (player.getBetChips() != getMinChips() && !player.isFolded()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Deals all cards for the start of each round of gameplay.
     */
    private void dealCards() {
        dealPlayers();

        dealCard();
        dealCard();
    }

    /**
     * Deals one card from the deck to communityCards.
     */
    private void dealCard() {
        communityCards.add(deck.dealCard());
    }

    /**
     * Deals NUM_CARDS_IN_HAND number of cards from the deck to each player's hand.
     */
    private void dealPlayers() {
        for (Player player : players) {
            for (int i = 0; i < NUM_CARDS_IN_HAND; i++) {
                player.addCardToHand(deck.dealCard());
            }
        }
    }

    /**
     * Creates an instance of the user, adds it to the players List, and gets their name from the ui.
     */
    private void createUser() {
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
     * Creates the CPUs with random names and adds them to the players List.
     */
    private void createCPUs() {
         for (int i = 0; i < NUM_CPU; i++) {
            players.add(PlayerFactory.getPlayer(getRandomCPU(), getRandomName()));
        }
    }

    /**
     * Uses Random.nextInt to get a random CPU name from CPU_NAMES.
     * @return A String name that is removed from the list of names and then returned.
     */
    private String getRandomName() {
        Random rand = new Random();
        String name = CPU_NAMES.get(rand.nextInt(CPU_NAMES.size()));
        CPU_NAMES.remove(name);
        return name;
    }

    /**
     * Uses Random.nextInt to get a random CPU from PlayerTypes.
     * @return A PlayerType that is not a User.
     */
    private PlayerType getRandomCPU() {
        List<PlayerType> cpuTypes = new ArrayList<>(List.of(values()));
        Random rand = new Random();
        while (true) {
            PlayerType type = cpuTypes.get(rand.nextInt(cpuTypes.size()));
            if (type != USER) {
                return type;
            }
        }
    }

    /**
     * Gets the minimum amount of chips needed in order to stay in the game.
     */
    private int getMinChips() {
        int minChips = 0;
        for (Player player : players) {
            if (player.getBetChips() > minChips) {
                minChips = player.getBetChips();
            }
        }
        return minChips;
    }

    /**
     * Instantiates and adds names to CPU_NAMES.
     */
    private void populateCPUNames() {
        CPU_NAMES = new ArrayList<>();
        CPU_NAMES.add("cpu Dave");
        CPU_NAMES.add("cpu Jeff");
        CPU_NAMES.add("cpu Tina");
        CPU_NAMES.add("cpu Patrick");
        CPU_NAMES.add("cpu Marcus");
        CPU_NAMES.add("cpu Anna");
        CPU_NAMES.add("cpu Kyle");
        CPU_NAMES.add("cpu Bridget");
        CPU_NAMES.add("cpu Larry");
    }
}
