package org.example;

import java.util.HashSet;
import java.util.Set;

public class Rekombobulator {

    public static Set<String> createNextPossibleWords(char[] input, char[] target) {
        final Set<String> options = new HashSet<>();

        for (int i = 0; i < input.length; i++) {
            if (input[i] != target[i]) {
                options.add(createElementWithValue(input, i, String.valueOf(target[i])));
            }
        }

        return options;
    }

    public static Set<String> createPatterns(char[] input, char[] target) {
        final Set<String> options = new HashSet<>();

        for (int i = 0; i < input.length; i++) {
            if (input[i] != target[i]) {
                options.add(createElementWithValue(input, i, "[a-z]"));
            }
        }

        return options;
    }

    private static String createElementWithValue(char[] input, int i, String replacement) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(input);
        stringBuilder.replace(i, i + 1, replacement);
        return stringBuilder.toString();
    }

}
