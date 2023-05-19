package edu.wctc.CPU;

public class SmartCPU extends CPU {

    public SmartCPU(String name) throws IllegalArgumentException {
        super(name);
    }

    @Override
    public int highRankBet(int currentPot, int minChips) {
        if (getHandRank().ordinal() == 0 && getBetChips() < 80) {
            return smartRaise(minChips, 30);
        }

        if (minChips > 20 && getBetChips() < 30) {
            return minChips;
        }

        if (currentPot > 110 && getBetChips() < 40) {
            return smartRaise(minChips, 10);
        }

        return minChips;
    }

    @Override
    public int midRankBet(int currentPot, int minChips) {
        int percent = getPercent();

        if (minChips > 40) {
            smartFold(minChips);
        }

        if (minChips > 20) {
            return minChips;
        }

        if (percent <= 50 && getBetChips() < 30) {
            return smartRaise(minChips, 10);
        }

        if (percent <= 75 && getBetChips() < 30) {
            return smartRaise(minChips, 5);
        }

        return minChips;
    }

    @Override
    public int lowRankBet(int currentPot, int minChips) {
        int percent = getPercent();

        if (minChips > 30) {
            smartFold(minChips);
        }

        if (minChips > 10) {
            return minChips;
        }

        if (percent <= 40 && getBetChips() < 30) {
            return smartRaise(minChips, 5);
        }

        if (percent <= 60 && getBetChips() < 30) {
            return smartFold(minChips);
        }

        return minChips;
    }
}
