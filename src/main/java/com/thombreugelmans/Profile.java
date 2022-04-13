package com.thombreugelmans;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Profile {

    private int id;
    private String name;
    private List<Integer> preferences;

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

    public void setPreferences(List<Integer> preferences) {
        this.preferences = preferences;
    }

    public List<Integer> getPreferences() {
        return preferences;
    }
}
