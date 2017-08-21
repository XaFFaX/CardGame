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

public class PokerHand extends CardHandAbs
{
	private List<FrenchCard> pokerHand;

	// private void checkParams(final CardDeck deck, int numberCards)
	// {
	// if (deck == null || deck.isEmpty())
	// throw new IllegalStateException("You cannot draw from an deck!");
	// if (numberCards <= 0)
	// throw new IllegalStateException(
	// "You cannot draw zero or less cards!");
	// if (numberCards > deck.size())
	// throw new IllegalStateException(
	// "You cannot draw more cards than in the deck!");
	// }

	public class PokerHandValidator
	{
		private List<FrenchCard> pokerHandValidate;

		private boolean checkStraight()
		{
			Collections.sort(pokerHandValidate);

			Card.Rank prevCardRank = null;
			for (FrenchCard card : pokerHandValidate)
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

		private boolean checkFlush()
		{
			return pokerHandValidate.stream().allMatch(
					c -> c.getColor() == pokerHandValidate.get(0).getColor());
		}

		private boolean checkDuplicates()
		{
			List<Card.Rank> handRanks = pokerHandValidate.stream()
					.map(r -> r.getRank()).collect(Collectors.toList());
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

		public Map<Integer, String> validateHand()
		{
			if (pokerHandValidate == null || pokerHandValidate.size() != 5)
				throw new IllegalStateException(
						"You can only validate exactly 5 card hand!");
			System.out.println("Straight: " + checkStraight());
			System.out.println("Flush: " + checkFlush());
			checkDuplicates();
			return null;
		}

		public PokerHandValidator(List<FrenchCard> hand)
		{
			pokerHandValidate = new ArrayList<FrenchCard>(hand);
		}
	}

	private void draw(final CardDeck deck, int numberCards, int type)
	{
		// checkParams(deck, numberCards);
		if (deck == null || deck.cardNumber() == 0)
			throw new IllegalStateException(
					"You cannot draw from an empty deck!");

		switch (type)
		{
		// top
		case 0:
		{
			pokerHand.addAll(deck.drawCard(numberCards));
			break;
		}
		// bottom
		case 1:
		{

			pokerHand.addAll(deck.drawCard(deck.cardNumber() - numberCards - 1,
					numberCards));
			break;
		}
		// random
		case 2:
		{
			int random = new Random().nextInt(deck.cardNumber() - numberCards);
			pokerHand.addAll(deck.drawCard(random, numberCards));
			break;
		}
		}
	}

	@Override
	protected void drawTop(final CardDeck deck, int numberCards)
	{
		draw(deck, numberCards, 0);
	}

	@Override
	protected void drawBottom(final CardDeck deck, int numberCards)
	{
		draw(deck, numberCards, 1);
	}

	@Override
	protected void drawRandom(final CardDeck deck, int numberCards)
	{
		draw(deck, numberCards, 2);
	}

	@Override
	protected void repaceCards(final CardDeck deck, Card... toReplace)
	{
		if (deck == null || toReplace == null || deck.cardNumber() == 0
				|| toReplace.length == 0)
			throw new IllegalStateException(
					"None of the paremeters can be null or empty!");
		int noCardstoReplace = toReplace.length;

		for (Card card : toReplace)
		{
			if (!pokerHand.contains(card))
				throw new IllegalStateException("Card: " + card.toString()
						+ " is not in the hand and thus cannot be replaced!");
			pokerHand.remove(card);
		}

		drawTop(deck, noCardstoReplace);
	}

}
