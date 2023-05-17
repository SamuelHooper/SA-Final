package edu.wctc.IO;

public interface IOStrategy {
    char inputChar();
    String inputString();
    int inputInt();
    void outputLine(String line);
}
