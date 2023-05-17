package edu.wctc;

import edu.wctc.Cards.Card;
import edu.wctc.IO.IOStrategy;

public class UI {
    private static UI ui;
    private final IOStrategy ioStrategy;

    private UI(IOStrategy ioStrategy) {
        this.ioStrategy = ioStrategy;
    }

    public void outputHand(Player player) {
        StringBuilder hand = new StringBuilder();
        hand.append("Your hand has: ");
        for (Card card : player.getHand()) {
            hand.append("%s | ".formatted(card));
        }
        outputLine(hand.toString());
    }

    public static UI getInstance(IOStrategy ioStrategy) {
        if (ui == null) {
            ui= new UI(ioStrategy);
        }
        return ui;
    }

    public String getPlayerName() {
        outputLine("Enter player name: ");
        return ioStrategy.inputString();
    }

    public void outputLine(String line) {
        ioStrategy.outputLine(line);
    }
}
