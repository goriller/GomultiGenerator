package com.cn.ric.entity;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author richen
 */
public class StructEntity {
    public String structName;
    public Map<String, String> structKeyValue;

    public StructEntity(String structName) {
        this.structName = structName;
        structKeyValue = new LinkedHashMap<>();
    }
}
