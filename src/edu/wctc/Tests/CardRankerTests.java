package edu.wctc.Tests;

import edu.wctc.HandRanking.CardRanker;
import edu.wctc.Cards.Card;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static edu.wctc.Cards.CardFace.*;
import static edu.wctc.Cards.CardSuit.*;
import static org.junit.Assert.*;

public class CardRankerTests {
    private final List<Card> royalFlushHand = new ArrayList<>();
    private final List<Card> straightFlushHand = new ArrayList<>();
    private final List<Card> fourOfAKindHand = new ArrayList<>();
    private final List<Card> fullHouseHand = new ArrayList<>();
    private final List<Card> flushHand = new ArrayList<>();
    private final List<Card> straightHand = new ArrayList<>();
    private final List<Card> threeOfAKindHand = new ArrayList<>();
    private final List<Card> twoPairHand = new ArrayList<>();
    private final List<Card> onePairHand = new ArrayList<>();
    private final List<Card> highCardHand = new ArrayList<>();

    @Before
    public void setUp() {
        royalFlushHand.add(new Card(SPADES, ACE));
        royalFlushHand.add(new Card(SPADES, KING));
        royalFlushHand.add(new Card(SPADES, QUEEN));
        royalFlushHand.add(new Card(SPADES, JACK));
        royalFlushHand.add(new Card(SPADES, TEN));
        straightFlushHand.add(new Card(DIAMONDS, NINE));
        straightFlushHand.add(new Card(DIAMONDS, EIGHT));
        straightFlushHand.add(new Card(DIAMONDS, SEVEN));
        straightFlushHand.add(new Card(DIAMONDS, SIX));
        straightFlushHand.add(new Card(DIAMONDS, FIVE));
        fourOfAKindHand.add(new Card(DIAMONDS, THREE));
        fourOfAKindHand.add(new Card(SPADES, THREE));
        fourOfAKindHand.add(new Card(CLUBS, EIGHT));
        fourOfAKindHand.add(new Card(HEARTS, THREE));
        fourOfAKindHand.add(new Card(CLUBS, THREE));
        fullHouseHand.add(new Card(CLUBS, FOUR));
        fullHouseHand.add(new Card(HEARTS, TEN));
        fullHouseHand.add(new Card(DIAMONDS, TEN));
        fullHouseHand.add(new Card(CLUBS, TEN));
        fullHouseHand.add(new Card(SPADES, FOUR));
        flushHand.add(new Card(HEARTS, SIX));
        flushHand.add(new Card(HEARTS, JACK));
        flushHand.add(new Card(HEARTS, TWO));
        flushHand.add(new Card(HEARTS, KING));
        flushHand.add(new Card(HEARTS, EIGHT));
        straightHand.add(new Card(DIAMONDS, JACK));
        straightHand.add(new Card(DIAMONDS, TEN));
        straightHand.add(new Card(CLUBS, NINE));
        straightHand.add(new Card(HEARTS, EIGHT));
        straightHand.add(new Card(CLUBS, SEVEN));
        threeOfAKindHand.add(new Card(DIAMONDS, KING));
        threeOfAKindHand.add(new Card(DIAMONDS, TEN));
        threeOfAKindHand.add(new Card(HEARTS, TEN));
        threeOfAKindHand.add(new Card(SPADES, FOUR));
        threeOfAKindHand.add(new Card(CLUBS, TEN));
        twoPairHand.add(new Card(DIAMONDS, KING));
        twoPairHand.add(new Card(SPADES, TWO));
        twoPairHand.add(new Card(CLUBS, FOUR));
        twoPairHand.add(new Card(CLUBS, TWO));
        twoPairHand.add(new Card(HEARTS, KING));
        onePairHand.add(new Card(CLUBS, FIVE));
        onePairHand.add(new Card(HEARTS, QUEEN));
        onePairHand.add(new Card(HEARTS, FIVE));
        onePairHand.add(new Card(SPADES, FOUR));
        onePairHand.add(new Card(CLUBS, JACK));
        highCardHand.add(new Card(DIAMONDS, QUEEN));
        highCardHand.add(new Card(CLUBS, NINE));
        highCardHand.add(new Card(HEARTS, JACK));
        highCardHand.add(new Card(SPADES, TEN));
        highCardHand.add(new Card(SPADES, FIVE));
    }

    @Test
    public void royalFlushTests() {
        assertTrue(CardRanker.isRoyalFlush(royalFlushHand));
        assertFalse(CardRanker.isRoyalFlush(straightFlushHand));
        assertFalse(CardRanker.isRoyalFlush(fourOfAKindHand));
        assertFalse(CardRanker.isRoyalFlush(fullHouseHand));
        assertFalse(CardRanker.isRoyalFlush(flushHand));
        assertFalse(CardRanker.isRoyalFlush(straightHand));
        assertFalse(CardRanker.isRoyalFlush(threeOfAKindHand));
        assertFalse(CardRanker.isRoyalFlush(twoPairHand));
        assertFalse(CardRanker.isRoyalFlush(onePairHand));
        assertFalse(CardRanker.isRoyalFlush(highCardHand));
    }

    @Test
    public void straightFlushTests() {
        assertFalse(CardRanker.isStraightFlush(royalFlushHand));
        assertTrue(CardRanker.isStraightFlush(straightFlushHand));
        assertFalse(CardRanker.isStraightFlush(fourOfAKindHand));
        assertFalse(CardRanker.isStraightFlush(fullHouseHand));
        assertFalse(CardRanker.isStraightFlush(flushHand));
        assertFalse(CardRanker.isStraightFlush(straightHand));
        assertFalse(CardRanker.isStraightFlush(threeOfAKindHand));
        assertFalse(CardRanker.isStraightFlush(twoPairHand));
        assertFalse(CardRanker.isStraightFlush(onePairHand));
        assertFalse(CardRanker.isStraightFlush(highCardHand));
    }

    @Test
    public void fourOfAKindTests() {
        assertFalse(CardRanker.isFourOfAKind(royalFlushHand));
        assertFalse(CardRanker.isFourOfAKind(straightFlushHand));
        assertTrue(CardRanker.isFourOfAKind(fourOfAKindHand));
        assertFalse(CardRanker.isFourOfAKind(fullHouseHand));
        assertFalse(CardRanker.isFourOfAKind(flushHand));
        assertFalse(CardRanker.isFourOfAKind(straightHand));
        assertFalse(CardRanker.isFourOfAKind(threeOfAKindHand));
        assertFalse(CardRanker.isFourOfAKind(twoPairHand));
        assertFalse(CardRanker.isFourOfAKind(onePairHand));
        assertFalse(CardRanker.isFourOfAKind(highCardHand));
    }

    @Test
    public void fullHouseTests() {
        assertFalse(CardRanker.isFullHouse(royalFlushHand));
        assertFalse(CardRanker.isFullHouse(straightFlushHand));
        assertFalse(CardRanker.isFullHouse(fourOfAKindHand));
        assertTrue(CardRanker.isFullHouse(fullHouseHand));
        assertFalse(CardRanker.isFullHouse(flushHand));
        assertFalse(CardRanker.isFullHouse(straightHand));
        assertFalse(CardRanker.isFullHouse(threeOfAKindHand));
        assertFalse(CardRanker.isFullHouse(twoPairHand));
        assertFalse(CardRanker.isFullHouse(onePairHand));
        assertFalse(CardRanker.isFullHouse(highCardHand));
    }

    @Test
    public void flushTests() {
        assertFalse(CardRanker.isFlushNotStraight(royalFlushHand));
        assertFalse(CardRanker.isFlushNotStraight(straightFlushHand));
        assertFalse(CardRanker.isFlushNotStraight(fourOfAKindHand));
        assertFalse(CardRanker.isFlushNotStraight(fullHouseHand));
        assertTrue(CardRanker.isFlushNotStraight(flushHand));
        assertFalse(CardRanker.isFlushNotStraight(straightHand));
        assertFalse(CardRanker.isFlushNotStraight(threeOfAKindHand));
        assertFalse(CardRanker.isFlushNotStraight(twoPairHand));
        assertFalse(CardRanker.isFlushNotStraight(onePairHand));
        assertFalse(CardRanker.isFlushNotStraight(highCardHand));
    }

    @Test
    public void straightTests() {
        assertFalse(CardRanker.isStraightNotFlush(royalFlushHand));
        assertFalse(CardRanker.isStraightNotFlush(straightFlushHand));
        assertFalse(CardRanker.isStraightNotFlush(fourOfAKindHand));
        assertFalse(CardRanker.isStraightNotFlush(fullHouseHand));
        assertFalse(CardRanker.isStraightNotFlush(flushHand));
        assertTrue(CardRanker.isStraightNotFlush(straightHand));
        assertFalse(CardRanker.isStraightNotFlush(threeOfAKindHand));
        assertFalse(CardRanker.isStraightNotFlush(twoPairHand));
        assertFalse(CardRanker.isStraightNotFlush(onePairHand));
        assertFalse(CardRanker.isStraightNotFlush(highCardHand));
    }

    @Test
    public void threeOfAKindTests() {
        assertFalse(CardRanker.isThreeOfAKindOnly(royalFlushHand));
        assertFalse(CardRanker.isThreeOfAKindOnly(straightFlushHand));
        assertFalse(CardRanker.isThreeOfAKindOnly(fourOfAKindHand));
        assertFalse(CardRanker.isThreeOfAKindOnly(fullHouseHand));
        assertFalse(CardRanker.isThreeOfAKindOnly(flushHand));
        assertFalse(CardRanker.isThreeOfAKindOnly(straightHand));
        assertTrue(CardRanker.isThreeOfAKindOnly(threeOfAKindHand));
        assertFalse(CardRanker.isThreeOfAKindOnly(twoPairHand));
        assertFalse(CardRanker.isThreeOfAKindOnly(onePairHand));
        assertFalse(CardRanker.isThreeOfAKindOnly(highCardHand));
    }

    @Test
    public void twoPairTests() {
        assertFalse(CardRanker.isTwoPair(royalFlushHand));
        assertFalse(CardRanker.isTwoPair(straightFlushHand));
        assertFalse(CardRanker.isTwoPair(fourOfAKindHand));
        assertFalse(CardRanker.isTwoPair(fullHouseHand));
        assertFalse(CardRanker.isTwoPair(flushHand));
        assertFalse(CardRanker.isTwoPair(straightHand));
        assertFalse(CardRanker.isTwoPair(threeOfAKindHand));
        assertTrue(CardRanker.isTwoPair(twoPairHand));
        assertFalse(CardRanker.isTwoPair(onePairHand));
        assertFalse(CardRanker.isTwoPair(highCardHand));
    }

    @Test
    public void twoOfAKindTests() {
        assertFalse(CardRanker.isTwoOfAKindOnly(royalFlushHand));
        assertFalse(CardRanker.isTwoOfAKindOnly(straightFlushHand));
        assertFalse(CardRanker.isTwoOfAKindOnly(fourOfAKindHand));
        assertFalse(CardRanker.isTwoOfAKindOnly(fullHouseHand));
        assertFalse(CardRanker.isTwoOfAKindOnly(flushHand));
        assertFalse(CardRanker.isTwoOfAKindOnly(straightHand));
        assertFalse(CardRanker.isTwoOfAKindOnly(threeOfAKindHand));
        assertFalse(CardRanker.isTwoOfAKindOnly(twoPairHand));
        assertTrue(CardRanker.isTwoOfAKindOnly(onePairHand));
        assertFalse(CardRanker.isTwoOfAKindOnly(highCardHand));
    }

    @Test
    public void highCardTests() {
        assertFalse(CardRanker.isHighCard(royalFlushHand));
        assertFalse(CardRanker.isHighCard(straightFlushHand));
        assertFalse(CardRanker.isHighCard(fourOfAKindHand));
        assertFalse(CardRanker.isHighCard(fullHouseHand));
        assertFalse(CardRanker.isHighCard(flushHand));
        assertFalse(CardRanker.isHighCard(straightHand));
        assertFalse(CardRanker.isHighCard(threeOfAKindHand));
        assertFalse(CardRanker.isHighCard(twoPairHand));
        assertFalse(CardRanker.isHighCard(onePairHand));
        assertTrue(CardRanker.isHighCard(highCardHand));
    }
}
