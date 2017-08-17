package com.xaffaxhome.cardgame;

import java.util.ArrayList;
import java.util.List;

public class CardDeck
{
	public static final List<FrenchCard> STANDARD52CARDDECK = gen52CardDeck();

	private static List<FrenchCard> gen52CardDeck()
	{
		List<FrenchCard> tempDeck = new ArrayList<FrenchCard>();

		for (FrenchCard.Rank rank : Card.Rank.values())
			for (FrenchCard.Suit suit : Card.Suit.values())
				tempDeck.add(new FrenchCard(rank, suit));
		return new ArrayList<FrenchCard>(tempDeck);
	}

	private CardDeck()
	{

	}
}