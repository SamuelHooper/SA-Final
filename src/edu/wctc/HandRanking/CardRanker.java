package edu.wctc.HandRanking;

import edu.wctc.Cards.Card;
import edu.wctc.Cards.CardFace;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * A utility class consisting of static methods to determine the ranking of a 5 card poker hand.
 * @author Samuel
 * @version 1.0
 */
public class CardRanker {

    /**
     * @param hand a list of cards that make up the poker hand to rank.
     * @param numberOfMatchingFaces the number of cards that need to share the same face.
     * @return The number of times that a face showed up the same amount of times as numberOfMatches.
     */
    private static int getNumberOfFaceMatches(List<Card> hand, int numberOfMatchingFaces) {
        List<Integer> facesCount = countOfEachFaceInHand(hand);
        int countFaceMatches = 0;
        for (Integer faceCount : facesCount) {
            if (faceCount == numberOfMatchingFaces) {
                countFaceMatches++;
            }
        }
        return countFaceMatches;
    }

    /**
     * @param hand a list of cards to search through.
     * @return a list of how many times each card face showed up in hand.
     */
    private static List<Integer> countOfEachFaceInHand(List<Card> hand) {
        List<Integer> faceFrequencies = new ArrayList<>();
        for (CardFace face : CardFace.values()) {
            int numberOfFace = 0;
            for (Card card : hand) {
                numberOfFace += card.getFace() == face ? 1 : 0;
            }
            faceFrequencies.add(numberOfFace);
        }
        return faceFrequencies;
    }

    /**
     * @param hand a list of cards that make up the poker hand to rank.
     * @return True if there is a poker straight. False if there is not a poker straight in hand.
     */
    private static boolean isStraight(List<Card> hand) {
        hand.sort(Comparator.comparing(Card::getFace));
        for (int i = 0; i < hand.size() - 1; i++) {
            if (hand.get(i).getFace().ordinal() != hand.get(i+1).getFace().ordinal() - 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param hand a list of cards that make up the poker hand to rank.
     * @return True if there is a poker flush in hand. False if there is not a poker flush in hand.
     */
    private static boolean isFlush(List<Card> hand) {
        hand.sort(Comparator.comparing(Card::getSuit));
        for (int i = 0; i < hand.size() - 1; i++) {
            if (hand.get(i).getSuit().ordinal() != hand.get(i+1).getSuit().ordinal()) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param hand a list of cards that make up the poker hand to rank.
     * @return True if there is three of a kind in hand. False if hand does not contain three cards of the same face.
     */
    private static boolean isThreeOfAKind(List<Card> hand) {
        return getNumberOfFaceMatches(hand, 3) == 1;
    }

    /**
     * @param hand a list of cards that make up the poker hand to rank.
     * @return True if there is two of a kind in hand. False if hand does not contain two cards of the same face.
     */
    private static boolean isTwoOfAKind(List<Card> hand) {
        return getNumberOfFaceMatches(hand, 2) == 1;
    }

    /**
     * @param hand a list of cards that make up the poker hand to rank.
     * @return True if there is an ace in the given hand. False if hand does not contain an ace.
     */
    private static boolean hasAceInHand(List<Card> hand) {
        for (Card card : hand) {
            if (card.getFace() == CardFace.ACE) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param hand a list of cards that make up the poker hand to rank.
     * @return True if hand contains a poker royal flush. False if hand does not contain a royal flush.
     */
    public static boolean isRoyalFlush(List<Card> hand) {
        return isFlush(hand) && isStraight(hand) && hasAceInHand(hand);
    }

    /**
     * @param hand a list of cards that make up the poker hand to rank.
     * @return True if hand contains only a poker straight flush. False if hand contains a poker royal flush or does not contain a straight flush.
     */
    public static boolean isStraightFlush(List<Card> hand) {
        return isFlush(hand) && isStraight(hand) && !hasAceInHand(hand);
    }

    /**
     * @param hand a list of cards that make up the poker hand to rank.
     * @return True if hand contains four of a kind. False if hand does not contain four cards of the same face.
     */
    public static boolean isFourOfAKind(List<Card> hand) {
        return getNumberOfFaceMatches(hand, 4) == 1;
    }

    /**
     * @param hand a list of cards that make up the poker hand to rank.
     * @return True if hand contains three of a kind and two of a kind. False if hand does not contain three of a kind and two of a kind.
     */
    public static boolean isFullHouse(List<Card> hand) {
        return isThreeOfAKind(hand) && isTwoOfAKind(hand);
    }

    /**
     * @param hand a list of cards that make up the poker hand to rank.
     * @return True if hand contains only a poker flush. False if hand contains no flush, or if hand contains a straight
     */
    public static boolean isFlushNotStraight(List<Card> hand) {
        return isFlush(hand) && !isStraight(hand);
    }

    /**
     * @param hand a list of cards that make up the poker hand to rank.
     * @return True if hand contains only a poker straight. False if hand contains no straight, or if hand contains a flush.
     */
    public static boolean isStraightNotFlush(List<Card> hand) {
        return isStraight(hand) && !isFlush(hand);
    }

    /**
     * @param hand a list of cards that make up the poker hand to rank.
     * @return True if hand contains only three of one card face. False if hand contains a full house or if hand has no three cards of the same face.
     */
    public static boolean isThreeOfAKindOnly(List<Card> hand) {
        return isThreeOfAKind(hand) && !isTwoOfAKind(hand);
    }

    /**
     * @param hand a list of cards that make up the poker hand to rank.
     * @return True if hand contains two distinct pairs of cards. False if hand does not contain exactly two distinct pairs of cards.
     */
    public static boolean isTwoPair(List<Card> hand) {
        return getNumberOfFaceMatches(hand, 2) == 2;
    }

    /**
     * @param hand a list of cards that make up the poker hand to rank.
     * @return True if hand contains only one pair of exactly two cards in hand. False if hand contains more than one pair or no pairs.
     */
    public static boolean isTwoOfAKindOnly(List<Card> hand) {
        return isTwoOfAKind(hand) && !isThreeOfAKind(hand);
    }

    /**
     * @param hand a list of cards that make up the poker hand to rank.
     * @return True if hand contains no ranked poker hand and only a high card. False if any ranked hand can be made.
     */
    public static boolean isHighCard(List<Card> hand) {
        return !isTwoOfAKind(hand) && !isTwoPair(hand) && !isThreeOfAKind(hand) && !isStraight(hand) && !isFlush(hand) && !isFourOfAKind(hand);
    }
}