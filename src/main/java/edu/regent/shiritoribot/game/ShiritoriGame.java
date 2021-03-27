package edu.regent.shiritoribot.game;

public class ShiritoriGame {

    private

    public void handleUserWord(String wordSubmission) {

        try{
            submitWord("myWord");
        } catch (EliminationException e) {
            eliminatePlayer();
        }

        if(isLegalWord(wordSubmission)) {
            nextPlayer();
        } else {
            eliminatePlayer();
        }
    }

    public boolean submitWord(String word) throws EliminationException {
        if(!startsWithSameLetterAsPreviousWordEndedWith(word)) return false;
        if(hasBeenUsedBefore(word)) return false;
        if(!isInLegalWordDictionary(word)) return false;
        return true;
    }


}
