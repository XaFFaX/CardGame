package com.xaffaxhome.cardgame;

import java.util.ArrayList;
import java.util.List;

public class CardDeck
{
	public static final List<Card> STANDARD52CARDDECK = gen52CardDeck();

	private static List<Card> gen52CardDeck()
	{
		List<Card> tempDeck = new ArrayList<Card>();

		for (FrenchCard.Rank rank : Card.Rank.values())
			for (FrenchCard.Suit suit : Card.Suit.values())
				tempDeck.add(new FrenchCard(rank, suit));
		return new ArrayList<Card>(tempDeck);
	}

	private CardDeck()
	{

	}
}
