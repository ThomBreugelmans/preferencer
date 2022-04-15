package com.thombreugelmans;

import javafx.util.Pair;

import java.util.*;

/**
 *
 */
public class Preferencer {

    private static Preferencer instance;

    private int amountOfPreferences;
    private List<Topic> topics;
    private List<Profile> profiles;

    private Preferencer() {
        this.amountOfPreferences = 0;
        this.topics = new ArrayList<>();
        this.profiles = new ArrayList<>();
    }

    public static Preferencer getInstance() {
        if (instance == null) {
            instance = new Preferencer();
        }
        return instance;
    }

    public Profile addProfile(String name, List<Topic> preferences) {
        int id = 1 + this.profiles.stream().map(Profile::getId).reduce(0, (a, b) -> b > a ? b : a);
        Profile p = new Profile(name, id);
        p.setPreferences(preferences);
        this.profiles.add(p);
        return p;
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
    public void removeProfile(Profile p) {
        this.profiles.remove(p);
    }
    public void clearProfiles() {
        this.profiles.clear();
    }

    public Topic addTopic(String topic) {
        // add 1 to the max ID in the current topics list
        int id = 1 + this.topics.stream().map(Topic::getId).reduce(0, (a, b) -> b > a ? b : a);
        Topic t = new Topic(topic, id);
        this.topics.add(t);
        return t;
    }
    public void removeTopic(Topic t) {
        this.topics.remove(t);
    }
    public void clearTopics() {
        this.topics.clear();
    }
    public Topic getTopicById(int id) {
        Topic t = this.topics.stream().reduce(null, (a, b) -> b.getId() == id ? b : a);
        return t;
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
    public Map<Profile, Topic> computeOptimalPreferences() {
        // firstly we want to assure there should be a possible subset of topics to preferences
        Set<Topic> preferedTopics = new HashSet<>();
        // get the ids of all topics that appear in the preferences lists
        for (Profile p : this.profiles) {
            for (Topic pref : p.getPreferences()) {
                preferedTopics.add(pref);
            }
        }
        if (preferedTopics.size() < this.profiles.size()) {
            // there are too little topics per person, throw an error
            throw new RuntimeException("Subset of prefered topics is too small, reduce number of participants, increase amount of preferences or give more diverse preferences");
        }

        // compute all permutations of the prefered topics
        Set<LinkedList<Topic>> permutations = new HashSet<>();
        LinkedList<Topic> tmp = new LinkedList<>();
        LinkedList<Topic> topics = new LinkedList<>(this.topics);
        computePermutations(permutations, tmp, topics, this.profiles.size());

        // compute the optimal permutation over the prefered topics
        LinkedList<Topic> optimal = null;
        int min = Integer.MAX_VALUE;
        for (LinkedList<Topic> perm : permutations) {
            int newMin = 0;
            Iterator<Topic> cur = perm.iterator();
            for (Profile p : this.profiles) {
                newMin += p.getPreferences().indexOf(cur.next());
            }

            if (newMin < min) {
                min = newMin;
                optimal = perm;
            }
        }

        Map<Profile, Topic> solution = new HashMap<>();
        Iterator<Topic> it = optimal.iterator();
        for (Profile p : this.profiles) {
            solution.put(p, it.next());
        }

        return solution;
    }

    private static void computePermutations(
            Set<LinkedList<Topic>> permutations,
            LinkedList<Topic> currentState,
            LinkedList<Topic> topics,
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
        for (int i = 0; i < topics.size(); i++) {
            Topic t = topics.removeFirst();
            currentState.addLast(t);

            computePermutations(permutations, currentState, topics, sizeOfPerm);

            currentState.removeLast();
            topics.addLast(t);
        }
    }

}
