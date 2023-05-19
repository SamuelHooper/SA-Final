package edu.wctc;

import edu.wctc.Cards.Card;

import java.util.List;

/**
 * A concrete version of the player class designed for the user to input the decisions.
 * @author Samuel Hooper
 * @version 1.0
 */
public class User extends Player {

    public User(String name) throws IllegalArgumentException {
        super(name);
    }

    @Override
    public int takeTurn(UI ui, int pot, int minChips, List<Card>communityCards) {
        ui.outputCommunityCards(communityCards);
        ui.outputHand(this);

        int betChips = ui.getTurnInput(getHeldChips(), pot, minChips - getBetChips());
        if (betChips == -1) {
            fold();
            return 0;
        } else {
            betChips(betChips);
            return betChips;
        }
    }
}
