package com.xaffaxhome.cardgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import com.xaffaxhome.cardgame.Card.Rank;

public class Hand
{
	public static class PokerHand
	{
		private static boolean checkStraight(final List<FrenchCard> hand)
		{
			Collections.sort(hand);

			Card.Rank prevCardRank = null;
			for (FrenchCard card : hand)
			{
				if (prevCardRank == null)
					prevCardRank = card.getRank();
				else if (card.getRank().rank() - prevCardRank.rank() != 1)
					return false;
				else
					prevCardRank = card.getRank();
			}
			return true;
		}

		private static boolean checkFlush(final List<FrenchCard> hand)
		{
			return hand.stream()
					.allMatch(c -> c.getColor() == hand.get(0).getColor());
		}

		private static boolean checkDuplicates(final List<FrenchCard> hand)
		{
			List<Card.Rank> handRanks = hand.stream().map(r -> r.getRank())
					.collect(Collectors.toList());
			System.out.println("Hand ranks: " + handRanks);

			Set<Card.Rank> singleCards = new HashSet<>();
			Set<Card.Rank> pairCards = new HashSet<>();
			Set<Card.Rank> tripleCards = new HashSet<>();
			Set<Card.Rank> quadCards = new HashSet<>();
			for (Rank rank : new HashSet<Rank>(handRanks))
			{
				if (Collections.frequency(handRanks, rank) == 1)
					singleCards.add(rank);
				else if (Collections.frequency(handRanks, rank) == 2)
					pairCards.add(rank);
				else if (Collections.frequency(handRanks, rank) == 3)
					tripleCards.add(rank);
				else if (Collections.frequency(handRanks, rank) == 4)
					quadCards.add(rank);
			}

			if (!quadCards.isEmpty())
				System.out.println(
						"You have four of a kind: " + quadCards.toString());
			else if (pairCards.size() == 2)
				System.out
						.println("You have two pairs: " + pairCards.toString());
			else if (pairCards.size() == 1 && tripleCards.size() == 1)
				System.out.println(
						"You have a full house: " + tripleCards.toString()
								+ " over: " + pairCards.toString());
			else if (pairCards.size() == 1)
				System.out.println("You have a pair: " + pairCards.toString());
			else
				System.out.println("You have a high card: "
						+ Collections.max(singleCards));

			return false;
		}

		public static Map<Integer, String> validateHand(
				final List<FrenchCard> hand)
		{
			if (hand == null || hand.size() != 5)
				throw new IllegalStateException(
						"You can only validate exactly 5 card hand!");
			System.out.println("Straight: "
					+ checkStraight(new ArrayList<FrenchCard>(hand)));
			System.out.println(
					"Flush: " + checkFlush(new ArrayList<FrenchCard>(hand)));
			checkDuplicates(new ArrayList<FrenchCard>(hand));
			return null;
		}
	}

	private static void checkParams(final List<FrenchCard> deck,
			int numberCards)
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

	private static List<FrenchCard> draw(final List<FrenchCard> deck,
			int numberCards, int type)
	{
		checkParams(deck, numberCards);

		List<FrenchCard> hand = new ArrayList<FrenchCard>();

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

	protected static void repaceCards(final List<FrenchCard> hand,
			final List<FrenchCard> deck, final Card... toReplace)
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

		List<FrenchCard> drawnCards = drawTop(deck, noCardstoReplace);

		hand.addAll(drawnCards);
	}

	protected static List<FrenchCard> drawTop(final List<FrenchCard> deck,
			int numberCards)
	{
		return (draw(deck, numberCards, 0));
	}

	protected static List<FrenchCard> drawBottom(final List<FrenchCard> deck,
			int numberCards)
	{
		return (draw(deck, numberCards, 1));
	}

	protected static List<FrenchCard> drawRandom(final List<FrenchCard> deck,
			int numberCards)
	{
		return (draw(deck, numberCards, 2));
	}

	protected static void showHand(final List<FrenchCard> hand)
	{
		hand.forEach(c -> System.out.println("Hand card: " + c));
	}
}
