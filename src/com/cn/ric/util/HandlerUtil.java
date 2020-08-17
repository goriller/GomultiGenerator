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
            strBuilder.append("Handler interface {\n\t");
            strBuilder.append("List(ctx *gin.Context)\n\t");
            strBuilder.append("Create(ctx *gin.Context)\n\t");
            strBuilder.append("Delete(ctx *gin.Context)\n\t");
            strBuilder.append("Update(ctx *gin.Context)");
            strBuilder.append("\n}\n\n");

            strBuilder.append("func New");
            strBuilder.append(structName);
            strBuilder.append("Handler() I");
            strBuilder.append(structName);
            strBuilder.append("Handler {\n\t");
            strBuilder.append("return &");
            strBuilder.append(structName);
            strBuilder.append("Handler {\n\t\t");
            strBuilder.append("//todo\n\t");
            strBuilder.append("}\n");
            strBuilder.append("\n}\n\n");

            strBuilder.append("func (h *");
            strBuilder.append(structName);
            strBuilder.append("Handler) List(ctx *gin.Context) {\n\t");
            strBuilder.append("//todo");
            strBuilder.append("\n}\n\n");

            strBuilder.append("func (h *");
            strBuilder.append(structName);
            strBuilder.append("Handler) Create(ctx *gin.Context) {\n\t");
            strBuilder.append("//todo");
            strBuilder.append("\n}\n\n");

            strBuilder.append("func (h *");
            strBuilder.append(structName);
            strBuilder.append("Handler) Delete(ctx *gin.Context) {\n\t");
            strBuilder.append("//todo");
            strBuilder.append("\n}\n\n");

            strBuilder.append("func (h *");
            strBuilder.append(structName);
            strBuilder.append("Handler) Update(ctx *gin.Context) {\n\t");
            strBuilder.append("//todo");
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
