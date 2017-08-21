package com.xaffaxhome.cardgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PokerDeck extends CardDeck
{
	List<FrenchCard> pokerDeck;

	private List<FrenchCard> gen52CardDeck()
	{
		List<FrenchCard> tempDeck = new ArrayList<FrenchCard>();

		for (FrenchCard.Rank rank : Card.Rank.values())
			for (FrenchCard.Suit suit : Card.Suit.values())
				tempDeck.add(new FrenchCard(rank, suit));
		return new ArrayList<FrenchCard>(tempDeck);
	}

	@Override
	protected List<FrenchCard> drawCard(int number)
	{
		return (this.drawCard(0, number));
	}

	@Override
	protected List<FrenchCard> drawCard(int index, int number)
	{
		if (index < 1 || number < 1)
			throw new IllegalStateException(
					"Index or number of cards drawn cannot be less than 1!");
		if (index + number > pokerDeck.size())
			throw new IllegalStateException(
					"You cannot draw that many cards from the deck at this index!");

		List<FrenchCard> drawnCards = pokerDeck.subList(index, index + number);
		pokerDeck.removeAll(drawnCards);
		return drawnCards;
	}

	@Override
	protected void shuffleDeck()
	{
		Collections.shuffle(pokerDeck);
	}

	public PokerDeck()
	{
		pokerDeck = gen52CardDeck();
	}

	@Override
	protected int cardNumber()
	{
		return pokerDeck.size();
	}

}
