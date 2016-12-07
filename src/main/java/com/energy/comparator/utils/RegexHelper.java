package com.energy.comparator.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexHelper {

    public static final String TYPE = "variable|standard|fixed|standing-charge";
    public static final String SP = "\\s+";
    public static final String NUMBER = "\\d+";
    public static final String SUPPLIER = "eon|ovo|edf|bg";

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
