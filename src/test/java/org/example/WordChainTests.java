package org.example;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WordChainTests {

    @Test
    public void testCatToDogHasFourElements() {
        WordChain wordChain = new WordChain("cat", "dog");
        assertThat(wordChain.getSize()).isEqualTo(4);
        System.out.println(wordChain);
    }

    @Test
    public void testLeadToGoldHasFourElements() {
        WordChain wordChain = new WordChain("lead", "gold");
        assertThat(wordChain.getSize()).isEqualTo(4);
        System.out.println(wordChain);
    }

    @Test
    public void testRubyToCodeHasSixElements() {
        WordChain wordChain = new WordChain("ruby", "code");
        assertThat(wordChain.getSize()).isEqualTo(6);
        System.out.println(wordChain);
    }

    @Test
    public void testCatToDogIsComplete() {
        WordChain wordChain = new WordChain("cat", "dog");
        assertThat(wordChain.isComplete()).isTrue();
    }

    @Test
    public void testLeadToGoldIsComplete() {
        WordChain wordChain = new WordChain("lead", "gold");
        assertThat(wordChain.isComplete()).isTrue();
    }

    @Test
    public void testRubyToCodeIsComplete() {
        WordChain wordChain = new WordChain("ruby", "code");
        assertThat(wordChain.isComplete()).isTrue();
    }
}
