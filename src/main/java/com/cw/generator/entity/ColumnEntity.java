package com.cw.generator.entity;

import lombok.Data;

/**
 * @Author C.W
 * @Date 2020/6/15 5:56 下午
 * @Description 列实体
 */
@Data
public class ColumnEntity {

    /**
     * 原始列名
     */
    private String columnSourceName;


    /**
     * 驼峰列名(首字母不转驼峰)
     */
    private String columnCamelCaseName;


    /**
     * 驼峰列名(首字母也转)
     */
    private String columnAllCamelCaseName;


    /**
     * 数据类型
     */
    private String dataType;


    /**
     * 列注释
     */
    private String comment;


}
