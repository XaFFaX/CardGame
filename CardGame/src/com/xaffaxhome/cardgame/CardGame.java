package com.xaffaxhome.cardgame;

import java.util.List;

public class CardGame
{

	public static void main(String[] args)
	{
		// Stream.generate(FrenchCard::new).limit(10).forEach(bla ->
		// System.out.println("Stream: " + bla));
		// CardDeck.STANDARD52CARDDECK.forEach(c -> System.out.println("Card: "
		// + c));

		List<Card> deck = CardDeck.STANDARD52CARDDECK;
		// Collections.shuffle(deck);

		deck.forEach(c -> System.out.println("Deck card before draw: " + c));

		List<Card> handTop = Hand.drawTop(deck, 5);
		handTop.forEach(c -> System.out.println("Hand card top: " + c));
		deck.forEach(c -> System.out.println("Deck card after top draw: " + c));

		List<Card> handBot = Hand.drawBottom(deck, 5);
		handBot.forEach(c -> System.out.println("Hand card bottom: " + c));
		deck.forEach(
				c -> System.out.println("Deck card after bott draw: " + c));

		List<Card> handRand = Hand.drawRandom(deck, 5);
		handRand.forEach(c -> System.out.println("Hand card random: " + c));
		deck.forEach(
				c -> System.out.println("Deck card after rand draw: " + c));

		Hand.repaceCards(handRand, deck, handRand.get(0), handRand.get(1));
		handRand.forEach(c -> System.out
				.println("Hand card random after replace: " + c));
		deck.forEach(c -> System.out
				.println("Deck card after rand draw and replace: " + c));
	}

}
