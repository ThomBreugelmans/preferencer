package com.thombreugelmans;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Profile {

    private int id;
    private String name;
    private List<Topic> preferences;

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
        if (name == null) return;
        this.name = name;
    }

    public void setPreferences(List<Topic> preferences) {
        this.preferences = preferences;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return id == profile.id && name.equals(profile.name) && Objects.equals(preferences, profile.preferences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, preferences);
    }

    public List<Topic> getPreferences() {
        return preferences;
    }
}
