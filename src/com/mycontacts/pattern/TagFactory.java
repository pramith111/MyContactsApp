package com.mycontacts.pattern;

import com.mycontacts.model.Tag;

import java.util.HashMap;
import java.util.Map;

public class TagFactory {
    private static Map<String, Tag> tagPool = new HashMap<>();

    public static Tag getTag(String name, String color) {
        String key = name.toLowerCase();
        if (!tagPool.containsKey(key)) {
            tagPool.put(key, new Tag(name, color));
            System.out.println("✓ New tag created: " + name);
        } else {
            System.out.println("✓ Reusing existing tag: " + name);
        }
        return tagPool.get(key);
    }

    public static Tag getTag(String name) {
        return getTag(name, "default");
    }

    public static Map<String, Tag> getAllTags() {
        return new HashMap<>(tagPool);
    }

    public static void displayAllTags() {
        if (tagPool.isEmpty()) {
            System.out.println("No tags created yet.");
        } else {
            System.out.println("\n--- All Tags ---");
            tagPool.values().forEach(System.out::println);
        }
    }
}