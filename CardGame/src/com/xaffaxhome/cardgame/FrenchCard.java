package com.xaffaxhome.cardgame;

import java.util.Arrays;
import java.util.Random;

public class FrenchCard extends Card implements Comparable<FrenchCard>
{
	private Rank	rank;
	private Suit	suit;
	private Color	color;

	public FrenchCard()
	{
		this(randomEnum(Rank.class), randomEnum(Color.class));
	}

	public FrenchCard(Rank rank, Color color)
	{
		if (rank == null)
			this.rank = randomEnum(Rank.class);
		else
			this.rank = rank;

		if (color == null)
		{
			this.suit = randomEnum(Suit.class);
			if (Arrays.asList(REDSUIT).contains(this.suit))
				this.color = Color.RED;
			else
			{
				this.color = color;
				if (this.color == Color.BLACK)
					this.suit = BLACKSUIT[new Random()
							.nextInt(BLACKSUIT.length)];
				else
					this.suit = REDSUIT[new Random().nextInt(REDSUIT.length)];
			}
		} else
		{
			this.color = color;
			if (this.color == Color.BLACK)
				this.suit = BLACKSUIT[new Random().nextInt(BLACKSUIT.length)];
			else
				this.suit = REDSUIT[new Random().nextInt(REDSUIT.length)];
		}

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

	public FrenchCard(Rank rank, Suit suit)
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

	@Override
	public String toString()
	{
		return "FrenchCard [rank=" + rank + ", suit=" + suit + ", color="
				+ color + "]";
	}

	@Override
	public int compareTo(FrenchCard card)
	{
		return this.rank.rank() - card.rank.rank();
	}

}
