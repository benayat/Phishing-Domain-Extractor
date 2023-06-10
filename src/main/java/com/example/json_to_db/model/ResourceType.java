package com.example.json_to_db.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public enum ResourceType {

    FILE_SYSTEM_RESOURCE("FILE_SYSTEM_RESOURCE"),
    CLASSPATH_RESOURCE("CLASSPATH_RESOURCE");

    private final String value;
    private static final Map<String, ResourceType> lookup = new HashMap<>();

    static {
        for (ResourceType status : ResourceType.values()) {
            lookup.put(status.getValue(), status);
        }
    }

    public static ResourceType get(String value) {
        return lookup.get(value);
    }
}
