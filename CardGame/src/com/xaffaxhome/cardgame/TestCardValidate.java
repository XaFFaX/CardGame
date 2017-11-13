package com.xaffaxhome.cardgame;

import org.junit.Test;

import com.xaffaxhome.cardgame.Card.Color;
import com.xaffaxhome.cardgame.Card.Rank;

public class TestCardValidate
{

	@Test
	public void test()
	{
		FrenchCard onePair[] = { new FrenchCard(Rank.TWO, Color.BLACK), new FrenchCard(Rank.TWO, Color.BLACK),
				new FrenchCard(), new FrenchCard(), new FrenchCard() };
		FrenchCard twoPairs[] = { new FrenchCard(), new FrenchCard(), new FrenchCard(), new FrenchCard(),
				new FrenchCard() };
		FrenchCard threeOfAKind[] = { new FrenchCard(), new FrenchCard(), new FrenchCard(), new FrenchCard(),
				new FrenchCard() };
		FrenchCard straight[] = { new FrenchCard(), new FrenchCard(), new FrenchCard(), new FrenchCard(),
				new FrenchCard() };
		FrenchCard flush[] = { new FrenchCard(), new FrenchCard(), new FrenchCard(), new FrenchCard(),
				new FrenchCard() };
		FrenchCard fullHouse[] = { new FrenchCard(), new FrenchCard(), new FrenchCard(), new FrenchCard(),
				new FrenchCard() };
		FrenchCard fourOfAKind[] = { new FrenchCard(), new FrenchCard(), new FrenchCard(), new FrenchCard(),
				new FrenchCard() };
		FrenchCard straightFlush[] = { new FrenchCard(), new FrenchCard(), new FrenchCard(), new FrenchCard(),
				new FrenchCard() };
	}

}
