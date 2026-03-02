package com.mycontacts.model;

import java.util.Objects;

public class Tag {
    private String name;
    private String color;

    public Tag(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() { return name; }
    public String getColor() { return color; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag)) return false;
        Tag tag = (Tag) o;
        return Objects.equals(name.toLowerCase(), tag.name.toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase());
    }

    @Override
    public String toString() {
        return "[Tag: " + name + " (" + color + ")]";
    }
}