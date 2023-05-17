package edu.wctc;

import edu.wctc.Cards.CardDeck;

import java.util.List;

import static edu.wctc.PlayerType.*;

/**
 * A Singleton class that handles running the game, creating players, and keeping track of cards.
 *  * @authur Samuel Hooper
 *  * @version 1.0
 */
public class PokerGame {
    private static PokerGame pokerGame;
    private List<Player> players;
    private CardDeck deck;
    private UI ui;

    private PokerGame(UI ui) {
        deck = new CardDeck();
        this.ui = ui;
        createUser();
        createCPU();
    }

    public static PokerGame getInstance(UI ui) {
        if (pokerGame == null) {
            pokerGame = new PokerGame(ui);
        }
        return pokerGame;
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
     * Creates the CPUs and adds them to the players List for gameplay.
     */
    public void createCPU() {
        players.add(PlayerFactory.getPlayer(BLUFF_CPU, "CPU 1"));
        players.add(PlayerFactory.getPlayer(CHEAP_CPU, "CPU 2"));
        players.add(PlayerFactory.getPlayer(SMART_CPU, "CPU 3"));
    }
}
