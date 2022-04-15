package com.thombreugelmans;


import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PreferenceTests {

    @BeforeEach
    public void setup() {
        Preferencer preferencer = Preferencer.getInstance();
        preferencer.setAmountOfPreferences(0);
        preferencer.clearTopics();
        preferencer.clearProfiles();
    }

    @Test
    public void simplePreferenceTest() {
        Preferencer preferencer = Preferencer.getInstance();
        preferencer.setAmountOfPreferences(5);

        Profile p1 = new Profile("Thom", 1);
        Profile p2 = new Profile("Daniel", 2);
        Profile p3 = new Profile("Sam", 3);
        Profile p4 = new Profile("Kasper", 4);
        Profile p5 = new Profile("Leah", 5);

        preferencer.addTopic("Discover");
        preferencer.addTopic("Optimize");
        preferencer.addTopic("Merge Protocols");
        preferencer.addTopic("Explore Algos");
        preferencer.addTopic("Probabilities");

//        p1.setPreferences(Arrays.asList(1,2,3,5,4));
//        p2.setPreferences(Arrays.asList(1,3,2,4,5));
//        p3.setPreferences(Arrays.asList(3,4,1,2,5));
//        p4.setPreferences(Arrays.asList(4,1,2,5,3));
//        p5.setPreferences(Arrays.asList(2,4,5,1,3));

//        preferencer.addProfile(p1);
//        preferencer.addProfile(p2);
//        preferencer.addProfile(p3);
//        preferencer.addProfile(p4);
//        preferencer.addProfile(p5);

//        Map<Profile, Topic> optimal = preferencer.computeOptimalPreferences();
//        assertThat(optimal.get(1)).isEqualTo(2);
//        assertThat(optimal.get(2)).isEqualTo(1);
//        assertThat(optimal.get(3)).isEqualTo(3);
//        assertThat(optimal.get(4)).isEqualTo(4);
//        assertThat(optimal.get(5)).isEqualTo(5);
    }

}
