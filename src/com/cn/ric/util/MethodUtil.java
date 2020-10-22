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
        int i = selectedLine.indexOf(".");
        String func = selectedLine.substring(0, i);
        String method = selectedLine.substring(i + 1);

        if (!text.contains(" " + func + " ")){
            return "";
        }

        String pattern;
        if (Pattern.matches("\\S+[(].*[)].*", method)) {
            pattern = "// %s \nfunc (%s *%s) %s {\n\t\n}";
//        } else if (Pattern.matches("\\S", method)) {
//            pattern = "// %s \nfunc (%s *%s) %s() {\n\t\n}";
        } else {
            return "";
        }

        String firstChar = func.substring(0, 1).toLowerCase();
        String comment = method.substring(0, method.indexOf("("));
        return String.format(pattern, comment, firstChar, func, method);
    }
}
