package com.xaffaxhome.cardgame;

import java.util.stream.Stream;

public class CardGame
{

	public static void main(String[] args)
	{
		Stream.generate(Card::new).limit(10)
				.forEach(bla -> System.out.println("Stream: " + bla));
		new Standard52CardDeck().generateDeck();
	}

}
