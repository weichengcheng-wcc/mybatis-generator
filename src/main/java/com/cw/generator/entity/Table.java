package com.cw.generator.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @Author weiChengCheng
 * @Date 2020-05-20 10:29
 * @Description 表
 */
@Data
public class Table implements Serializable {

    private String tableName;

    private String tableNameCamel;

    private String prefixPackage;

    private String tableComment;

    private String dbName;

    private String engine;

    private Date createTime;

    private String charSet;

    private Set<String> imports;

    private List<Column> columnList;

    private String entityPackage;

    private String daoPackage;

    private String servicePackage;

    private String controllerPackage;

    private String serialVersionUID;

    private String date;

    private String author;

    private String boPackage;

    public Table(){

    }

}
