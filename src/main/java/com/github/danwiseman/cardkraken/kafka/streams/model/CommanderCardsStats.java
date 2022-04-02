package com.github.danwiseman.cardkraken.kafka.streams.model;

import com.google.common.collect.Iterables;

import java.util.List;

public class CommanderCardsStats {

    private String commander_name;
    private String commander_uuid;

    private List<CommanderCard> card_counts;


    public CommanderCardsStats() {

    }

    public CommanderCardsStats(String commander_name, String commander_uuid, List<CommanderCard> card_counts) {
        this.commander_name = commander_name;
        this.commander_uuid = commander_uuid;
        this.card_counts = card_counts;
    }

    public String getCommander_name() {
        return commander_name;
    }

    public void setCommander_name(String commander_name) {
        this.commander_name = commander_name;
    }

    public String getCommander_uuid() {
        return commander_uuid;
    }

    public void setCommander_uuid(String commander_uuid) {
        this.commander_uuid = commander_uuid;
    }

    public List<CommanderCard> getCard_counts() {
        return card_counts;
    }

    public void setCard_counts(List<CommanderCard> card_counts) {
        this.card_counts = card_counts;
    }

    public void addCards(List<CommanderCard> cardsToAdd) {
        for(CommanderCard card : cardsToAdd) {
            if (this.card_counts.stream().anyMatch(item -> card.getName().equals(item.getName()))) {
                int index =
                        Iterables.indexOf(this.card_counts, c -> card.getName().equals(c.getName()));
                CommanderCard updatedQty = this.card_counts.get(index);
                Integer newQty = Integer.parseInt(updatedQty.getQty()) + 1;
                updatedQty.setQty(newQty.toString());
                this.card_counts.set(index, updatedQty);
            } else {
                this.card_counts.add(card);
            }
        }

    }
}
