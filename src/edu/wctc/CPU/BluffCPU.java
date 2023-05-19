package edu.wctc.CPU;

public class BluffCPU extends CPU {
    private boolean bluffGame;

    public BluffCPU(String name) throws IllegalArgumentException {
        super(name);
        bluffGame = false;
    }

    @Override
    public void clearHand() {
        bluffGame = false;
        super.clearHand();
    }

    @Override
    public int highRankBet(int currentPot, int minChips) {
        if (bluffGame && getBetChips() < 50) {
            return smartRaise(minChips, 10);
        }

        if (getBetChips() > 50) {
            return minChips;
        }

        return smartRaise(minChips, 5);
    }

    @Override
    public int midRankBet(int currentPot, int minChips) {
        int percent = getPercent();

        if (bluffGame && getBetChips() < 50) {
            return smartRaise(minChips, 5);
        }

        if (percent <= 95) {
            return minChips;
        } else {
            return smartFold(minChips);
        }
    }

    @Override
    public int lowRankBet(int currentPot, int minChips) {
        int percent = getPercent();

        if (bluffGame && getBetChips() < 50) {
            return smartRaise(minChips, 10);
        }

        if (percent <= 80) {
            return minChips;
        } else  if (percent <= 95 && getBetChips() < 50) {
            bluffGame = true;
            return smartRaise(minChips, 5);
        } else {
            return smartFold(minChips);
        }
    }
}
