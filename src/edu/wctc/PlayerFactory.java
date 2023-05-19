package edu.wctc;

import edu.wctc.CPU.*;

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
            case BLUFF_CPU: {
                return new BluffCPU(name);
            }
            case CHEAP_CPU: {
                return new CheapCPU(name);
            }
            case SMART_CPU: {
                return new SmartCPU(name);
            }
            default:
                return null;
        }
    }
}
