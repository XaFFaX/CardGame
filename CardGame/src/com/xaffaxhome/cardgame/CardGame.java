package com.xaffaxhome.cardgame;

public class CardGame
{

	public static void main(String[] args)
	{
		// Stream.generate(FrenchCard::new).limit(10).forEach(bla ->
		// System.out.println("Stream: " + bla));
		// CardDeck.STANDARD52CARDDECK.forEach(c -> System.out.println("Card: "
		// + c));

		PokerDeck deck = new PokerDeck();
		// Collections.shuffle(deck);

		deck.getPokerDeck().forEach(
				c -> System.out.println("Deck card before draw: " + c));

		PokerHand handTop = new PokerHand();
		handTop.drawTop(deck, 5);
		handTop.getPokerHand()
				.forEach(c -> System.out.println("Hand card top: " + c));
		deck.getPokerDeck().forEach(
				c -> System.out.println("Deck card after top draw: " + c));

		PokerHand handBot = new PokerHand();
		handBot.drawBottom(deck, 5);
		handBot.getPokerHand()
				.forEach(c -> System.out.println("Hand card bottom: " + c));
		deck.getPokerDeck().forEach(
				c -> System.out.println("Deck card after bott draw: " + c));

		PokerHand handRand = new PokerHand();
		handRand.drawBottom(deck, 5);
		handRand.getPokerHand()
				.forEach(c -> System.out.println("Hand card random: " + c));
		deck.getPokerDeck().forEach(
				c -> System.out.println("Deck card after rand draw: " + c));

		handRand.replaceCards(deck, handRand.getPokerHand().get(0),
				handRand.getPokerHand().get(1));
		handRand.getPokerHand().forEach(c -> System.out
				.println("Hand card random after replace: " + c));
		deck.getPokerDeck().forEach(c -> System.out
				.println("Deck card after rand draw and replace: " + c));

		// Hand.PokerHand.validateHand(
		// Arrays.asList(new FrenchCard(Rank.TEN, Color.BLACK),
		// new FrenchCard(Rank.ACE, Color.RED),
		// new FrenchCard(Rank.JACK, Color.BLACK),
		// new FrenchCard(Rank.KING, Color.RED),
		// new FrenchCard(Rank.QUEEN, Color.BLACK)));

		System.out.println("Hand top value: " + handTop.validateHand());
		System.out.println("Hand bot value: " + handBot.validateHand());
		System.out.println("Hand rand value: " + handRand.validateHand());
	}

}
