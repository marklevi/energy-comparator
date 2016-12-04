package com.uswitch.comparator.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexHelper {

    public static String SP = "\\s+";
    public static String NUMBER = "\\d+";

    public static String group(String name, String regex) {
        return String.format("(?<%s>%s)", name, regex);
    }

    public static Matcher match(Pattern pattern, String input) {
        final Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(input);
        }
        return matcher;
    }


    public static boolean matches(Pattern pattern, String input) {
        return pattern.matcher(input).matches();
    }
}
