package com.github.danwiseman.cardkraken.kafka.streams.model;

public class CommanderCard
{
    private String name;
    private String id;
    private String qty;

    public CommanderCard(String name, String id, String qty) {
        this.name = name;
        this.id = id;
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
