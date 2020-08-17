package com.cn.ric.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: GoBuilderGeneratorPlugin
 * @description:
 * @author: richen
 * @create: 2020-08-17 14:26
 **/

public class TagsUtil {
    private static final Pattern structPattern = Pattern.compile("(type\\s+\\w+\\s+struct\\s*\\{\\s*\n)([^}]*)(\\s*})");
    private static final Pattern fieldPattern = Pattern.compile("^([ \t]*([a-zA-Z0-9_]+)[ \t]+([*.a-zA-Z0-9_\\[\\]]+))([ \t]+(`[A-Za-z0-9_ :\"]+`))?", Pattern.MULTILINE);
    private static final Pattern wordPatten = Pattern.compile("[A-Z][A-Za-z0-9_]*]");

    public static String generateTagsPatternCode(String content, boolean json, boolean bson, boolean camel){
        // replace
        Matcher structMatcher = structPattern.matcher(content);
        StringBuffer modified = new StringBuffer();

        while (structMatcher.find()) {
            Matcher fieldMatcher = fieldPattern.matcher(structMatcher.group(2));
            int max_len = 0;
            while (fieldMatcher.find()) {
                int len = fieldMatcher.group(1).length();
                if (len > max_len) {
                    max_len = len;
                }
            }
            fieldMatcher = fieldPattern.matcher(structMatcher.group(2));
            StringBuffer modifiedFields = new StringBuffer();
            while (fieldMatcher.find()) {
                String tagName = fieldMatcher.group(2);
                if (camel) {
                    tagName = Character.toLowerCase(tagName.charAt(0)) + tagName.substring(1);
                } else {
                    tagName = Character.toLowerCase(tagName.charAt(0)) + tagName.substring(1);
                    StringBuffer tagBuffer = new StringBuffer();
                    for (int i = 0; i < tagName.length(); i++) {
                        if (Character.isUpperCase(tagName.charAt(i))) {
                            if (i  == 0) {
                                tagBuffer.append('_');
                            } else if (!Character.isUpperCase(tagName.charAt(i-1))) {
                                tagBuffer.append('_');
                            }
                        }
                        tagBuffer.append(Character.toLowerCase(tagName.charAt(i)));
                    }
                    tagName = tagBuffer.toString();
                }
                String bsonTag = String.format("bson:\"%s\"", tagName);
                String jsonTag = String.format("json:\"%s\"", tagName);
                if (tagName.equals("id")) {
                    bsonTag = String.format("bson:\"%s\"", "_id");
                }
                String tag = "";
                if (bson) {
                    tag = bsonTag;
                }
                if (json) {
                    if (!tag.equals("")) {
                        tag += " ";
                    }
                    tag += jsonTag;
                }
                String field;
                if (bson || json) {
                    StringBuffer spaces = new StringBuffer();
                    for (int i = 0; i < max_len - fieldMatcher.group(1).length(); i++) {
                        spaces.append(" ");
                    }
                    field = String.format("%s %s`%s`", fieldMatcher.group(1), spaces, tag);
                } else {
                    field = String.format("%s", fieldMatcher.group(1));
                }
                fieldMatcher.appendReplacement(modifiedFields, field);
            }
            fieldMatcher.appendTail(modifiedFields);
            structMatcher.appendReplacement(modified, String.format("%s%s%s", structMatcher.group(1), modifiedFields.toString(), structMatcher.group(3)));
        }
        structMatcher.appendTail(modified);
        return modified.toString();
    }
}
