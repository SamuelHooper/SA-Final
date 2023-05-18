package edu.wctc;

/**
 * A Factory Pattern class to create concrete instances of players.
 * @author Samuel
 * @version 1.0
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
