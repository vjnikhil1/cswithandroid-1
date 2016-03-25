package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private ArrayList<String> wordList = new ArrayList<>();
    private HashSet<String> wordSet = new HashSet<>();
    private HashMap<String, ArrayList<String>> lettersToWord = new HashMap<>();

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
            wordSet.add(word);
            char[] chars = word.toCharArray();
            Arrays.sort(chars);
            String newWord = new String(chars);
            if(lettersToWord.get(newWord)!=null)
                lettersToWord.get(newWord).add(word);
            else{
                ArrayList<String> values = new ArrayList<>();
                values.add(word);
                lettersToWord.put(newWord, values);
            }
        }
    }

    public boolean isGoodWord(String word, String base) {
        if(wordSet.contains(word)&&!word.contains(base))
            return true;
        else
            return false;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<>();
        for(char i='a';i<='z';i++){
            String temp = word + i;
            char[] chars = temp.toCharArray();
            Arrays.sort(chars);
            String nw = new String(chars);
            //Log.d("mauka", "defaultAction: " + nw);
            if (lettersToWord.get(nw) != null) {
                result.addAll(lettersToWord.get(nw));
            }
        }
        return result;
    }

    public String pickGoodStarterWord() {
        int i = random.nextInt((wordList.size()));
        while(getAnagramsWithOneMoreLetter(wordList.get(i)).size()<=MIN_NUM_ANAGRAMS){
            i = random.nextInt((wordList.size()));
        }
        return wordList.get(i);
    }
}
