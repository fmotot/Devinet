package fr.eni.devinet.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Utils {
    private Utils() {
    }

    public static StringBuilder capitalize(StringBuilder sb){

        return new StringBuilder(String.valueOf(Character.toUpperCase(sb.charAt(0)))).append(sb.substring(1));
    }

    public static List<String> shuffleString(String string) {
        List<String> letters = new ArrayList<>();
        for(char c : string.toCharArray()) {
            letters.add(String.valueOf(c));
        }
        Collections.shuffle(letters);

        return letters;
    }
}
