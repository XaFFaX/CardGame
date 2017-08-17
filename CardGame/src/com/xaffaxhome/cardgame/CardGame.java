package com.xaffaxhome.cardgame;

import java.util.Arrays;
import java.util.List;

import com.xaffaxhome.cardgame.Card.Color;
import com.xaffaxhome.cardgame.Card.Rank;

public class CardGame
{

	public static void main(String[] args)
	{
		// Stream.generate(FrenchCard::new).limit(10).forEach(bla ->
		// System.out.println("Stream: " + bla));
		// CardDeck.STANDARD52CARDDECK.forEach(c -> System.out.println("Card: "
		// + c));

		List<FrenchCard> deck = CardDeck.STANDARD52CARDDECK;
		// Collections.shuffle(deck);

		deck.forEach(c -> System.out.println("Deck card before draw: " + c));

		List<FrenchCard> handTop = Hand.drawTop(deck, 5);
		handTop.forEach(c -> System.out.println("Hand card top: " + c));
		deck.forEach(c -> System.out.println("Deck card after top draw: " + c));

		List<FrenchCard> handBot = Hand.drawBottom(deck, 5);
		handBot.forEach(c -> System.out.println("Hand card bottom: " + c));
		deck.forEach(
				c -> System.out.println("Deck card after bott draw: " + c));

		List<FrenchCard> handRand = Hand.drawRandom(deck, 5);
		handRand.forEach(c -> System.out.println("Hand card random: " + c));
		deck.forEach(
				c -> System.out.println("Deck card after rand draw: " + c));

		Hand.repaceCards(handRand, deck, handRand.get(0), handRand.get(1));
		handRand.forEach(c -> System.out
				.println("Hand card random after replace: " + c));
		deck.forEach(c -> System.out
				.println("Deck card after rand draw and replace: " + c));

		Hand.PokerHand.validateHand(
				Arrays.asList(new FrenchCard(Rank.ONE, Color.BLACK),
						new FrenchCard(Rank.TWO, Color.BLACK),
						new FrenchCard(Rank.TREE, Color.BLACK),
						new FrenchCard(Rank.FOUR, Color.BLACK),
						new FrenchCard(Rank.SEVEN, Color.BLACK)));
	}

}
