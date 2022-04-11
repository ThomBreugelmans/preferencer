package com.thombreugelmans;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 */
public class Preferencer {

    private static Preferencer instance;

    private int maxPreferences;
    private List<Pair<Integer, String>> topics;
    private List<Profile> profiles;

    private Preferencer() {
        this.maxPreferences = 0;
        this.topics = new ArrayList<>();
    }

    public static Preferencer getInstance() {
        if (instance == null) {
            instance = new Preferencer();
        }
        return instance;
    }

    public void addProfile(Profile p) {
        if (p.getPreferences().size() != maxPreferences) {
            // The profile should have the same amount of preferences as specified
            throw new RuntimeException("The amount of profiles do not match the specified amount");
        }
        this.profiles.add(p);
    }
    public void clearProfiles() {
        this.profiles.clear();
    }

    public void addTopic(String topic) {
        // add 1 to the max ID in the current topics list
        int id = this.topics.stream().max(new Comparator<Pair<Integer, String>>() {
            @Override
            public int compare(Pair<Integer, String> p1, Pair<Integer, String> p2) {
                return Integer.compare(p1.getKey(), p2.getKey());
            }
        }).get().getKey() + 1;
        this.topics.add(new Pair<>(id, topic));
    }
    public void removeTopic(String t) {
        for (Pair<Integer, String> p : this.topics) {
            if (p.getValue().equals(t)) {
                this.topics.remove(p);
                break;
            }
        }
    }

    public void setMaxPreferences(int max) {
        this.maxPreferences = max;
    }

    public Object computePreferences() {
        // compute the optimal preferences using Dynamic Programming
        // OPT(?) = ?
        // TODO: compute the optimal preferences
        return null;
    }

}
