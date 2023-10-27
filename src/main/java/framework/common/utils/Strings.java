package framework.common.utils;

public class Strings {

    public static String toLowerCaseFirstLetter(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }
}
