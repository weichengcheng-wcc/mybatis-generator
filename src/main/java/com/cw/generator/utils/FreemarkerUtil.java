package com.cw.generator.utils;

import freemarker.template.Configuration;

import java.io.File;
import java.io.IOException;

/**
 * @Author C.W
 * @Date 2020/6/16 2:08 下午
 * @Description freemarker工具
 */
public class FreemarkerUtil {


    /**
     * 获取配置（classpath）
     *
     * @return
     */
    public static Configuration getConfig() {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);

        configuration.setClassForTemplateLoading(FreemarkerUtil.class, "/templates/");
        configuration.setDefaultEncoding("UTF-8");

        return configuration;
    }


    /**
     * 获取配置(file)
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static Configuration getConfig(String path) throws IOException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);

        configuration.setDirectoryForTemplateLoading(new File(path));
        configuration.setDefaultEncoding("UTF-8");

        return configuration;
    }


}
