package com.dialog.blend.util;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class NumberToGuess {

    private Random random;

    private List<String> numToWord = Arrays.asList("1,one", "2,two", "3,three,free", "4,four", "5,five",
            "6,six", "7,seven", "8,eight", "9,nine", "10,ten");

    public NumberToGuess(){
        this.random = new Random();
    }

    public String getGuessNum() {
        return numToWord.get(random.nextInt(numToWord.size()));
    }
}
