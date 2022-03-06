package edu.regent.shirtoribot.game;

import edu.regent.shiritoribot.game.WordDictionary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

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

    @Test
    public void fromFile_loadsAllWords() throws Exception {
        File dictFile = new File(getClass().getClassLoader().getResource("simpleDictionary.txt").getFile());
        dict = WordDictionary.fromFile(dictFile);

        assert(dict.contains("europe"));
        assert(dict.contains("neptune"));
        assert(dict.contains("mars"));
        assert(dict.contains("jupiter"));
        assert(dict.contains("salt lake city")); //ensures multi-word names work
    }

    @Test
    public void fromStream_loadsAllWords() throws Exception {
        var inputSteam = getClass().getClassLoader().getResourceAsStream("simpleDictionary.txt");
        dict = WordDictionary.fromStream(inputSteam);

        assert(dict.contains("europe"));
        assert(dict.contains("neptune"));
        assert(dict.contains("mars"));
        assert(dict.contains("jupiter"));
        assert(dict.contains("salt lake city")); //ensures multi-word names work
    }

}
