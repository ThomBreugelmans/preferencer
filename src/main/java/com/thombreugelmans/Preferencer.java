package com.thombreugelmans;

import javafx.util.Pair;

import java.util.*;

/**
 *
 */
public class Preferencer {

    private static Preferencer instance;

    private int amountOfPreferences;
    private Map<Integer, String> topics;
    private List<Profile> profiles;

    private Preferencer() {
        this.amountOfPreferences = 0;
        this.topics = new HashMap<>();
        this.profiles = new ArrayList<>();
    }

    public static Preferencer getInstance() {
        if (instance == null) {
            instance = new Preferencer();
        }
        return instance;
    }

    public void addProfile(Profile p) {
        this.profiles.add(p);
    }
    public Profile getProfileById(int id) {
        Profile p = null;
        for (Profile prof : this.profiles) {
            if (prof.getId() == id) {
                p = prof;
                break;
            }
        }
        return p;
    }
    public void clearProfiles() {
        this.profiles.clear();
    }

    public int addTopic(String topic) {
        // add 1 to the max ID in the current topics list
        int id = 1 + this.topics.keySet().stream().reduce(0, (a, b) -> b > a ? b : a);
        this.topics.put(id, topic);
        return id;
    }
    public void removeTopicByString(String t) {
        while (this.topics.values().remove(t));
    }
    public void removeTopicById(int id) {
        this.topics.remove(id);
    }
    public void clearTopics() {
        this.topics.clear();
    }
    public String getTopicById(int id) {
        if (this.topics.containsKey(id)) {
            return this.topics.get(id);
        }
        else
            return null;
    }

    public void setAmountOfPreferences(int max) {
        this.amountOfPreferences = max;
    }
    public int getAmountOfPreferences() { return this.amountOfPreferences; }
    public List<Profile> getErroneousProfiles() {
        List<Profile> erroneousProfiles = new ArrayList<>();
        for (Profile p : this.profiles) {
            if (p.getPreferences().size() != this.amountOfPreferences) {
                erroneousProfiles.add(p);
            }
        }
        return erroneousProfiles;
    }

    // TODO: figure out a more efficient (time and space) way to compute optimal matching
    public Map<Integer, Integer> computeOptimalPreferences() {
        // firstly we want to assure there should be a possible subset of topics to preferences
        Set<Integer> preferedTopics = new HashSet<>();
        // get the ids of all topics that appear in the preferences lists
        for (Profile p : this.profiles) {
            for (int pref : p.getPreferences()) {
                preferedTopics.add(pref);
            }
        }
        if (preferedTopics.size() < this.profiles.size()) {
            // there are too little topics per person, throw an error
            throw new RuntimeException("Subset of prefered topics is too small, reduce number of participants, increase amount of preferences or give more diverse preferences");
        }

        // compute all permutations of the prefered topics
        Set<LinkedList<Integer>> permutations = new HashSet<>();
        LinkedList<Integer> tmp = new LinkedList<>();
        LinkedList<Integer> topicIds = new LinkedList<>(this.topics.keySet());
        computePermutations(permutations, tmp, topicIds, this.profiles.size());

        // compute the optimal permutation over the prefered topics
        LinkedList<Integer> optimal = null;
        int min = Integer.MAX_VALUE;
        for (LinkedList<Integer> perm : permutations) {
            int newMin = 0;
            Iterator<Integer> cur = perm.iterator();
            for (Profile p : this.profiles) {
                newMin += p.getPreferences().indexOf(cur.next());
            }

            if (newMin < min) {
                min = newMin;
                optimal = perm;
            }
        }

        Map<Integer, Integer> solution = new HashMap<>();
        Iterator<Integer> it = optimal.iterator();
        for (Profile p : this.profiles) {
            solution.put(p.getId(), it.next());
        }

        return solution;
    }

    private static void computePermutations(
            Set<LinkedList<Integer>> permutations,
            LinkedList<Integer> currentState,
            LinkedList<Integer> topicIds,
            int sizeOfPerm) {
        if (currentState.size() == sizeOfPerm) {
            permutations.add(new LinkedList<>(currentState));
            return;
        }

        // we remove the head of the topics
        // add it to the currentState
        // pass them recursively to computePermutations
        // and then remove the topic from currentState and add it to the tail of the topics
        // we do this n times, where n is the size of topics, essentially rotating through all topics allowing for the least space complexity
        for (int i = 0; i < topicIds.size(); i++) {
            int tId = topicIds.removeFirst();
            currentState.addLast(tId);

            computePermutations(permutations, currentState, topicIds, sizeOfPerm);

            currentState.removeLast();
            topicIds.addLast(tId);
        }
    }

}
