package com.xaffaxhome.cardgame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import com.xaffaxhome.cardgame.Card.Rank;
import com.xaffaxhome.cardgame.Card.Suit;

public class TestCardValidate
{

	@Test
	public void test()
	{
		FrenchCard highCard[] = { new FrenchCard(Rank.TWO, Suit.CLUBS),
				new FrenchCard(Rank.FOUR, Suit.DIAMONDS),
				new FrenchCard(Rank.EIGHT, Suit.CLUBS),
				new FrenchCard(Rank.KING, Suit.CLUBS),
				new FrenchCard(Rank.ACE, Suit.HEARTS), };
		FrenchCard highCard_max[] = { new FrenchCard(Rank.NINE, Suit.CLUBS),
				new FrenchCard(Rank.JACK, Suit.DIAMONDS),
				new FrenchCard(Rank.QUEEN, Suit.CLUBS),
				new FrenchCard(Rank.KING, Suit.CLUBS),
				new FrenchCard(Rank.ACE, Suit.HEARTS), };
		FrenchCard onePair_2[] = { new FrenchCard(Rank.TWO, Suit.CLUBS),
				new FrenchCard(Rank.TWO, Suit.DIAMONDS),
				new FrenchCard(Rank.QUEEN, Suit.CLUBS),
				new FrenchCard(Rank.KING, Suit.CLUBS),
				new FrenchCard(Rank.ACE, Suit.HEARTS), };
		FrenchCard onePair_3[] = { new FrenchCard(Rank.THREE, Suit.CLUBS),
				new FrenchCard(Rank.THREE, Suit.DIAMONDS),
				new FrenchCard(Rank.FOUR, Suit.CLUBS),
				new FrenchCard(Rank.FIVE, Suit.CLUBS),
				new FrenchCard(Rank.SEVEN, Suit.HEARTS), };
		FrenchCard twoPairs[] = { new FrenchCard(Rank.TWO, Suit.CLUBS),
				new FrenchCard(Rank.TWO, Suit.DIAMONDS),
				new FrenchCard(Rank.THREE, Suit.CLUBS),
				new FrenchCard(Rank.THREE, Suit.HEARTS),
				new FrenchCard(Rank.JACK, Suit.CLUBS) };
		FrenchCard threeOfAKind[] = { new FrenchCard(Rank.JACK, Suit.CLUBS),
				new FrenchCard(Rank.JACK, Suit.HEARTS),
				new FrenchCard(Rank.JACK, Suit.SPADES),
				new FrenchCard(Rank.THREE, Suit.CLUBS),
				new FrenchCard(Rank.KING, Suit.CLUBS) };
		FrenchCard straight[] = { new FrenchCard(Rank.THREE, Suit.HEARTS),
				new FrenchCard(Rank.FOUR, Suit.CLUBS),
				new FrenchCard(Rank.FIVE, Suit.CLUBS),
				new FrenchCard(Rank.SIX, Suit.CLUBS),
				new FrenchCard(Rank.SEVEN, Suit.SPADES) };
		FrenchCard straight_5high[] = { new FrenchCard(Rank.TWO, Suit.SPADES),
				new FrenchCard(Rank.THREE, Suit.HEARTS),
				new FrenchCard(Rank.FOUR, Suit.CLUBS),
				new FrenchCard(Rank.FIVE, Suit.CLUBS),
				new FrenchCard(Rank.ACE, Suit.CLUBS), };
		FrenchCard flush[] = { new FrenchCard(Rank.TWO, Suit.CLUBS),
				new FrenchCard(Rank.EIGHT, Suit.CLUBS),
				new FrenchCard(Rank.JACK, Suit.CLUBS),
				new FrenchCard(Rank.QUEEN, Suit.CLUBS),
				new FrenchCard(Rank.ACE, Suit.CLUBS) };
		FrenchCard fullHouse[] = { new FrenchCard(Rank.JACK, Suit.HEARTS),
				new FrenchCard(Rank.JACK, Suit.SPADES),
				new FrenchCard(Rank.ACE, Suit.CLUBS),
				new FrenchCard(Rank.ACE, Suit.HEARTS),
				new FrenchCard(Rank.ACE, Suit.DIAMONDS) };
		FrenchCard fourOfAKind[] = { new FrenchCard(Rank.JACK, Suit.HEARTS),
				new FrenchCard(Rank.JACK, Suit.CLUBS),
				new FrenchCard(Rank.JACK, Suit.DIAMONDS),
				new FrenchCard(Rank.JACK, Suit.SPADES),
				new FrenchCard(Rank.KING, Suit.HEARTS) };
		FrenchCard straightFlush[] = { new FrenchCard(Rank.THREE, Suit.CLUBS),
				new FrenchCard(Rank.FOUR, Suit.CLUBS),
				new FrenchCard(Rank.FIVE, Suit.CLUBS),
				new FrenchCard(Rank.SIX, Suit.CLUBS),
				new FrenchCard(Rank.SEVEN, Suit.CLUBS) };

		List<PokerHand> allHands = new ArrayList<PokerHand>();
		allHands.add(new PokerHand(highCard));
		allHands.add(new PokerHand(highCard_max));
		allHands.add(new PokerHand(onePair_2));
		allHands.add(new PokerHand(onePair_3));
		allHands.add(new PokerHand(twoPairs));
		allHands.add(new PokerHand(threeOfAKind));
		allHands.add(new PokerHand(straight));
		allHands.add(new PokerHand(straight_5high));
		allHands.add(new PokerHand(flush));
		allHands.add(new PokerHand(fullHouse));
		allHands.add(new PokerHand(fourOfAKind));
		allHands.add(new PokerHand(straightFlush));

		for (PokerHand hand : allHands)
			System.out.println("Value: " + hand.validateHand() + "\n");

		for (int i = 0; i < 100; i++)
		{
			int hand1 = new Random().nextInt(allHands.size());
			int hand2 = new Random().nextInt(allHands.size());
			if (allHands.get(hand2).compareTo(allHands.get(hand1)) > 0)
				System.out.println("Hand: " + allHands.get(hand2).toString()
						+ " with value: " + allHands.get(hand2).getHandValue()
						+ " is better than hand: " + allHands.get(hand1)
						+ " with value: " + allHands.get(hand1).getHandValue());
			else if (allHands.get(hand2).compareTo(allHands.get(hand1)) < 0)
				System.out.println("Hand: " + allHands.get(hand1).toString()
						+ " with value: " + allHands.get(hand1).getHandValue()
						+ " is better than hand: "
						+ allHands.get(hand2).toString() + " with value: "
						+ allHands.get(hand2).getHandValue());
			else
				System.out.println("Hand: " + allHands.get(hand1).toString()
						+ " is tied to hand: "
						+ allHands.get(hand2).toString());
		}

		// System.out.println(""
		// + new PokerHand(onePair_2).compareTo(new PokerHand(onePair_3)));
		// System.out.println(""
		// + new PokerHand(onePair_3).compareTo(new PokerHand(onePair_2)));
		// System.out.println();
		// new PokerHand(twoPairs).validateHand();
		// System.out.println();
		// new PokerHand(threeOfAKind).validateHand();
		// System.out.println();
		// new PokerHand(straight).validateHand();
		// System.out.println();
		// new PokerHand(flush).validateHand();
		// System.out.println();
		// new PokerHand(fullHouse).validateHand();
		// System.out.println();
		// new PokerHand(fourOfAKind).validateHand();
		// System.out.println();
		// new PokerHand(straightFlush).validateHand();
	}

}
