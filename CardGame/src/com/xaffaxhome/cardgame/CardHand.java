package com.xaffaxhome.cardgame;

import java.util.List;
import java.util.Random;

public class CardHand<T extends Card>
{
	private void checkParams(final List<T> deck, int numberCards)
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

	private void draw(final List<T> deck, int numberCards, int type)
	{
		checkParams(deck, numberCards);

		for (int i = 0; i < numberCards; i++)
		{
			switch (type)
			{
				// top
				case 0:
				{
					this.add(deck.get(0));
					deck.remove(0);
					break;
				}
				// bottom
				case 1:
				{

					this.add(deck.get(deck.size() - 1));
					deck.remove(deck.size() - 1);
					break;
				}
				// random
				case 2:
				{
					int random = new Random().nextInt(deck.size());
					this.add(deck.get(random));
					deck.remove(random);
					break;
				}
			}
		}
	}

	protected void repaceCards(final List<T> deck, final Card... toReplace)
	{
		if (deck == null || toReplace == null || deck.size() == 0
				|| toReplace.length == 0)
			throw new IllegalStateException(
					"None of the paremeters can be null or empty!");
		int noCardstoReplace = toReplace.length;

		for (Card card : toReplace)
		{
			if (!this.contains(card))
				throw new IllegalStateException("Card: " + card.toString()
						+ " is not in the hand and thus cannot be replaced!");
			this.remove(card);
		}

		drawTop(deck, noCardstoReplace);

	}

	protected void drawTop(final List<T> deck, int numberCards)
	{
		draw(deck, numberCards, 0);
	}

	protected void drawBottom(final List<T> deck, int numberCards)
	{
		draw(deck, numberCards, 1);
	}

	protected void drawRandom(final List<T> deck, int numberCards)
	{
		draw(deck, numberCards, 2);
	}

	protected void showHand(final List<T> hand)
	{
		hand.forEach(c -> System.out.println("Hand card: " + c));
	}
}
