package edu.wctc;

import edu.wctc.IO.*;

public class Main {
    public static void main(String[] args) {
        IOStrategy ioStrategy = new ConsoleIO();
        UI ui = UI.getInstance(ioStrategy);
        PokerGame pokerGame = PokerGame.getInstance(ui);

        pokerGame.playGame();
    }
}