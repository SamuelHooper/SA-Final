package edu.wctc.HandRanking;

public enum HandRank {
    ROYAL_FLUSH("Royal Flush"),
    STRAIGHT_FLUSH("Straight Flush"),
    FOUR_OF_A_KIND("Four of a kind"),
    FULL_HOUSE("Full House"),
    FLUSH("Flush"),
    STRAIGHT("Straight"),
    THREE_OF_A_KIND("Three of a kind"),
    TWO_PAIR("Two pair"),
    TWO_OF_A_KIND("Two of a kind"),
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
