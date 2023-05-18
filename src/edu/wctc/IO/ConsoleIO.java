package edu.wctc.IO;

import java.util.Scanner;

/**
 * A Strategy Pattern class to handle Console Input/Output using the IOStrategy Interface.
 * @author Samuel
 * @version 1.0
 */
public class ConsoleIO implements IOStrategy {
    private final Scanner keyboard = new Scanner(System.in);

    /**
     * Gets an uppercase char of the character at position 0 in the string the user inputs.
     * @return An uppercase char entered by the user.
     */
    @Override
    public char inputChar() {
        return inputString().toUpperCase().charAt(0);
    }

    /**
     * Gets input from the user and returns it.
     * @return The string entered by the user.
     */
    @Override
    public String inputString() {
        return keyboard.nextLine();
    }

    /**
     * Uses Integer.parseInt() to parse a string entered by the user into an int.
     * @return An int entered by the user.
     * @throws NumberFormatException If the user enters a string that cannot be converted to an int.
     */
    @Override
    public int inputInt() throws NumberFormatException {
        return Integer.parseInt(inputString());
    }

    /**
     * Prints out a line to the console.
     * @param line The line to print out to the console.
     */
    @Override
    public void outputLine(String line) {
        System.out.println(line);
    }
}
