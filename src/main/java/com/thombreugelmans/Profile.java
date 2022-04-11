package com.thombreugelmans;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Profile {

    private int id;
    private String name;
    private List<Pair<Integer, String>> preferences;

    public Profile(String name, int id) {
        this.name = name;
        this.id = id;
        this.preferences = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPreferences(List<Pair<Integer, String>> preferences) {
        this.preferences = preferences;
    }

    public List<Pair<Integer, String>> getPreferences() {
        return preferences;
    }
}
