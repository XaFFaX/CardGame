package com.xaffaxhome.cardgame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Hand
{
	private static class PokerHand
	{
		private boolean checkStraight(final List<Card> hand)
		{
			List<Card> tempHand=new ArrayList<Card>(hand);
			Collections.sort(tempHand);
			
			Card.Rank prevCardRank = null;
			for (Card card : tempHand)
			{
				if(prevCardRank==null) prevCardRank=card.getRank();
				else if(card.)
			}
			return false;
		}

		private boolean checkFlush(final List<Card> hand)
		{
			return false;
		}

		private boolean checkDuplicates(final List<Card> hand)
		{
			return false;
		}

		public Map<Integer, String> validateHand(final List<Card> hand)
		{
			if (hand == null || hand.size() != 5)
				throw new IllegalStateException(
						"You can only validate exactly 5 card hand!");
			return null;
		}
	}

	private static void checkParams(final List<Card> deck, int numberCards)
	{
		if (deck == null || deck.isEmpty())
			throw new IllegalStateException("You cannot draw from an deck!");
		if (numberCards <= 0)
			throw new IllegalStateException(
					"You cannot draw zero or less cards!");
		if (numberCards > deck.size())
			throw new IllegalStateException(
					"You cannot draw more cards than in the deck!");
	}

	private static List<Card> draw(final List<Card> deck, int numberCards,
			int type)
	{
		checkParams(deck, numberCards);

		List<Card> hand = new ArrayList<Card>();

		for (int i = 0; i < numberCards; i++)
		{
			switch (type)
			{
				// top
				case 0:
				{
					hand.add(deck.get(0));
					deck.remove(0);
					break;
				}
				// bottom
				case 1:
				{

					hand.add(deck.get(deck.size() - 1));
					deck.remove(deck.size() - 1);
					break;
				}
				// random
				case 2:
				{
					int random = new Random().nextInt(deck.size());
					hand.add(deck.get(random));
					deck.remove(random);
					break;
				}
			}
		}
		return (hand);
	}

	private Hand()
	{

	}

	protected static void repaceCards(final List<Card> hand,
			final List<Card> deck, final Card... toReplace)
	{
		if (hand == null || deck == null || toReplace == null
				|| hand.size() == 0 || deck.size() == 0
				|| toReplace.length == 0)
			throw new IllegalStateException(
					"None of the paremeters can be null or empty!");
		int noCardstoReplace = toReplace.length;

		for (Card card : toReplace)
		{
			if (!hand.contains(card))
				throw new IllegalStateException("Card: " + card.toString()
						+ " is not in the hand and thus cannot be replaced!");
			hand.remove(card);
		}

		List<Card> drawnCards = drawTop(deck, noCardstoReplace);

		hand.addAll(drawnCards);
	}

	protected static List<Card> drawTop(final List<Card> deck, int numberCards)
	{
		return (draw(deck, numberCards, 0));
	}

	protected static List<Card> drawBottom(final List<Card> deck,
			int numberCards)
	{
		return (draw(deck, numberCards, 1));
	}

	protected static List<Card> drawRandom(final List<Card> deck,
			int numberCards)
	{
		return (draw(deck, numberCards, 2));
	}

	protected static void showHand(final List<Card> hand)
	{
		hand.forEach(c -> System.out.println("Hand card: " + c));
	}
}
