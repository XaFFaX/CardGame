package com.xaffaxhome.cardgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardDeck
{
	public static final List<Card> STANDARD52CARDDECK = gen52CardDeck();

	private static List<Card> gen52CardDeck()
	{
		List<Card> tempDeck = new ArrayList<Card>();

		for (Card.Rank rank : Card.Rank.values())
			for (Card.Suit suit : Card.Suit.values())
				tempDeck.add(new Card(rank, suit));
		return Collections.unmodifiableList(tempDeck);

	}

	private CardDeck()
	{

	}
}
