package edu.wctc;

import edu.wctc.CPU.CPU;
import edu.wctc.Cards.Card;
import edu.wctc.IO.IOStrategy;

import java.util.List;

import static edu.wctc.Cards.CardFace.*;
import static edu.wctc.Cards.CardSuit.*;

/**
 * Class to handle communication between the program and the IOStrategy.
 * @author Samuel
 * @version 1.0
 */
public class UI {
    private static final String DIVIDER = " | ";
    private static final String MENU_PROMPT = "Please select an option:";
    private static final String EXIT_OPTION = "\tPress any other key to exit";

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
     * Prompts the user for a name.
     * @return the name entered by the user.
     */
    public String getPlayerName() {
        final String NAME_PROMPT = "Enter player name: ";

        outputLine(NAME_PROMPT);
        return ioStrategy.inputString();
    }

    /**
     * Prompts the user for which menu option they would like to select and returns the int response.
     * @return 0 if the user wishes to exit the program. 1 if the user wishes start a game or 2 if the user read the tutorial.
     */
    public int getPokerMenuInput() {
        final String TITLE_SCREEN = "\n===== Poker Application =====";
        final String START_GAME_OPTION = "\t1. Start a game";
        final String TUTORIAL_OPTION = "\t2. Learn to play";

        outputLine(TITLE_SCREEN);
        outputLine(MENU_PROMPT);
        outputLine(START_GAME_OPTION);
        outputLine(TUTORIAL_OPTION);
        outputLine(EXIT_OPTION);

        try {
            int answer = ioStrategy.inputInt();
            if (answer == 1) {
                return answer;
            } else if (answer == 2) {
                outputTutorial();
                return answer;
            }
        } catch (Exception ignored) {
        }
        return 0;
    }

    /**
     * Outputs the tutorial and waits for the user to enter a key before moving on.
     */
    public void outputTutorial() {
        String tutorial = "\n===== Poker Application - Tutorial =====" +
                "\n1. Basic terms" +
                "\n\tHand - Two cards dealt to each player that only they may see and use to build a winning hand." +
                "\n\tCommunity Cards - The 3-5 cards that are available for all players to use in combination with their hand to win." +
                "\n\tPot - The total number of chips that have been bet and will go to the winner at the end of the round." +
                "\n\tCheck - To bet 0 and pass the bet to the next player." +
                "\n\tCall - To bet only the minimum number of chips to stay in the game." +
                "\n\tRaise - To bet at least the minimum number of chips to stay in the game, and more. 'Raising' the minimum number of chips needed to stay in the game." +
                "\n\tFold - To get rid of your hand and forfeit your chance of winning the pot." +
                "\n\n2. Basic rules of play" +
                "\n\tAt the start of the game, each player is dealt 2 cards for their hand, and 3 cards are dealt for the Community Cards." +
                "\n\tAfter each round of betting, another card is dealt to the Community Cards, for a total of 5 Community Cards." +
                "\n\tYour goal is to make the best possible hand that consists of your two cards, and any 3 Community Cards.(See 3. Hand Rankings)" +
                "\n\tOn your turn, you may bet any number of chips as long as you have enough chips to bet, and it meets the minimum call." +
                "\n\tIf you fold, you forfeit any chance of winning the current pot, but you won't be forced to bet anymore chips to stay in the round." +
                "\n\tAfter 3 rounds of betting the winning player is revealed and they take everything in the pot." +
                "\n\t(In the event of a tie, the player with a highest card in their hand is the winner. If there is still a tie, the pot is evenly split among all winners)" +
                "\n\n3. Hand Rankings" +
                "\n\tAll poker hands are ranked as follows, with 1. being the best possible hand, and 10. being the worst." +
                "\n\t1. Royal Flush - Ace, King, Queen, Jack, Ten, all in the same suit." +
                "\n\t\t%s | %s | %s | %s | %s".formatted(new Card(DIAMONDS, ACE), new Card(DIAMONDS, KING), new Card(DIAMONDS, QUEEN), new Card(DIAMONDS, JACK), new Card(DIAMONDS, TEN)) +
                "\n\t2. Straight flush - Five cards in a sequence, all in the same suit." +
                "\n\t\t%s | %s | %s | %s | %s".formatted(new Card(CLUBS, EIGHT), new Card(CLUBS, SEVEN), new Card(CLUBS, SIX), new Card(CLUBS, FIVE), new Card(CLUBS, FOUR)) +
                "\n\t3. Four of a kind - All four cards of the same rank." +
                "\n\t\t%s | %s | %s | %s | %s".formatted(new Card(HEARTS, ACE), new Card(DIAMONDS, KING), new Card(SPADES, QUEEN), new Card(CLUBS, JACK), new Card(DIAMONDS, SEVEN)) +
                "\n\t4. Full House - Three of a kind with a pair." +
                "\n\t\t%s | %s | %s | %s | %s".formatted(new Card(HEARTS, TEN), new Card(DIAMONDS, TEN), new Card(SPADES, TEN), new Card(CLUBS, NINE), new Card(DIAMONDS, NINE)) +
                "\n\t5. Flush - Any five cards of the same suit, but not in a sequence." +
                "\n\t\t%s | %s | %s | %s | %s".formatted(new Card(SPADES, FOUR), new Card(SPADES, JACK), new Card(SPADES, EIGHT), new Card(SPADES, TWO), new Card(SPADES, NINE)) +
                "\n\t6. Straight - Five cards in a sequence, but not of the same suit." +
                "\n\t\t%s | %s | %s | %s | %s".formatted(new Card(CLUBS, NINE), new Card(DIAMONDS, EIGHT), new Card(SPADES, SEVEN), new Card(DIAMONDS, SIX), new Card(HEARTS, FIVE)) +
                "\n\t7. Three of a kind - Three cards of the same rank." +
                "\n\t\t%s | %s | %s | %s | %s".formatted(new Card(CLUBS, SEVEN), new Card(DIAMONDS, SEVEN), new Card(SPADES, SEVEN), new Card(CLUBS, KING), new Card(DIAMONDS, THREE)) +
                "\n\t8. Two pair - Two different pairs." +
                "\n\t\t%s | %s | %s | %s | %s".formatted(new Card(CLUBS, FIVE), new Card(SPADES, FIVE), new Card(CLUBS, THREE), new Card(DIAMONDS, THREE), new Card(CLUBS, QUEEN)) +
                "\n\t9. Pair - Two cards of the same rank." +
                "\n\t\t%s | %s | %s | %s | %s".formatted(new Card(HEARTS, ACE), new Card(DIAMONDS, ACE), new Card(CLUBS, EIGHT), new Card(SPADES, FOUR), new Card(HEARTS, SEVEN)) +
                "\n\t10. High Card - When you haven't made any of the hands above, the highest card plays, In the example below, the Jack plays as the highest card." +
                "\n\t\t%s | %s | %s | %s | %s".formatted(new Card(DIAMONDS, THREE), new Card(CLUBS, JACK), new Card(SPADES, EIGHT), new Card(HEARTS, FOUR), new Card(SPADES, TWO)) +
                "\nPress enter to return to main menu";

        outputLine(tutorial);
        ioStrategy.inputString();
    }

    /**
     * Prompts the user if they wish to continue playing the game and returns the int response.
     * @return 1 if the user wishes to play again. 0 if the user wishes to return to the main menu.
     */
    public int getPokerRoundInput(List<Player> players) {
        final String ROUND_END = "\n===== Round complete =====";
        final String PLAYER_STATS_HEADER = "Players:";
        final String PLAY_AGAIN_OPTION = "\t1. Play another round";

        StringBuilder playersInfo = new StringBuilder();
        for (Player player : players) {
            playersInfo.append(player).append(DIVIDER);
        }

        outputLine(ROUND_END);
        outputLine(playersInfo.toString());
        outputLine(PLAYER_STATS_HEADER);
        outputLine(MENU_PROMPT);
        outputLine(PLAY_AGAIN_OPTION);
        outputLine(EXIT_OPTION);

        try {
            int answer = ioStrategy.inputInt();
            if (answer == 1) {
                return answer;
            }
        } catch (Exception ignored) {
        }
        return 0;
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
            communityCardOutput.append(card).append(DIVIDER);
        }
        outputLine(communityCardOutput.toString());
    }

    /**
     * Displays the final hands of all players.
     * @param players All the players whose final hand gets displayed.
     */
    public void revealFinalHands(List<Player> players) {
        StringBuilder playerHands = new StringBuilder();
        for (int i = 0; i < players.size(); i++) {
            playerHands.append("\n").append(i+1).append(". ").append(players.get(i).displayRoundInfo());
        }
        outputLine(playerHands.toString());
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
            hand.append(card).append(DIVIDER);
        }
        outputLine(hand.toString());
    }

    /**
     * Displays the current betting round number.
     * @param roundNumber The round number of the current betting round.
     */
    public void outputNewRound(int roundNumber) {
        final String ROUND_MESSAGE = "\nBetting Round %d\n".formatted(roundNumber);
        outputLine(ROUND_MESSAGE);
    }

    /**
     * Displays the information of the player who lost and has been removed from the game.
     * @param player The player who is removed.
     */
    public void removePlayer(Player player) {
        final String REMOVE_PLAYER_MESSAGE = "\n%s has run out of chips and been removed from the game!\n";

        outputLine(REMOVE_PLAYER_MESSAGE.formatted(player.getName()));
    }

    /**
     * Outputs a line through the IOStrategy.
     * @param line The line to be output.
     */
    public void outputLine(String line) {
        ioStrategy.outputLine(line);
    }

    /**
     * Outputs the turn information of the CPU.
     * @param cpu The CPU whose turn it is.
     * @param betChips The amount of chips the CPU bet.
     * @param minChips The minimum number of chips the player must bet to stay in the round.
     */
    public void outputCPUTurn(CPU cpu, int betChips, int minChips) {
        if (cpu.isFolded()) {
            outputLine(cpu.getName() + " Folded.");
        } else if (betChips == 0) {
            outputLine(cpu.getName() + " Checked.");
        } else if (betChips == minChips) {
            outputLine(cpu.getName() + " Called.");
        } else if (minChips > 0) {
            outputLine(cpu.getName() + " Called and Raised %d chips.".formatted(betChips - minChips));
        } else {
            outputLine(cpu.getName() + " Bet %d chips.".formatted(betChips));
        }
    }

    /**
     * Prompts the user for how many chips they wish to bet, or if they want to fold. Then returns the int of -1 for a fold, or the number of chips they wish to bet.
     * @param heldChips The total number of chips held by the user.
     * @param pot The current number of chips in the pot.
     * @param minBet The minimum number of chips the player must bet to stay in the round.
     * @return The total number of chips the user wishes to bet, or -1 if they wish to fold.
     */
    public int getTurnInput(int heldChips, int pot, int minBet) {
        final String POT_MESSAGE = "Current Pot: ";
        final String MINIMUM_CHIPS_MESSAGE = "Chips required to call: ";
        final String CHIPS_HELD = "Chips held: ";
        final String BET_PROMPT = "Enter number of chips to bet";
        final String BET_TOOLTIP = "(-1 to Fold, 0 to Check)";
        final String BET_ERROR = "Bet must be -1, 0, or a valid number of chips!";
        final String CHECK_ERROR = "Cannot Check if Chips required to call is greater than 0!";

        outputLine(POT_MESSAGE + pot + DIVIDER + MINIMUM_CHIPS_MESSAGE + minBet);
        outputLine(BET_PROMPT + DIVIDER + CHIPS_HELD + heldChips);
        outputLine(BET_TOOLTIP);
        while (true) {
            try {
                int answer = ioStrategy.inputInt();
                if (answer == 0 && minBet > 0) {
                    outputLine(CHECK_ERROR);
                }
                if (answer >= -1 && answer <= heldChips) {
                    if (answer >= minBet || answer == -1) {
                        return answer;
                    }
                } else {
                    outputLine(BET_ERROR);
                }
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
