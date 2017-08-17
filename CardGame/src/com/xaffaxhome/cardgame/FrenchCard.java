package com.xaffaxhome.cardgame;

import java.util.Arrays;
import java.util.Random;

public class FrenchCard extends Card implements Comparable<FrenchCard>
{
	protected Rank rank;
	protected Suit suit;
	protected Color color;

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
	public int compareTo(FrenchCard card)
	{
		return card.rank.rank() - this.rank.rank();
	}

}
