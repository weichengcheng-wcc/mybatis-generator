package com.cw.generator.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author weiChengCheng
 * @Date 2020-05-22 08:57
 * @Description 列
 */
@Data
public class Column implements Serializable {

    private String dbName;

    private String tableName;

    private String name;

    private String nameCamel;

    private String nameCamelAll;

    private String type;

    private String comment;

    public Column(){

    }

}
