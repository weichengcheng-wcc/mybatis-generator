package com.cw.generator.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author C.W
 * @Date 2020/6/15 5:55 下午
 * @Description 表实体
 */
@Data
public class TableEntity {

    /**
     * controller包名
     */
    private String controllerPackage;

    /**
     * 实体类包名
     */
    private String entityPackage;

    /**
     * dao层包名
     */
    private String daoPackage;

    /**
     * service包名
     */
    private String servicePackage;

    /**
     * 作者
     */
    private String author;

    /**
     * 属性是否转驼峰
     */
    private Boolean camelCase;


    /**
     * 原始表名
     */
    private String tableSourceName;


    /**
     * 驼峰命名表名(首字母未大写)
     */
    private String tableCamelCaseName;


    /**
     * 驼峰命名表名(首字母大写)
     */
    private String tableAllCamelCaseName;


    /**
     * 注释
     */
    private String comment;


    /**
     * 列
     */
    private List<ColumnEntity> columnEntityList;


    /**
     * 所属数据库
     */
    private String dbName;


    /**
     * 存储引擎
     */
    private String engin;


    /**
     * 数据量
     */
    private Integer dataCount;


    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;


    /**
     * 注释中使用的时间
     */
    private String time;


    /**
     * 序列化id
     */
    private String serialVersionUID;


    /**
     * 需要导入的包
     */
    private List<String> imports;

}
