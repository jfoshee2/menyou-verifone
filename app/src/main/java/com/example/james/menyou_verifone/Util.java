package com.example.james.menyou_verifone;

import java.util.List;

/**
 * Created by austin on 4/24/2018.
 */

public class Util {

    public static List<String> sanitizeList (List<String> ingredients) {

        for(int i = 0; i < ingredients.size(); i++){

            String item = ingredients.get(i);

            //This regex is meant to get rid of everything that is not an alphanumeric character.
            //and also a white space between words such as lamb chop.
            String everythingNotAlphanumeric = item.replaceAll("(?![A-Z\\s])\\W+", "");

            //This regex gets rid of any numbers.
            String getRidOfNumbers = everythingNotAlphanumeric.replaceAll("([0-9])+", "");

            ingredients.set(i, getRidOfNumbers.trim());
        }
        return ingredients;
    }
}
