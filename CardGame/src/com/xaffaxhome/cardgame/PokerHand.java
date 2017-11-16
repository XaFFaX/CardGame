package com.xaffaxhome.cardgame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import com.xaffaxhome.cardgame.Card.Rank;

public class PokerHand extends CardHand
{
	private final List<FrenchCard> pokerHand;
	private boolean validated;
	private int handValue;

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

	{
		pokerHand = new ArrayList<>(5);
		validated = false;
		handValue = 0;
	}

	private static class PokerHandValidator
	{
		protected enum PokerHandValues
		{
			HIGH_CARD(0), ONE_PAIR(1), TWO_PAIR(2), THREE_OF_A_KIND(
					3), STRAIGHT(4), FLUSH(5), FULL_HOUSE(
							6), FOUR_OF_A_KIND(7), STRAIGHT_FLUSH(8);

			private int handValue;

			PokerHandValues(int handValue)
			{
				this.handValue = handValue;
			}

			protected int handValue()
			{
				return handValue;
			}
		}

		private List<FrenchCard> pokerHandValidate;
		private Integer pokerHandValue = new Integer(0);
		private static final Integer MAIN_HAND_VALUE_COEFFICIENT = 100000;
		private String handText;
		private boolean is_flush;
		private boolean is_straight;

		private boolean checkStraight()
		{
			Collections.sort(this.pokerHandValidate);
			Integer tempHandValue = new Integer(0);
			int fiveHighCheck = 2;

			// need to figure this out...
			Card.Rank prevCardRank = null;
			is_straight = true;
			for (FrenchCard card : this.pokerHandValidate)
			{
				if (prevCardRank == null)
				{
					prevCardRank = card.getRank();
					tempHandValue += card.getRank().rank();
					fiveHighCheck++;
				} else if (card.getRank().rank() - prevCardRank.rank() != 1)
				{
					if (fiveHighCheck != 5 || card.getRank() != Rank.ACE)
					{
						is_straight = false;
						tempHandValue += card.getRank().rank();
					}
					// we assume that ACEs value in five high straight is 0
					break;
				} else
				{
					prevCardRank = card.getRank();
					tempHandValue += card.getRank().rank();
					fiveHighCheck++;
				}
			}
			if (is_straight)
			{
				handText = "You have a straight, with a "
						+ Collections.max(pokerHandValidate) + " card!";
				this.pokerHandValue = tempHandValue
						+ MAIN_HAND_VALUE_COEFFICIENT
								* PokerHandValues.STRAIGHT.handValue();
			}
			return is_straight;
		}

		private boolean checkFlush()
		{
			Integer tempHandValue = new Integer(0);

			for (FrenchCard card : pokerHandValidate)
			{
				if (card.getColor() != pokerHandValidate.get(0).getColor())
					return false;
				tempHandValue += card.getRank().rank();
			}
			handText = "You have a flush, with a "
					+ Collections.max(pokerHandValidate) + " card!";
			this.pokerHandValue = tempHandValue + MAIN_HAND_VALUE_COEFFICIENT
					* PokerHandValues.FLUSH.handValue();
			// return this.pokerHandValidate.stream().allMatch(c -> c
			// .getColor() == this.pokerHandValidate.get(0).getColor());
			return true;
		}

		private boolean checkDuplicates()
		{
			Integer tempHandValue = new Integer(0);
			List<Card.Rank> handRanks = this.pokerHandValidate.stream()
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

				tempHandValue += rank.rank();
			}

			if (!quadCards.isEmpty())
			{
				handText = "You have four of a kind: " + quadCards.toString();
				tempHandValue += MAIN_HAND_VALUE_COEFFICIENT
						* PokerHandValues.FOUR_OF_A_KIND.handValue();

			} else if (pairCards.size() == 0 && tripleCards.size() == 1)
			{
				handText = "You have three of a kind: "
						+ tripleCards.toString();
				tempHandValue += MAIN_HAND_VALUE_COEFFICIENT
						* PokerHandValues.THREE_OF_A_KIND.handValue();
			} else if (pairCards.size() == 2)
			{
				handText = "You have two pairs: " + pairCards.toString();
				tempHandValue += MAIN_HAND_VALUE_COEFFICIENT
						* PokerHandValues.TWO_PAIR.handValue();
			} else if (pairCards.size() == 1 && tripleCards.size() == 1)
			{
				handText = "You have a full house: " + tripleCards.toString()
						+ " over: " + pairCards.toString();
				tempHandValue += MAIN_HAND_VALUE_COEFFICIENT
						* PokerHandValues.FULL_HOUSE.handValue();
			} else if (pairCards.size() == 1)
			{
				handText = "You have a pair: " + pairCards.toString();
				tempHandValue += MAIN_HAND_VALUE_COEFFICIENT
						* PokerHandValues.ONE_PAIR.handValue();
			} else
			{
				handText = "You have a high card: "
						+ Collections.max(singleCards);
				tempHandValue += PokerHandValues.HIGH_CARD.handValue();
			}

			this.pokerHandValue = tempHandValue;
			return false;
		}

		public Integer validateHand()
		{
			if (this.pokerHandValidate == null
					|| this.pokerHandValidate.size() != 5)
				throw new IllegalStateException(
						"You can only validate exactly 5 card hand!");
			System.out.println(
					"Straight: " + (is_straight = this.checkStraight()));
			System.out.println("Flush: " + (is_flush = this.checkFlush()));
			if (is_straight && !is_flush)
				handText = "You have a straight with a "
						+ Collections.max(pokerHandValidate).getRank()
						+ " card!";
			else if (!is_straight && is_flush)
				handText = "You have a flush with a "
						+ Collections.max(pokerHandValidate).getRank()
						+ " card!";
			else if (is_straight && is_flush)
			{
				handText = "You have a straight flush with a "
						+ Collections.max(pokerHandValidate).getRank()
						+ " card!!!";
				this.pokerHandValue += 1_000_000_000;
			} else
				this.checkDuplicates();
			System.out.println(handText + "\n");
			return this.pokerHandValue;
		}

		public PokerHandValidator(List<FrenchCard> hand)
		{
			this.pokerHandValidate = new ArrayList<FrenchCard>(hand);
		}
	}

	private void draw(final CardDeck deck, int numberCards, int type)
	{
		// checkParams(deck, numberCards);
		if (deck == null || deck.cardNumber() == 0)
			throw new IllegalStateException(
					"You cannot draw from an empty or non-existant deck!");

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

				pokerHand.addAll(deck.drawCard(
						deck.cardNumber() - numberCards - 1, numberCards));
				break;
			}
			// random
			case 2:
			{
				int random = new Random()
						.nextInt(deck.cardNumber() - numberCards);
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
	protected void replaceCards(final CardDeck deck, Card... toReplace)
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

	protected Integer validateHand()
	{
		PokerHandValidator validator = new PokerHandValidator(pokerHand);
		handValue = validator.validateHand();
		validated = true;
		return (handValue);
	}

	public PokerHand()
	{

	}

	public PokerHand(PokerDeck deck)
	{
		drawTop(deck, 5);
	}

	public PokerHand(FrenchCard... hand)
	{
		if (hand.length != 5)
			throw new IllegalStateException(
					"Poker hand must be exactly 5 cards!");
		pokerHand.addAll(Arrays.asList(hand));
		// pokerHand = Arrays.asList(hand);
	}

	public List<FrenchCard> getPokerHand()
	{
		return new ArrayList<FrenchCard>(pokerHand);
	}

	public boolean isValidated()
	{
		return validated;
	}

	public int getHandValue()
	{
		// Alternatively we could check if validated and validate if not, before
		// returning a value.
		if (!this.validated)
			throw new IllegalStateException(
					"You first need to validate hand to get its value! Call validateHand() before this.");
		return handValue;
	}
}
