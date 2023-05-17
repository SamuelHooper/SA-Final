package edu.wctc;

public class User extends Player {

    public User(String name) throws IllegalArgumentException {
        super(name);
    }

    @Override
    public void takeTurn(UI ui) {
        ui.outputHand(this);
    }
}
