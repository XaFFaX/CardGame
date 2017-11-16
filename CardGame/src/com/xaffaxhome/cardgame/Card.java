package com.xaffaxhome.cardgame;

import java.util.Random;

public abstract class Card
{
	public static final Suit[] REDSUIT = { Suit.DIAMONDS, Suit.HEARTS };
	public static final Suit[] BLACKSUIT = { Suit.CLUBS, Suit.SPADES };

	protected static <E extends Enum<E>> E randomEnum(Class<E> e)
	{
		return e.getEnumConstants()[new Random()
				.nextInt(e.getEnumConstants().length)];
	}

	protected enum Rank
	{
		TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(
				9), TEN(10), JACK(11), QUEEN(12), KING(13), ACE(14);

		private int rank;

		Rank(int rank)
		{
			this.rank = rank;
		}

		protected int rank()
		{
			return rank;
		}

	}

	protected enum Suit
	{
		DIAMONDS("\u2666"), HEARTS("\u2665"), CLUBS("\u2663"), SPADES("\u2660");

		private String suit;

		Suit(String suit)
		{
			this.suit = suit;
		}

		protected String suit()
		{
			return suit;
		}
	}

	protected enum Color
	{
		BLACK, RED;
	}
}
