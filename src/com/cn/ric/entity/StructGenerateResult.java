package com.cn.ric.entity;

import java.util.List;

/**
 * @author richen
 */
public class StructGenerateResult {
    public String error;

    public List<StructEntity> structEntityList;

    public StructGenerateResult(String error, List<StructEntity> structEntityList) {
        this.error = error;
        this.structEntityList = structEntityList;
    }
}
