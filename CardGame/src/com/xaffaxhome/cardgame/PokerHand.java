package com.xaffaxhome.cardgame;

import java.util.List;
import java.util.Random;

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
