package edu.regent.shiritoribot.game;

import java.util.Arrays;
import java.util.List;

public class SimpleWordDictionaryImpl implements WordDictionary {

    private List<String> wordList;

    public SimpleWordDictionaryImpl(String... wordList) {
        this.wordList = Arrays.asList(wordList);
    }

    public SimpleWordDictionaryImpl(List<String> wordList) {
        this.wordList = wordList;
    }

    @Override
    public boolean contains(String word) {
        return wordList.stream().anyMatch(word::equalsIgnoreCase);
    }
}
