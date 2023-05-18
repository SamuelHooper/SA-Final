package edu.wctc.HandRanking;

public enum HandRank {
    ROYAL_FLUSH("Royal Flush"),
    STRAIGHT_FLUSH("Straight Flush"),
    FOUR_OF_A_KIND("Four of a Kind"),
    FULL_HOUSE("Full House"),
    FLUSH("Flush"),
    STRAIGHT("Straight"),
    THREE_OF_A_KIND("Three of a Kind"),
    TWO_PAIR("Two Pair"),
    TWO_OF_A_KIND("Two of a Kind"),
    HIGH_CARD("High Card"),
    UNRANKED("Unranked");

    final String name;

    HandRank(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
