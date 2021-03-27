package edu.regent.shirtoribot.game;

import edu.regent.shiritoribot.game.EliminationException;
import edu.regent.shiritoribot.game.ShiritoriGame;
import edu.regent.shiritoribot.game.WordDictionary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


public class ShitoriGameTests {

    private ShiritoriGame game;

    private WordDictionary mockDictionary;

    @BeforeEach()
    public void setup() {
        mockDictionary = mock(WordDictionary.class);
        when(mockDictionary.contains(anyString())).thenReturn(true);
        game = new ShiritoriGame(mockDictionary);
    }

    @Test
    public void submittingWordInDictionary_returnsNormally() throws Exception {
        when(mockDictionary.contains(anyString())).thenReturn(true);
        game.submitWord("validWord");
        assert true;
    }

    @Test
    public void submittingWordNotInDictionary_throwsEliminationException() {
        when(mockDictionary.contains(anyString())).thenReturn(false);
        assertThrows(EliminationException.class, ()-> game.submitWord("validWord"));
    }

    @Test
    public void wordReuse_throwsEliminationException() throws Exception {
        game.submitWord("noon");
        assertThrows(EliminationException.class, ()-> game.submitWord("noon"));
    }

    @Test
    public void wordReuse_isNotCaseSensitive() throws Exception {
        game.submitWord("noon");
        assertThrows(EliminationException.class, ()-> game.submitWord("NoOn"));
    }

    @Test
    public void submittingWordWithIncorrectFirstCharacter_throwsEliminationException() throws Exception {
        game.submitWord("pizza");
        assertThrows(EliminationException.class, ()-> game.submitWord("mario"));
    }

    @Test
    public void lastLetterFirstLetterRule_isNotCaseSensitive() throws Exception {
        game.submitWord("Neptune");
        game.submitWord("Europe");
    }
}
