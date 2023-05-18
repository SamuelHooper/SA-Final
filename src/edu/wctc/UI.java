package edu.wctc;

import edu.wctc.Cards.Card;
import edu.wctc.IO.IOStrategy;

import java.util.List;

/**
 * Class to handle communication between the program and the IOStrategy.
 * @author Samuel
 * @version 1.0
 */
public class UI {
    private static UI ui;
    private final IOStrategy ioStrategy;

    private UI(IOStrategy ioStrategy) {
        this.ioStrategy = ioStrategy;
    }

    public static UI getInstance(IOStrategy ioStrategy) {
        if (ui == null) {
            ui= new UI(ioStrategy);
        }
        return ui;
    }

    /**
     * Promts the user for a name.
     * @return the name entered by the user.
     */
    public String getPlayerName() {
        final String NAME_PROMPT = "Enter player name: ";

        outputLine(NAME_PROMPT);
        return ioStrategy.inputString();
    }

    /**
     * Displays the total chips in the current pot.
     * @param currentPot The total number of chips in the current pot.
     */
    public void outputCurrentPot(int currentPot) {
        final String CURRENT_POT_MESSAGE = "Current Pot: ";

        outputLine(CURRENT_POT_MESSAGE + currentPot);
    }

    /**
     * Displays the minimum number of chips a player must bet to stay in the round.
     * @param minimumChips The minimum number of chips a player must bet to stay in the round.
     */
    public void outputMinimumChips(int minimumChips) {
        final String MINIMUM_CHIPS_MESSAGE = "Chips required to call: ";

        outputLine(MINIMUM_CHIPS_MESSAGE + minimumChips);
    }

    /**
     * Displays a list of the cards available to all players.
     * @param communityCards A list of the cards available to all players.
     */
    public void outputCommunityCards(List<Card> communityCards) {
        final String COMMUNITY_CARDS_MESSAGE = "Community Cards: ";

        StringBuilder communityCardOutput = new StringBuilder();
        communityCardOutput.append(COMMUNITY_CARDS_MESSAGE);
        for (Card card : communityCards) {
            communityCardOutput.append("%s | ".formatted(card));
        }
        outputLine(communityCardOutput.toString());
    }

    /**
     * Displays the final hand of a player.
     * @param finalHand The final hand of a single player.
     * @param playerName The name of the player who owns the finalHand.
     */
    public void revealFinalHand(List<Card> finalHand, String playerName) {
        final String FINAL_HAND_MESSAGE = "%s's Final Hand: ";

        StringBuilder finalHandOutput = new StringBuilder();
        finalHandOutput.append(FINAL_HAND_MESSAGE.formatted(playerName));
        for (Card card : finalHand) {
            finalHandOutput.append("%s | ".formatted(card));
        }
        outputLine(finalHandOutput.toString());
    }

    /**
     * Displays all cards in a player's hand.
     * @param player The player whose hand is displayed.
     */
    public void outputHand(Player player) {
        final String HAND_MESSAGE = "%s's hand: ".formatted(player.getName());

        StringBuilder hand = new StringBuilder();
        hand.append(HAND_MESSAGE);
        for (Card card : player.getHand()) {
            hand.append("%s | ".formatted(card));
        }
        outputLine(hand.toString());
    }

    /**
     * Outputs a line through the IOStrategy.
     * @param line The line to be output.
     */
    public void outputLine(String line) {
        ioStrategy.outputLine(line);
    }

    /**
     * Prompts the player for how many chips they wish to bet, or if they want to fold. Then returns the int of -1 for a fold, or the number of chips they wish to bet.
     * @param heldChips The total number of chips held by the player.
     * @param minBet The minimum number of chips the player must bet to stay in the round.
     * @return The total number of chips the player wishes to bet, or -1 if they wish to fold.
     */
    public int getTurnInput(int heldChips, int minBet) {
        final String CHIPS_HELD = "Chips held: ";
        final String BET_PROMPT = "Enter number of chips to bet. ";
        final String BET_TOOLTIP = "(-1 to Fold, 0 to Check)";
        final String BET_ERROR = "Not a valid option! Bet must be -1, 0, or a valid number of chips!";

        outputLine(BET_PROMPT + CHIPS_HELD + heldChips);
        outputLine(BET_TOOLTIP);
        while (true) {
            try {
                int answer = ioStrategy.inputInt();
                if (answer >= -1 && answer <= heldChips) {
                    if (answer >= minBet || answer == -1) {
                        return answer;
                    }
                }
                outputLine(BET_ERROR);
            } catch (Exception e) {
                outputLine(BET_ERROR);
            }
        }
    }

    /**
     * Displays the name of the player who won the round, the rank of their hand, and the amount of chips they won.
     * @param player The player who won the round.
     * @param collectedChips The number of chips the winning player received.
     */
    public void collectChips(Player player, int collectedChips) {
        final String COLLECT_CHIPS_MESSAGE = "%s won with a %s and collected %d Chips!";

        outputLine(COLLECT_CHIPS_MESSAGE.formatted(player.getName(),player.getHandRank(), collectedChips));
    }
}
