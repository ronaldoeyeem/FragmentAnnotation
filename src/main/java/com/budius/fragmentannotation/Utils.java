package com.budius.fragmentannotation;

/**
 * Created by budius on 26.08.14.
 */
public class Utils {

    private static final String SPACE = "   ";

    public static String addIndentation(String line) {
        return line + SPACE;
    }

    public static String removeIndentation(String line) {
        return line.substring(0, line.length() - SPACE.length());
    }

    public static String firstUpper(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1, string.length());
    }

    public static String simplifiedType(String type) {
        try {
            String[] vals = type.split("\\.");
            return vals[vals.length - 1];
        } catch (Exception e) {
            return type;
        }
    }

}
