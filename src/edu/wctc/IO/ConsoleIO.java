package edu.wctc.IO;

import java.util.Scanner;

public class ConsoleIO implements IOStrategy {
    private final Scanner keyboard = new Scanner(System.in);

    @Override
    public char inputChar() {
        return inputString().toUpperCase().charAt(0);
    }

    @Override
    public String inputString() {
        return keyboard.nextLine();
    }

    @Override
    public int inputInt() throws NumberFormatException {
        return Integer.parseInt(inputString());
    }

    @Override
    public void outputLine(String line) {
        System.out.println(line);
    }
}
