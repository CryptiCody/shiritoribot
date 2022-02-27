package edu.regent.shiritoribot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShiritoriBotTest {


    @Test
    void getWordDictionary_returnsValidDictionary() {
        var dictionary = ShiritoriBot.getWordDictionary();
        assertNotNull(dictionary);
        assertTrue(dictionary.contains("Word"));
    }
}