package edu.wctc.HandRanking;

import edu.wctc.Cards.Card;
import edu.wctc.Cards.CardFace;
import edu.wctc.Player;

import java.util.*;
import java.util.stream.Collectors;

import static edu.wctc.HandRanking.HandRank.*;

/**
 * A utility class consisting of static methods to determine the ranking of a 5 card poker hand.
 * @author Samuel
 * @version 1.0
 */
public class CardRanker {

    /**
     * Ranks a players hand and sets the rankHand field equal to the best rank in a players hand.
     * @param player The player whose hand is ranked.
     * @param communityCards A list of the cards available to all players.
     */
    public static void rankHand(Player player, List<Card> communityCards) {
        HandRank highestRank = UNRANKED;
        List<Card> bestHand = new ArrayList<>();

        List<List<Card>> allHands = allPossibleHands(communityCards);
        for (List<Card> hand : allHands) {
            if (hand.size() == 3) {
                List<Card> finalHand = new ArrayList<>(hand);
                finalHand.addAll(player.getHand());
                findHighestHandRank(player, finalHand);

                if (player.getHandRank().ordinal() < highestRank.ordinal()) {
                    bestHand = finalHand;
                    highestRank = player.getHandRank();
                }
            } else if (hand.size() == 4) {
                for (Card card : player.getHand()) {
                    List<Card> finalHand = new ArrayList<>(hand);
                    finalHand.add(card);
                    findHighestHandRank(player, finalHand);

                    if (player.getHandRank().ordinal() < highestRank.ordinal()) {
                        bestHand = finalHand;
                        highestRank = player.getHandRank();
                    }
                }

            }
        }
        player.setBestHand(bestHand);
        player.rankHand(highestRank);
    }

    /**
     * Gets all unique permutations for a given set of cards.
     * @param cards The set of cards to get all unique permutations for.
     * @return A List of hands that each represent a unique permutation of allCardsInPlay.
     */
    private static List<List<Card>> allPossibleHands(List<Card> cards) {
        List<List<Card>> allHands = new ArrayList<>();
        int lastIdx = cards.size() - 1;

        if (lastIdx >= 0) {
            List<Card> temp = new ArrayList<>();
            temp.add(cards.get(lastIdx));
            allHands.add(temp);

            if (lastIdx > 0) {
                List<Card> smallerHand = cards.stream().limit(lastIdx).collect(Collectors.toList());
                List<List<Card>> subHand = allPossibleHands(smallerHand);
                allHands.addAll(subHand);
                for (List<Card> hand : subHand) {
                    List<Card> tempHand = new ArrayList<>(hand);
                    tempHand.add(cards.get(lastIdx));
                    allHands.add(tempHand);
                }
            }
        }

        return allHands;
    }

    /**
     * Find the highest ranked hand that can be made from the cards in the given players hand.
     * @param player The player whose hand is ranked.
     * @param finalHand A list of 5 cards that make up a players final hand.
     */
    private static void findHighestHandRank(Player player, List<Card> finalHand) {
        if (CardRanker.isRoyalFlush(finalHand)) {
            player.rankHand(ROYAL_FLUSH);
        } else if (CardRanker.isStraightFlush(finalHand)) {
            player.rankHand(STRAIGHT_FLUSH);
        } else if (CardRanker.isFourOfAKind(finalHand)) {
            player.rankHand(FOUR_OF_A_KIND);
        } else if (CardRanker.isFullHouse(finalHand)) {
            player.rankHand(FULL_HOUSE);
        } else if (CardRanker.isFlushNotStraight(finalHand)) {
            player.rankHand(FLUSH);
        } else if (CardRanker.isStraightNotFlush(finalHand)) {
            player.rankHand(STRAIGHT);
        } else if (CardRanker.isThreeOfAKindOnly(finalHand)) {
            player.rankHand(THREE_OF_A_KIND);
        } else if (CardRanker.isTwoPair(finalHand)) {
            player.rankHand(TWO_PAIR);
        } else if (CardRanker.isTwoOfAKindOnly(finalHand)) {
            player.rankHand(TWO_OF_A_KIND);
        } else if (CardRanker.isHighCard(finalHand)) {
            player.rankHand(HIGH_CARD);
        }
    }

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