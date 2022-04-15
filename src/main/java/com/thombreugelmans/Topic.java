package com.thombreugelmans;

import java.util.Objects;

public class Topic {
    private String name;
    private int identifier;

    public Topic(String name, int id) {
        this.name = name;
        this.identifier = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) return;
        this.name = name;
    }

    public int getId() {
        return identifier;
    }

    public void setId(int identifier) {
        this.identifier = identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Topic topic = (Topic) o;
        return identifier == topic.identifier && name.equals(topic.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, identifier);
    }
}
