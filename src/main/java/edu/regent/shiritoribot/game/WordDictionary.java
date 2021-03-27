package edu.regent.shiritoribot.game;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public interface WordDictionary {
    public boolean contains(String word);

    public static WordDictionary of(String... wordList) {
        return new WordDictionaryImpl(wordList);
    }

    public static WordDictionary fromFile(File file) throws IOException {
        Scanner scanner = new Scanner(file);
        List<String> wordList = new ArrayList<>();
        while(scanner.hasNext()) {
            wordList.add(scanner.next());
        }
        return new WordDictionaryImpl(wordList);
    }
}
