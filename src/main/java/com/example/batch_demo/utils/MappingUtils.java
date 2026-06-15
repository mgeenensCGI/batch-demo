package com.example.batch_demo.utils;

public class MappingUtils {

    private MappingUtils() {
    }

    public static String trim(String value) {
        return value == null ? null : value.trim();
    }

    public static Long parseLong(String value, String fieldName) {
        String cleaned = trim(value);
        if (cleaned == null || cleaned.isBlank()) {
            throw new IllegalArgumentException("Field '%s' is blank".formatted(fieldName));
        }

        try {
            return Long.valueOf(cleaned);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Field '%s' is not a valid number: %s".formatted(fieldName, cleaned), ex);
        }
    }

}
