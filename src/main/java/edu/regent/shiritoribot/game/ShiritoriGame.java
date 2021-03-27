package edu.regent.shiritoribot.game;

public class ShiritoriGame {

    public class EliminationException extends Exception {

    }

    public boolean submitWord(String word) throws EliminationException {
        if(!startsWithSameLetterAsPreviousWordEndedWith(word)) return false;
        if(hasBeenUsedBefore(word)) return false;
        if(!isInLegalWordDictionary(word)) return false;
        return true;
    }

    private boolean startsWithSameLetterAsPreviousWordEndedWith(String word) {
        return true;
    }

    private boolean hasBeenUsedBefore(String word) {
        return false;
    }

    private boolean isInLegalWordDictionary(String word) {
        return true;
    }


}
