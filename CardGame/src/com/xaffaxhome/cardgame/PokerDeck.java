package com.xaffaxhome.cardgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PokerDeck extends CardDeck
{
	private List<FrenchCard> pokerDeck;

	@Override
	protected List<FrenchCard> drawCard(int number)
	{
		return (this.drawCard(0, number));
	}

	@Override
	protected List<FrenchCard> drawCard(int index, int number)
	{
		if (index < 0 || number < 1)
			throw new IllegalStateException(
					"Index cannot be less than 0 amd number of cards drawn cannot be less than 1!");
		if (index + number > this.pokerDeck.size())
			throw new IllegalStateException(
					"You cannot draw that many cards from the deck at this index!");

		List<FrenchCard> drawnCards = new ArrayList<>(
				this.pokerDeck.subList(index, index + number));
		this.pokerDeck.removeAll(drawnCards);
		return drawnCards;
	}

	@Override
	protected void shuffleDeck()
	{
		Collections.shuffle(this.pokerDeck);
	}

	public PokerDeck()
	{
		pokerDeck = STANDARD52CARDDECK;
		this.shuffleDeck();
	}

	@Override
	protected int cardNumber()
	{
		return this.pokerDeck.size();
	}

	public List<FrenchCard> getPokerDeck()
	{
		return new ArrayList<FrenchCard>(this.pokerDeck);
	}

}
