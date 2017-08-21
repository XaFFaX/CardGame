package com.xaffaxhome.cardgame;

public abstract class CardHandAbs
{
	protected abstract void drawTop(final CardDeck deck, int numberCards);

	protected abstract void drawBottom(final CardDeck deck, int numberCards);

	protected abstract void drawRandom(final CardDeck deck, int numberCards);

	protected abstract void repaceCards(final CardDeck deck,
			final Card... toReplace);
}
