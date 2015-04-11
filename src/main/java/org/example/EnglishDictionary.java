package org.example;

import com.google.common.collect.ImmutableSet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class EnglishDictionary {

    private static final Path WORD_FILE_PATH = Paths.get("src/main/resources/english-words.txt");
    private final Set<String> englishWords;

    public EnglishDictionary() {
        try {
            englishWords = ImmutableSet.copyOf(Files.readAllLines(WORD_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Could not read dictionary! Reason: " + e.getMessage(), e);
        }
    }

    /**
     * Returns the first word that exists in the english dictionary using the given collection of words.
     * @param words collections of words that is checked
     * @return first real word or nothing if none of the words is real
     */
    public Optional<String> getExistingWord(Collection<String> words) {
        return words.stream().filter(englishWords::contains).findFirst();
    }

    public List<String> getMatchingWords(String pattern, int wordLength) {
        return englishWords.stream().parallel()
                .filter((String word) -> word.length() == wordLength)
                .filter((String word) -> word.matches(pattern) )
                .collect(Collectors.toList());
    }
}
