package com.cw.generator.entity;

import lombok.Data;

/**
 * @Author C.W
 * @Date 2020/6/15 10:39 下午
 * @Description 表查询条件
 */
@Data
public class TableQueryEntity {

    /**
     * 数据库名
     */
    private String dbName;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 注释
     */
    private String comment;


    /**
     * 分页开始
     */
    private Integer start;


    /**
     * 每页大小
     */
    private Integer size;

}
