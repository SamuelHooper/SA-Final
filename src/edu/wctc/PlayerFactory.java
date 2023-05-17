package edu.wctc;

/**
 * A Factory class to create players.
 */
public class PlayerFactory {
    public static Player getPlayer(PlayerType playerType, String name) {
        switch (playerType) {
            case USER: {
                return new User(name);
            }
            default:
                return null;
        }
    }
}
