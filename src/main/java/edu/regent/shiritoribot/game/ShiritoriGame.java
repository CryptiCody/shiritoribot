package edu.regent.shiritoribot.game;

import java.util.ArrayList;
import java.util.List;

public class ShiritoriGame {

    private WordDictionary wordDictionary;
    private List<String> usedWords = new ArrayList<>();
    private String previousWord;

    public ShiritoriGame(WordDictionary wordDictionary) {
        this.wordDictionary = wordDictionary;
    }

    public void submitWord(String word) throws EliminationException {
        if(isValidWord(word)) {
            usedWords.add(word.toLowerCase());
            previousWord = word;
        } else {
            throw new EliminationException();
        }
    }

    private boolean isValidWord(String word) throws EliminationException {
        if(!startsWithSameLetterAsPreviousWordEndedWith(word)) return false;
        if(hasBeenUsedBefore(word)) return false;
        return wordDictionary.contains(word);
    }

    private boolean startsWithSameLetterAsPreviousWordEndedWith(String word) {
        if(previousWord == null) return true;
        return Character.toLowerCase(lastLetterOf(previousWord)) == Character.toLowerCase(firstLetterOf(word));
    }

    private boolean hasBeenUsedBefore(String word) {
        return usedWords.contains(word.toLowerCase());
    }

    private static char firstLetterOf(String word) {
        return word.charAt(0);
    }

    private static char lastLetterOf(String word) {
        return word.charAt(word.length() - 1);
    }

}
