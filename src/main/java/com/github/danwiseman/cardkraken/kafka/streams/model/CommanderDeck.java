package com.github.danwiseman.cardkraken.kafka.streams.model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CommanderDeck
{
    private List<String> commanders;
    private List<String> commanders_uuids;
    private List<CommanderCard> cards;
    private String deck_id;
    private String game_type;
    private String source;
    private String url;

    public CommanderDeck() {
    }

    public CommanderDeck(List<String> commanders, List<String> commanders_uuids, List<CommanderCard> cards, String deck_id, String game_type, String source, String url) {
        this.commanders = commanders;
        this.commanders_uuids = commanders_uuids;
        this.cards = cards;
        this.deck_id = deck_id;
        this.game_type = game_type;
        this.source = source;
        this.url = url;
    }

    public List<String> getCommanders() {
        return commanders;
    }

    public void setCommanders(List<String> commanders) {
        this.commanders = commanders;
    }

    public List<CommanderCard> getCards() {
        return cards;
    }


    public List<String> getCardIds() {
        List<String> card_ids = new ArrayList<String>();

        for (CommanderCard card : cards) {
            JSONObject card_json = new JSONObject(card);
            card_ids.add(card_json.getString("id"));
        }

        return card_ids;

    }


    public void setCards(List<CommanderCard> cards) {
        this.cards = cards;
    }

    public String getDeck_id() {
        return deck_id;
    }

    public void setDeck_id(String deck_id) {
        this.deck_id = deck_id;
    }

    public String getGame_type() {
        return game_type;
    }

    public void setGame_type(String game_type) {
        this.game_type = game_type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getCommanders_uuids() {
        return commanders_uuids;
    }

    public void setCommanders_uuids(List<String> commanders_uuids) {
        this.commanders_uuids = commanders_uuids;
    }
}
