package com.xaffaxhome.cardgame;

public abstract class CardHand implements Comparable<CardHand>
{
	protected abstract void drawTop(final CardDeck deck, int numberCards);

	protected abstract void drawBottom(final CardDeck deck, int numberCards);

	protected abstract void drawRandom(final CardDeck deck, int numberCards);

	protected abstract void replaceCards(final CardDeck deck,
			final Card... toReplace);
}
