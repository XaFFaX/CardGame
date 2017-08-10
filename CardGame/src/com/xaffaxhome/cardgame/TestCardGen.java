package com.xaffaxhome.cardgame;

import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import com.xaffaxhome.cardgame.Card.Color;

public class TestCardGen
{
	protected Predicate<Card> incorrectCard()
	{
		return (c -> ((Arrays.asList(Card.getRedsuite()).contains(c.getSuit()))
				&& c.getColor() == Color.BLACK)
				|| ((Arrays.asList(Card.getBlacksuite()).contains(c.getSuit()))
						&& c.getColor() == Color.RED));
	}

	@Test
	public void testCard()
	{
		List<Card> badCards = Stream.generate(Card::new).limit(1000000)
				.filter(incorrectCard()).collect(Collectors.toList());
		if (!badCards.isEmpty())
		{
			badCards.forEach(c -> System.out.println("BAD: " + c));
			fail("There are bad cards in the set, above is the list!");
		}
	}

}
