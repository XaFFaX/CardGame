package com.xaffaxhome.cardgame;

import java.util.Arrays;
import java.util.Random;

public class Card implements Comparable<Card>
{

	private Rank rank;
	private Suit suit;
	private Color color;
	private static final Suit[] REDSUITE =
	{ Suit.DIAMONDS, Suit.HEARTS };
	private static final Suit[] BLACKSUITE =
	{ Suit.CLUBS, Suit.SPADES };

	private static <E extends Enum<E>> E randomEnum(Class<E> e)
	{
		return e.getEnumConstants()[new Random()
				.nextInt(e.getEnumConstants().length)];
	}

	protected enum Rank
	{
		ACE(0), ONE(1), TWO(2), TREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(
				7), EIGHT(8), NINE(9), TEN(10), JACK(11), QUEEN(12), KING(13);

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

	public Card()
	{
		this(randomEnum(Rank.class), randomEnum(Color.class));
	}

	public Card(Rank rank, Color color)
	{
		if (rank == null)
			this.rank = randomEnum(Rank.class);
		else
			this.rank = rank;

		if (color == null)
		{
			this.suit = randomEnum(Suit.class);
			if (Arrays.asList(REDSUITE).contains(this.suit))
				this.color = Color.RED;
			else
			{
				this.color = color;
				if (this.color == Color.BLACK)
					this.suit = BLACKSUITE[new Random()
							.nextInt(BLACKSUITE.length)];
				else
					this.suit = REDSUITE[new Random().nextInt(REDSUITE.length)];
			}
		} else
		{
			this.color = color;
			if (this.color == Color.BLACK)
				this.suit = BLACKSUITE[new Random().nextInt(BLACKSUITE.length)];
			else
				this.suit = REDSUITE[new Random().nextInt(REDSUITE.length)];
		}

	}

	public Card(Rank rank, Suit suit)
	{
		if (rank == null)
			this.rank = randomEnum(Rank.class);
		else
			this.rank = rank;
		if (suit == null)
			this.suit = randomEnum(Suit.class);
		else
			this.suit = suit;
		if (this.suit == Suit.CLUBS || this.suit == Suit.SPADES)
			this.color = Color.BLACK;
		else
			this.color = Color.RED;
	}

	protected Rank getRank()
	{
		return rank;
	}

	protected Suit getSuit()
	{
		return suit;
	}

	protected Color getColor()
	{
		return color;
	}

	protected static Suit[] getRedsuite()
	{
		return REDSUITE;
	}

	protected static Suit[] getBlacksuite()
	{
		return BLACKSUITE;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((rank == null) ? 0 : rank.hashCode());
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (color != other.color)
			return false;
		if (rank != other.rank)
			return false;
		if (suit != other.suit)
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "Card [rank=" + rank + ", color=" + color + ", suit="
				+ suit.suit() + "]";
	}

	@Override
	public int compareTo(Card o)
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
