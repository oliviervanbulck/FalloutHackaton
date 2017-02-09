package com.oliviervanbulck.fallouthackaton.model;

/**
 * Created by olivi on 3/12/2015.
 */
public class Item {
    private String id;
    private String name;
    private int value;
    private int count;

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public int getCount() {
        return count;
    }

    public String getId() {
        return id;
    }
}
