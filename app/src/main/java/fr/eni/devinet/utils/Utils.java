package fr.eni.devinet.utils;

import android.util.Log;

public class Utils {
    private Utils() {
    }

    public static StringBuilder capitalize(StringBuilder sb){

        return new StringBuilder(String.valueOf(Character.toUpperCase(sb.charAt(0)))).append(sb.substring(1));
    }
}
