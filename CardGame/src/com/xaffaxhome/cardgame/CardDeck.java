package com.xaffaxhome.cardgame;

import java.util.List;

public abstract class CardDeck<T extends Card>
{
	protected List<T> cardDeck;

	protected abstract void generateDeck();

	protected List<T> getCardDeck()
	{
		return cardDeck;
	}

	@Override
	public String toString()
	{
		return "CardDeck [cardDeck=" + cardDeck + "]";
	}
}
