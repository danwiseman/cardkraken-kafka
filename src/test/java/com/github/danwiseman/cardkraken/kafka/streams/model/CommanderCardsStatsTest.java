package com.github.danwiseman.cardkraken.kafka.streams.model;

import static org.junit.Assert.*;

import com.google.common.collect.Iterables;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CommanderCardsStatsTest {


    private static final CommanderCard cardOne = new CommanderCard(
            "Umara Raptor",
            "6049cc80-1faa-48bf-897e-fefe5a8e7ab2",
            "1");
    private static final CommanderCard cardTwo = new CommanderCard(
            "Unclaimed Territory",
            "6037414e-8fa6-48df-bcd6-f5be022bf4af",
            "1");
    private static final List<CommanderCard> cardList = new ArrayList<CommanderCard>();


    @Test
    public void addCards() {
        cardList.add(cardOne);
        CommanderCardsStats testCards = new CommanderCardsStats("test commander", "test id", cardList);

        assertEquals(1, testCards.getCard_counts().size());

        List<CommanderCard> anotherCardList = new ArrayList<CommanderCard>();
        anotherCardList.add(cardOne);
        anotherCardList.add(cardTwo);

        testCards.addCards(anotherCardList);
        int index =
                Iterables.indexOf(testCards.getCard_counts(), c -> cardOne.getName().equals(c.getName()));

        assertEquals("2", testCards.getCard_counts().get(index).getQty());

    }
}