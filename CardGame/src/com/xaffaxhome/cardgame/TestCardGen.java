package com.xaffaxhome.cardgame;

import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class TestCardGen
{
	protected Predicate<FrenchCard> incorrectCard()
	{
		return (c -> ((Arrays.asList(FrenchCard.REDSUIT).contains(c.getSuit())) && c.getColor() == Card.Color.BLACK)
				|| ((Arrays.asList(FrenchCard.BLACKSUIT).contains(c.getSuit())) && c.getColor() == Card.Color.RED));
	}

	@Test
	public void testCard()
	{
		List<FrenchCard> badCards = Stream.generate(FrenchCard::new).limit(1000000).filter(incorrectCard())
				.collect(Collectors.toList());
		if (!badCards.isEmpty())
		{
			badCards.forEach(c -> System.out.println("BAD: " + c));
			fail("There are bad cards in the set, above is the list!");
		}
	}

}
