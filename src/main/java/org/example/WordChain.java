package org.example;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class WordChain {

    private static final EnglishDictionary ENGLISH_DICTIONARY = new EnglishDictionary();

    private final String base;
    private final String target;
    private final List<String> usedWords;

    public WordChain(final String base, final String target) {
        if (base.length() != target.length()) {
            throw new IllegalArgumentException("Base and target string must have the same length! Current values " + base + " - " + target);
        }

        this.base = base.toLowerCase();
        this.target = target.toLowerCase();
        this.usedWords = Lists.newArrayList(this.base);

        buildChain();
    }

    public boolean isComplete() {
        return usedWords.size() > 0 && usedWords.get(usedWords.size() - 1).equals(target);
    }

    public int getSize() {
        return usedWords.size();
    }

    @Override
    public String toString() {
        return "WordChain " + "for '" + base + "'" + " to '" + target + "'" + " with result = " + usedWords;
    }

    private void buildChain() {
        String currentChainElement = base;

        while (!currentChainElement.equals(target)) {
            final Optional<String> nextWord = getWordInDirectNeighborhood(currentChainElement);

            if (nextWord.isPresent()) {
                currentChainElement = nextWord.get();
                usedWords.add(currentChainElement);
            } else {
                completeWithSubChainsInExpandedNeighborhood(currentChainElement);
                break;
            }
        }
    }

    private Optional<String> getWordInDirectNeighborhood(final String currentChainElement) {
        final Set<String> nextWords = Rekombobulator.createNextPossibleWords(currentChainElement.toCharArray(), target.toCharArray());

        return ENGLISH_DICTIONARY.getExistingWord(nextWords);
    }

    private void completeWithSubChainsInExpandedNeighborhood(final String currentChainElement) {
        final Optional<WordChain> subChain = getSubChainFromExpandedNeighborhood(currentChainElement);

        if (subChain.isPresent()) {
            usedWords.addAll(subChain.get().usedWords);
        } else {
            System.out.println("Found no english words! :( ");
        }
    }

    private Optional<WordChain> getSubChainFromExpandedNeighborhood(final String currentChainElement) {
        final Set<String> matchingWords = getMatchingWords(currentChainElement);

        return matchingWords.stream().parallel()
                .map((String word) -> new WordChain(word, target))
                .filter(WordChain::isComplete)
                .min((WordChain chain1, WordChain chain2) -> Integer.compare(chain1.getSize(), chain2.getSize()) * -1);
    }

    private Set<String> getMatchingWords(final String currentChainElement) {
        final Set<String> patterns = Rekombobulator.createPatterns(currentChainElement.toCharArray(), target.toCharArray());
        final int wordLength = base.length();

        return patterns.stream().parallel()
                .map( (String pattern) -> ENGLISH_DICTIONARY.getMatchingWords(pattern, wordLength))
                .flatMap(List::stream)
                .filter( (String word) -> !word.equals(currentChainElement) )
                .collect(Collectors.toSet());
    }
}