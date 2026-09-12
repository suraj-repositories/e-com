package com.oranbyte.ecom.util;

public class SlugUtils {

    private SlugUtils() {
    }

    public static String toSlug(String text) {
        if (text == null || text.isBlank()) {
            return null;
        }

        return text
                .toLowerCase()
                .trim()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-");
    }
}