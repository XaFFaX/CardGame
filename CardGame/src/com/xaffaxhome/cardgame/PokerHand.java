package com.xaffaxhome.cardgame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.xaffaxhome.cardgame.Card.Rank;
import com.xaffaxhome.cardgame.Card.Suit;

public class PokerHand extends CardHand
{
	private final List<FrenchCard>		pokerHand		= new ArrayList<>(5);
	private final Map<Integer, String>	valueTextMap	= new HashMap<>();
	// private final List<FrenchCard> highestHand = new ArrayList<>();
	private boolean						validated;
	private int							handValue;
	private static String				handText;

	// must be static due to enum
	private static class PokerHandValidator
	{
		private enum PokerHandValues
		{
			HIGH_CARD(0), ONE_PAIR(1), TWO_PAIR(2), THREE_OF_A_KIND(3), STRAIGHT(4), FLUSH(
					5), FULL_HOUSE(6), FOUR_OF_A_KIND(7), STRAIGHT_FLUSH(8);

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

		private final List<FrenchCard>		pokerHandValidate;
		// private final List<FrenchCard> highestFlush = new ArrayList<>();
		private Integer						pokerHandValue				= new Integer(0);
		private static final Integer		MAIN_HAND_VALUE_COEFFICIENT	= 100_000;
		private static final Integer		MAIN_CARD_VALUE_COEFFICIENT	= 1_000;
		private final Map<Integer, String>	valueTextMap				= new HashMap<>();
		private boolean						is_flush;
		private boolean						is_straight;
		private Card						highCard;

		private boolean checkStraight()
		{
			return (this.checkStraight(null));
		}

		private boolean checkStraight(List<FrenchCard> toValidate)
		{

			List<FrenchCard> tempHand = toValidate == null ? new ArrayList<>(this.pokerHandValidate)
					: new ArrayList<>(toValidate);
			Collections.sort(tempHand);

			// below will remove all duplicate ranks from validated list.
			// since in poker all suits are of the same value,
			// it does not matter which will be removed and which will stay.
			// for "proper" poker card hand this will make
			// no change to existing list,
			// this only matters for "exotic card lists".
			// such "exotic lists" will cause below list
			// to be seemingly random in nature.
			// (we do not know exactly which suits will get removed)

			List<FrenchCard> uniqueCardRanks = tempHand.stream()
					.collect(Collectors.collectingAndThen(
							Collectors.toCollection(
									() -> new TreeSet<>(Comparator.comparing(FrenchCard::getRank))),
							ArrayList::new));
			Integer tempHandValue = new Integer(0);
			Card.Rank prevCardRank = null;
			is_straight = true;
			boolean is_5highStrait = false;
			int howManyinSequence = 0;
			int iterator = 0;

			for (FrenchCard card : uniqueCardRanks)
			{
				if (prevCardRank == null)
				{
					prevCardRank = card.getRank();
					tempHandValue += card.getRank().rank();
					howManyinSequence++;
				} else if (card.getRank().rank() - prevCardRank.rank() != 1)
				{
					if (card.getRank() == Rank.ACE && howManyinSequence == 4 && iterator == 5)
					{
						is_5highStrait = true;
						// we assume that ACEs value in five high straight is 0
						// this is why we do not add its value to handValue.
						// this makes sure it will be the lowest straight
						// possible
						// tempHandValue += card.getRank().rank();
					}
					is_straight = false;
					break;
				} else
				{
					prevCardRank = card.getRank();
					tempHandValue += card.getRank().rank();
					if (++howManyinSequence == 5)
						break;
				}
			}
			if (is_straight)
			{
				highCard = is_5highStrait ? uniqueCardRanks.get(3)
						: Collections.max(uniqueCardRanks);
				// we put this on the map regardless of whether
				// we check for straight or straight flush.
				// it is only straight that matters.
				// this will get filtered out later as needed
				// based on hand value itself.
				valueTextMap.put(
						tempHandValue + MAIN_HAND_VALUE_COEFFICIENT
								* PokerHandValues.STRAIGHT.handValue(),
						"Straight, with a " + highCard + " high card!");
				// this.pokerHandValue = tempHandValue+
				// MAIN_HAND_VALUE_COEFFICIENT*
				// PokerHandValues.STRAIGHT.handValue();
			}
			return is_straight;
		}

		// private boolean checkStraight()
		// {
		// Collections.reverse(this.pokerHandValidate);
		// Integer tempHandValue = new Integer(0);
		// int fiveHighCheck = 2;
		// Card.Rank prevCardRank = null;
		// is_straight = true;
		// is_5highStrait = false;
		//
		// for (FrenchCard card : this.pokerHandValidate)
		// {
		// if (prevCardRank == null)
		// {
		// prevCardRank = card.getRank();
		// tempHandValue += card.getRank().rank();
		// fiveHighCheck++;
		// } else if (card.getRank().rank() - prevCardRank.rank() != -1)
		// {
		// if (fiveHighCheck != 6 || card.getRank() != Rank.ACE)
		// {
		// is_straight = false;
		// tempHandValue += card.getRank().rank();
		// }
		// is_5highStrait = true;
		// // we assume that ACEs value in five high straight is 0
		// // this is why we do not add its value to handValue.
		// // this makes sure it will be the lowest straight possible
		// break;
		// } else
		// {
		// prevCardRank = card.getRank();
		// tempHandValue += card.getRank().rank();
		// fiveHighCheck++;
		// }
		// }
		// if (is_straight)
		// {
		// highCard = is_5highStrait ? pokerHandValidate.get(3)
		// : Collections.max(pokerHandValidate);
		// handText = "Straight, with a " + highCard + " high card!";
		// this.pokerHandValue = tempHandValue
		// + MAIN_HAND_VALUE_COEFFICIENT
		// * PokerHandValues.STRAIGHT.handValue();
		// }
		// return is_straight;
		// }

		private boolean checkFlush()
		{
			List<FrenchCard> tempHand = new ArrayList<>(this.pokerHandValidate);
			Collections.reverse(tempHand);
			Map<Card.Suit, List<FrenchCard>> suitCardMap = new HashMap<>();
			for (Suit suit : Suit.values())
				suitCardMap.put(suit, tempHand.stream().filter(card -> card.getSuit() == suit)
						.collect(Collectors.toList()));

			int bestValue = 0;
			int prevValue = 0;
			int currValue = 0;
			boolean temp_is_straight = false;
			is_flush = false;
			FrenchCard highCard = null;
			for (Map.Entry<Card.Suit, List<FrenchCard>> suitCards : suitCardMap.entrySet())
			{
				if (suitCards.getValue().size() < 5)
					continue;
				is_flush = true;
				currValue = suitCards.getValue().stream().limit(5).mapToInt(r -> r.getRank().rank())
						.sum();
				// at this point we need to check
				// if there are straight flushes
				// in the lists where there are flushes,
				// otherwise this information is lost.
				temp_is_straight = this.checkStraight(suitCards.getValue());
				is_straight = is_straight ? true : temp_is_straight;
				if (currValue > prevValue)
				{
					bestValue = temp_is_straight
							? currValue + PokerHandValues.STRAIGHT_FLUSH.handValue()
									* MAIN_HAND_VALUE_COEFFICIENT
							: currValue + PokerHandValues.FLUSH.handValue()
									* MAIN_HAND_VALUE_COEFFICIENT;
					// this.highestFlush.clear();
					// Collections.copy(this.highestFlush,
					// suitCards.getValue().subList(0, 4));
					highCard = suitCards.getValue().get(0);
				}
				prevValue = currValue;

			}
			String handText = is_straight
					? "You have a straight flush with " + highCard.toString() + " kicker."
					: "You have a flush with " + highCard.toString() + " kicker.";
			valueTextMap.put(bestValue, handText);
			return is_flush;
		}

		// private boolean checkFlush()
		// {
		// Collections.reverse(this.pokerHandValidate);
		// Integer tempHandValue = new Integer(0);
		// is_flush = true;
		// int i = 0;
		//
		// for (FrenchCard card : pokerHandValidate)
		// {
		// if (card.getColor() != pokerHandValidate.get(0).getColor())
		// is_flush = false;
		// tempHandValue += card.getRank().rank();
		// }
		// if (is_flush)
		// {
		// highCard = Collections.max(pokerHandValidate);
		// handText = "Flush, with a " + highCard + " card!";
		//
		// this.pokerHandValue = tempHandValue
		// + MAIN_HAND_VALUE_COEFFICIENT
		// * PokerHandValues.FLUSH.handValue();
		// }
		// // return this.pokerHandValidate.stream().allMatch(c -> c
		// // .getColor() == this.pokerHandValidate.get(0).getColor());
		// return is_flush;
		// }

		private boolean checkDuplicates()
		{
			List<FrenchCard> tempHand = new ArrayList<>(this.pokerHandValidate);
			Integer tempHandValue = new Integer(0);
			boolean cardDuplicates = false;
			List<Card.Rank> handRanks = tempHand.stream().map(r -> r.getRank())
					.collect(Collectors.toList());
			// System.out.println("Hand ranks: " + handRanks);

			Map<Long, List<Card.Rank>> rankFreqMap = new HashMap<>();

			// below is very inefficient. you need to iterate over all ranks,
			// and for each rank you need to count how many of each there are
			// in an entire set of cards to validate...
			// ideally you would only iterate over pokerHandValidate once.
			// not sure how to do this in such a way
			// or with a stream though :(.
			for (Card.Rank rank : Card.Rank.values())
			{
				long tempMultiples = tempHand.stream().filter(r -> r.getRank().equals(rank))
						.count();
				// we do maximum of four-of-a-kind
				tempMultiples = tempMultiples > 4 ? 4 : tempMultiples;
				if (rankFreqMap.get(tempMultiples) == null)
					rankFreqMap.put(tempMultiples, new ArrayList<>());
				rankFreqMap.get(tempMultiples).add(rank);
			}

			// we can do this, since this is a max of 4.
			// we use Long in the map to avoid casts in previous lines
			Card.Rank highest = Collections
					.max(rankFreqMap.get(Collections.max(rankFreqMap.keySet())));
			Collections.reverse(tempHand);
			// below will find first, non-highest ranked card.
			// if this returns empty, then we assume no kicker card.
			// since this can only happen for "invalid" poker hands,
			// any assumption is correct.
			Optional<FrenchCard> kicker = tempHand.stream().filter(c -> c.getRank() != highest)
					.findFirst();
			switch (Collections.max(rankFreqMap.keySet()).intValue())
			{
			case 4:
			{
				tempHandValue = MAIN_HAND_VALUE_COEFFICIENT
						* PokerHandValues.FOUR_OF_A_KIND.handValue()
						+ MAIN_CARD_VALUE_COEFFICIENT * highest.rank() + 4 * highest.rank()
						+ (kicker.isPresent() ? kicker.get().getRank().rank() : 0);
				String tempText = "Four of a kind: " + highest.toString()
						+ (kicker.isPresent() ? " with " + kicker.get().getRank() + " kicker!"
								: "");
				valueTextMap.put(tempHandValue, tempText);
				break;
			}
			case 3:
			{
				if (rankFreqMap.containsKey(2L))
				// full house
				{

				}
			}
			}

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
				handText = "Four of a kind: " + quadCards.toString();
				List<Card.Rank> tempRank = new ArrayList<Card.Rank>(quadCards);
				tempHandValue += MAIN_HAND_VALUE_COEFFICIENT
						* PokerHandValues.FOUR_OF_A_KIND.handValue()
						+ MAIN_CARD_VALUE_COEFFICIENT * tempRank.get(0).rank();
				cardDuplicates = true;

			} else if (pairCards.size() == 0 && tripleCards.size() == 1)
			{
				handText = "Three of a kind: " + tripleCards.toString();
				List<Card.Rank> tempRank = new ArrayList<Card.Rank>(tripleCards);
				tempHandValue += MAIN_HAND_VALUE_COEFFICIENT
						* PokerHandValues.THREE_OF_A_KIND.handValue()
						+ MAIN_CARD_VALUE_COEFFICIENT * tempRank.get(0).rank();
				cardDuplicates = true;
			} else if (pairCards.size() == 2)
			{
				handText = "Two pairs: " + pairCards.toString();
				List<Card.Rank> tempRank = new ArrayList<Card.Rank>(pairCards);
				tempHandValue += MAIN_HAND_VALUE_COEFFICIENT * PokerHandValues.TWO_PAIR.handValue()
						+ MAIN_CARD_VALUE_COEFFICIENT
								* (tempRank.get(0).rank() + tempRank.get(1).rank());
				cardDuplicates = true;
			} else if (pairCards.size() == 1 && tripleCards.size() == 1)
			{
				handText = "A full house: " + tripleCards.toString() + " over: "
						+ pairCards.toString();
				List<Card.Rank> tempRank = new ArrayList<Card.Rank>(tripleCards);
				tempHandValue += MAIN_HAND_VALUE_COEFFICIENT
						* PokerHandValues.FULL_HOUSE.handValue()
						+ MAIN_CARD_VALUE_COEFFICIENT * tempRank.get(0).rank();
				cardDuplicates = true;
			} else if (pairCards.size() == 1)
			{
				handText = "A pair: " + pairCards.toString();
				List<Card.Rank> tempRank = new ArrayList<Card.Rank>(pairCards);
				tempHandValue += MAIN_HAND_VALUE_COEFFICIENT * PokerHandValues.ONE_PAIR.handValue()
						+ MAIN_CARD_VALUE_COEFFICIENT * tempRank.get(0).rank();
				cardDuplicates = true;
			} else
			{
				handText = "High card: " + Collections.max(singleCards);
				tempHandValue += PokerHandValues.HIGH_CARD.handValue();
			}

			this.pokerHandValue = tempHandValue;
			return cardDuplicates;
		}

		// private boolean checkDuplicates()
		// {
		// // due to operations on ranks alone, it is hard to determine what is
		// // a high card for duplicates... hence for duplicates this will be
		// // null
		// Integer tempHandValue = new Integer(0);
		// boolean cardDuplicates = false;
		// List<Card.Rank> handRanks = this.pokerHandValidate.stream()
		// .map(r -> r.getRank()).collect(Collectors.toList());
		// // System.out.println("Hand ranks: " + handRanks);
		//
		// Set<Card.Rank> singleCards = new HashSet<>();
		// Set<Card.Rank> pairCards = new HashSet<>();
		// Set<Card.Rank> tripleCards = new HashSet<>();
		// Set<Card.Rank> quadCards = new HashSet<>();
		// for (Rank rank : new HashSet<Rank>(handRanks))
		// {
		// if (Collections.frequency(handRanks, rank) == 1)
		// singleCards.add(rank);
		// else if (Collections.frequency(handRanks, rank) == 2)
		// pairCards.add(rank);
		// else if (Collections.frequency(handRanks, rank) == 3)
		// tripleCards.add(rank);
		// else if (Collections.frequency(handRanks, rank) == 4)
		// quadCards.add(rank);
		//
		// tempHandValue += rank.rank();
		// }
		//
		// if (!quadCards.isEmpty())
		// {
		// handText = "Four of a kind: " + quadCards.toString();
		// List<Card.Rank> tempRank = new ArrayList<Card.Rank>(quadCards);
		// tempHandValue += MAIN_HAND_VALUE_COEFFICIENT
		// * PokerHandValues.FOUR_OF_A_KIND.handValue()
		// + MAIN_CARD_VALUE_COEFFICIENT * tempRank.get(0).rank();
		// cardDuplicates = true;
		//
		// } else if (pairCards.size() == 0 && tripleCards.size() == 1)
		// {
		// handText = "Three of a kind: " + tripleCards.toString();
		// List<Card.Rank> tempRank = new ArrayList<Card.Rank>(
		// tripleCards);
		// tempHandValue += MAIN_HAND_VALUE_COEFFICIENT
		// * PokerHandValues.THREE_OF_A_KIND.handValue()
		// + MAIN_CARD_VALUE_COEFFICIENT * tempRank.get(0).rank();
		// cardDuplicates = true;
		// } else if (pairCards.size() == 2)
		// {
		// handText = "Two pairs: " + pairCards.toString();
		// List<Card.Rank> tempRank = new ArrayList<Card.Rank>(pairCards);
		// tempHandValue += MAIN_HAND_VALUE_COEFFICIENT
		// * PokerHandValues.TWO_PAIR.handValue()
		// + MAIN_CARD_VALUE_COEFFICIENT * (tempRank.get(0).rank()
		// + tempRank.get(1).rank());
		// cardDuplicates = true;
		// } else if (pairCards.size() == 1 && tripleCards.size() == 1)
		// {
		// handText = "A full house: " + tripleCards.toString() + " over: "
		// + pairCards.toString();
		// List<Card.Rank> tempRank = new ArrayList<Card.Rank>(
		// tripleCards);
		// tempHandValue += MAIN_HAND_VALUE_COEFFICIENT
		// * PokerHandValues.FULL_HOUSE.handValue()
		// + MAIN_CARD_VALUE_COEFFICIENT * tempRank.get(0).rank();
		// cardDuplicates = true;
		// } else if (pairCards.size() == 1)
		// {
		// handText = "A pair: " + pairCards.toString();
		// List<Card.Rank> tempRank = new ArrayList<Card.Rank>(pairCards);
		// tempHandValue += MAIN_HAND_VALUE_COEFFICIENT
		// * PokerHandValues.ONE_PAIR.handValue()
		// + MAIN_CARD_VALUE_COEFFICIENT * tempRank.get(0).rank();
		// cardDuplicates = true;
		// } else
		// {
		// handText = "High card: " + Collections.max(singleCards);
		// tempHandValue += PokerHandValues.HIGH_CARD.handValue();
		// }
		//
		// this.pokerHandValue = tempHandValue;
		// return cardDuplicates;
		// }

		public Integer validateHand()
		{
			if (this.pokerHandValidate == null || this.pokerHandValidate.size() != 5)
				throw new IllegalStateException("You can only validate exactly 5 card hand!");
			System.out.println("Straight: " + (is_straight = this.checkStraight()));
			System.out.println("Flush: " + (is_flush = this.checkFlush()));

			if (is_straight && is_flush)
			{
				int tempValue = this.pokerHandValue;
				// below is to remove current calculated main value
				// but not to remove each card's value
				// if current hand was calculated as either straight
				// or flush - for sure its value is >
				// MAIN_HAND_VALUE_COEFFICIENT
				// probably suboptimal, but works and those are simple
				// calculations
				do
					tempValue -= MAIN_HAND_VALUE_COEFFICIENT;
				while (tempValue > MAIN_HAND_VALUE_COEFFICIENT);
				handText = "Straight flush with a " + ((FrenchCard) highCard).getRank().rank()
						+ " card!!!";
				this.pokerHandValue = tempValue
						+ MAIN_HAND_VALUE_COEFFICIENT * PokerHandValues.STRAIGHT_FLUSH.handValue();
			}

			if (!is_straight && !is_flush)
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
			throw new IllegalStateException("You cannot draw from an empty or non-existant deck!");

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

			pokerHand.addAll(deck.drawCard(deck.cardNumber() - numberCards - 1, numberCards));
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
	protected void replaceCards(final CardDeck deck, Card... toReplace)
	{
		if (deck == null || toReplace == null || deck.cardNumber() == 0 || toReplace.length == 0)
			throw new IllegalStateException("None of the paremeters can be null or empty!");
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
		System.out.println("Hand ranks: " + new ArrayList<>(pokerHand).stream()
				.map(r -> r.getRank()).collect(Collectors.toList()));

		PokerHandValidator validator = new PokerHandValidator(pokerHand);
		this.handValue = validator.validateHand();
		// this.handText = validator.handText;
		this.validated = true;
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
			throw new IllegalStateException("Poker hand must be exactly 5 cards!");
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
		if (!this.validated)
			this.validateHand();
		return handValue;
	}

	@Override
	public int compareTo(CardHand o)
	{
		if (!(o instanceof PokerHand))
			throw new ClassCastException("You can only compare PokerHand classes!");
		if (!this.validated)
			this.validateHand();
		if (!((PokerHand) o).isValidated())
			((PokerHand) o).validateHand();
		return this.handValue - ((PokerHand) o).handValue;
	}

	@Override
	public String toString()
	{
		return this.handText;
	}
}
