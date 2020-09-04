package com.cn.ric.util;

import com.cn.ric.entity.StructEntity;

import java.util.List;
import java.util.Map;

/**
 * @author richen
 */
public class HandlerUtil {

    public static String generateHandlerPatternCode(List<StructEntity> structEntityList) {
        StringBuilder strBuilder = new StringBuilder();

        for (StructEntity structEntity : structEntityList) {
            String structName = structEntity.structName;
            String lowerStructName = toLowerCase(structEntity.structName);

            strBuilder.append("\n\n// ");
            strBuilder.append(structName);
            strBuilder.append(" handler pattern code\n");

            strBuilder.append("type I");
            strBuilder.append(structName);
            strBuilder.append(" interface {\n\t");
            strBuilder.append("//todo\n\t");
            strBuilder.append("\n}\n\n");

            strBuilder.append("func New");
            strBuilder.append(structName);
            strBuilder.append("() I");
            strBuilder.append(structName);
            strBuilder.append(" {\n\t");
            strBuilder.append("return &");
            strBuilder.append(structName);
            strBuilder.append("{\n\t\t");
            strBuilder.append("//todo\n\t");
            strBuilder.append("}\n");
            strBuilder.append("\n}\n\n");

        }
        return strBuilder.toString();
    }

    private static String toLowerCase(String str) {
        char[] cs = str.toCharArray();
        if (Character.isUpperCase(cs[0])) {
            cs[0] += 32;
        }
        return String.valueOf(cs);
    }
}
