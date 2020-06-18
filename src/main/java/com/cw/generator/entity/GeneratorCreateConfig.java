package com.cw.generator.entity;

import com.cw.generator.utils.StringUtil;
import lombok.Data;

import java.util.List;

/**
 * @Author C.W
 * @Date 2020/6/15 5:50 下午
 * @Description 代码生成器配置
 */
@Data
public class GeneratorCreateConfig {

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
     * 数据库名
     */
    private String dbName;


    /**
     * 表名
     */
    private String tableNames;


    /**
     * 表
     */
    List<TableEntity> tables;


    /**
     * 参数校验
     *
     * @return
     */
    public boolean validate() {
        return StringUtil.isNotBlank(this.controllerPackage)
                && StringUtil.isNotBlank(this.entityPackage)
                && StringUtil.isNotBlank(this.daoPackage)
                && StringUtil.isNotBlank(this.servicePackage)
                && StringUtil.isNotBlank(this.author)
                && camelCase != null
                && StringUtil.isNotBlank(this.dbName)
                && StringUtil.isNotBlank(this.tableNames);
    }

}
