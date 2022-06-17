package ru.cs.vsu.ast2.util;

import java.util.regex.Pattern;

public final class StringUtil {

    private StringUtil() {}

    public static String getTypeName(String name) {
        switch (name) {
            case "passenger":
                return "Легковой";
            case "truck":
                return "Грузовой";
            case "invalid":
                return "Для инвалидов";
            case "Легковой":
                return "passenger";
            case "Грузовой":
                return "truck";
            case "Для инвалидов":
                return "invalid";
            default:
                return "";
        }
    }

    public static boolean isCarNumber(String s) {
        String trimmed = s.trim();
        if (trimmed.isEmpty())
            return false;
        return Pattern.compile("^[АВЕКМНОРСТУХ]\\d{3}(?<!000)[АВЕКМНОРСТУХ]{2}\\d{2,3}").matcher(trimmed).matches();

    }

}