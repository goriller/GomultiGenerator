package com.cn.ric.util;

import java.util.regex.Pattern;

/**
 * @program: GoBuilderGeneratorPlugin
 * @description:
 * @author: richen
 * @create: 2020-08-17 14:51
 **/

public class MethodUtil {

    public static String generateMethodPatternCode(String selectedLine, String text) {
        String[] params = selectedLine.split("\\.");
        if (params.length != 2){
            return "";
        }
        if (!text.contains(" " + params[0] + " ")){
            return "";
        }

        String pattern;
        if (Pattern.matches("\\S+[(].*[)].*", params[1])) {
            pattern = "func (%s *%s) %s {\n\t\n}";
        } else if (Pattern.matches("\\S+", params[1])) {
            pattern = "func (%s *%s) %s() {\n\t\n}";
        } else {
            return "";
        }

        String firstChar = params[0].substring(0, 1).toLowerCase();
        return String.format(pattern, firstChar, params[0], params[1]);
    }
}
