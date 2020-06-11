package com.cw.generator.controller;

import com.alibaba.fastjson.JSONArray;
import com.cw.generator.entity.CreateConfig;
import com.cw.generator.entity.Table;
import com.cw.generator.service.DataService;
import freemarker.template.TemplateException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author weiChengCheng
 * @Date 2020-05-20 19:12
 * @Description 数据
 */
@RestController
@RequestMapping("data")
public class DataController {

    @Resource
    private DataService dataService;


    /**
     * @Author weiChengCheng
     * @Date 2020-05-20 23:01
     * @Description 获取表
     */
    @GetMapping("getTables")
    public Map<String, Object> getTables(String table, String db, Integer start, Integer size) {
        Map<String, Object> defaultMap = new HashMap<>(2);
        defaultMap.put("data", null);
        defaultMap.put("count", 0);
        if (db == null || db.equals("") || start == null || size == null) {
            return defaultMap;
        }
        return dataService.getTables(table, db, start, size);
    }


    /**
     * @Author weiChengCheng
     * @Date 2020-05-20 23:01
     * @Description 获取数据库
     */
    @GetMapping("getDatabases")
    public List<String> getDatabases() {
        return dataService.getDatabases();
    }


    /**
     * @Author weiChengCheng
     * @Date 2020-05-22 08:53
     * @Description 导出生成的代码
     */
    @PostMapping("export")
    public String export(CreateConfig createConfig) throws IOException, TemplateException {
        Table[] tablesTem = new Table[0];
        tablesTem = JSONArray.parseArray(createConfig.getTables(), Table.class).toArray(tablesTem);

        return dataService.export(createConfig, tablesTem);
    }


    /**
     * @Author weiChengCheng
     * @Date 2020-05-24 14:16
     * @Description 下载文件
     */
    @GetMapping("download")
    public void download(String path, HttpServletResponse response) throws IOException {
        dataService.download(path, response);
    }

}
