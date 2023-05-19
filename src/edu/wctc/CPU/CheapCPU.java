package edu.wctc.CPU;

public class CheapCPU extends CPU {

    public CheapCPU(String name) throws IllegalArgumentException {
        super(name);
    }

    @Override
    public int highRankBet(int currentPot, int minChips) {
        if (getBetChips() > 50) {
            return minChips;
        }

        return smartRaise(minChips, 10);
    }

    @Override
    public int midRankBet(int currentPot, int minChips) {
        if (minChips > 30) {
            return smartFold(minChips);
        }

        if (getBetChips() > 30) {
            return minChips;
        }

        return smartRaise(minChips, 5);
    }

    @Override
    public int lowRankBet(int currentPot, int minChips) {
        int percent = getPercent();

        if (minChips > 10) {
            if (percent <= 50) {
                return smartFold(minChips);
            }
        }

        if (percent <= 70) {
            return minChips;
        } else if (percent <= 85) {
            if (getBetChips() > 10) {
                return minChips;
            }
            return smartRaise(minChips, 5);
        } else {
            return smartFold(minChips);
        }
    }
}
