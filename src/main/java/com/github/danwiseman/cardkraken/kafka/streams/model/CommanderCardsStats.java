package com.github.danwiseman.cardkraken.kafka.streams.model;

import com.google.common.collect.Iterables;

import java.util.List;

public class CommanderCardsStats {

    private List<String> commanders;
    private List<String> commander_uuids;
    private String stats_id;
    private List<CommanderCard> card_counts;


    public CommanderCardsStats() {

    }

    public CommanderCardsStats(List<String> commanders, List<String> commander_uuids, String stats_id, List<CommanderCard> card_counts) {
        this.commanders = commanders;
        this.commander_uuids = commander_uuids;
        this.stats_id = stats_id;
        this.card_counts = card_counts;
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

    public String getStats_id() {
        return stats_id;
    }

    public void setStats_id(String stats_id) {
        this.stats_id = stats_id;
    }

    public List<String> getCommanders() {
        return commanders;
    }

    public void setCommanders(List<String> commanders) {
        this.commanders = commanders;
    }

    public List<String> getCommander_uuids() {
        return commander_uuids;
    }

    public void setCommander_uuids(List<String> commander_uuids) {
        this.commander_uuids = commander_uuids;
    }
}
