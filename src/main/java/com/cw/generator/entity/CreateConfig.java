package com.cw.generator.entity;

import lombok.Data;

/**
 * @Author weiChengCheng
 * @Date 2020-05-23 23:00
 * @Description 代码生成配置
 */
@Data
public class CreateConfig {

    private String entityPackage;

    private String daoPackage;

    private String servicePackage;

    private String controllerPackage;

    private String tables;

    private String author;

}
