package edu.wctc.CPU;

import edu.wctc.Cards.Card;
import edu.wctc.HandRanking.CardRanker;
import edu.wctc.Player;
import edu.wctc.UI;

import java.util.List;
import java.util.Random;

/**
 * An abstract class using the Template Method Design Pattern to handle the logic of the cpu players.
 * @author Samuel Hooper
 * @version 1.0
 */
public abstract class CPU extends Player {
    private static final Random rand = new Random();

    public CPU(String name) throws IllegalArgumentException {
        super(name);
    }

    /**
     * @return A random number between 1 - 100 inclusive.
     */
    public int getPercent() {
        return rand.nextInt(1, 101);
    }

    @Override
    public final int takeTurn(UI ui, int currentPot, int minimumChips, List<Card> communityCards) {
        int betChips = 0;

        int minChips = minimumChips - getBetChips();

        if (minChips > getHeldChips()) {
            fold();
        } else {
            CardRanker.rankHand(this, communityCards);
            int rank = getHandRank().ordinal();
            if (rank <= 3) {
                betChips = highRankBet(currentPot, minChips);
            } else if (rank <= 6) {
                betChips = midRankBet(currentPot, minChips);
            } else {
                betChips = lowRankBet(currentPot, minChips);
            }
        }

        ui.outputCPUTurn(this, betChips, minChips);
        betChips(betChips);
        return betChips;
    }

    /**
     * Contains the logic for how the cpu will bet given a high rank hand.
     * @param currentPot The total number of chips in the current pot.
     * @param minChips The minimum number of chips a cpu must bet to stay in the round.
     * @return The amount of chips added to the pot after the cpu's turn has finished.
     */
    public abstract int highRankBet(int currentPot, int minChips);

    /**
     * Contains the logic for how the cpu will bet given a medium rank hand.
     * @param currentPot The total number of chips in the current pot.
     * @param minChips The minimum number of chips a cpu must bet to stay in the round.
     * @return The amount of chips added to the pot after the cpu's turn has finished.
     */
    public abstract int midRankBet(int currentPot, int minChips);

    /**
     * Contains the logic for how the cpu will bet given a low rank hand.
     * @param currentPot The total number of chips in the current pot.
     * @param minChips The minimum number of chips a cpu must bet to stay in the round.
     * @return The amount of chips added to the pot after the cpu's turn has finished.
     */
    public abstract int lowRankBet(int currentPot, int minChips);

    /**
     * Checks to see if the cpu has enough chips to bet by the raise amount, and if it does, it raises by raise amount.
     * @param minChips The minimum number of chips a cpu must bet to stay in the round.
     * @param raise The number of chips the cpu would like to raise the bet by.
     * @return The amount of chips the cpu raised the bet.
     */
    public int smartRaise(int minChips, int raise) {
        if (minChips + raise > getHeldChips()){
            return minChips;
        }
        return minChips + raise;
    }

    /**
     * Checks to see if the cpu needs to bet any chips to stay in the game, and if they do, they fold.
     * @param minChips The minimum number of chips a cpu must bet to stay in the round.
     * @return 0.
     */
    public int smartFold(int minChips) {
        if (minChips > 0) {
            fold();
            return 0;
        }
        return minChips;
    }
}
