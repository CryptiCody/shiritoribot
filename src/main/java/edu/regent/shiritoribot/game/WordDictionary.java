package edu.regent.shiritoribot.game;

public interface WordDictionary {
    public boolean contains(String word);

    public static WordDictionary of(String... wordList) {
        return new WordDictionaryImpl(wordList);
    }
}
