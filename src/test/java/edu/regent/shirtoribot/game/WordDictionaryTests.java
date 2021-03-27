package edu.regent.shirtoribot.game;

import edu.regent.shiritoribot.game.WordDictionary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WordDictionaryTests {
    private WordDictionary dict;

    @Test
    public void contains_returnsTrue_whenWordIsInDictionary() {
        dict = WordDictionary.of("word");
        assert(dict.contains("word"));
    }

    @Test
    public void contains_returnsFalse_whenWordIsNotInDictionary() {
        dict = WordDictionary.of("words");
        assert(!dict.contains("alphabet"));
    }

    @Test
    public void contains_isNotCaseSensitive() {
        dict = WordDictionary.of("Word");
        assert(dict.contains("wORd"));
    }

}
