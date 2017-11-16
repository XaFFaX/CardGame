package com.xaffaxhome.cardgame;

import java.util.ArrayList;
import java.util.List;

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

		List<FrenchCard[]> allHands = new ArrayList<FrenchCard[]>();
		allHands.add(highCard);
		allHands.add(onePair_2);
		allHands.add(onePair_3);
		allHands.add(twoPairs);
		allHands.add(threeOfAKind);
		allHands.add(straight);
		allHands.add(straight_5high);
		allHands.add(flush);
		allHands.add(fullHouse);
		allHands.add(fourOfAKind);
		allHands.add(straightFlush);

		for (FrenchCard[] hand : allHands)
			System.out.println(
					"Value: " + new PokerHand(hand).validateHand() + "\n");
		// new PokerHand(onePair).validateHand();
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
