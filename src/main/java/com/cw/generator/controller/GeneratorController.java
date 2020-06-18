package com.cw.generator.controller;

import com.cw.generator.entity.GeneratorCreateConfig;
import com.cw.generator.entity.TableQueryEntity;
import com.cw.generator.service.GeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Author weiChengCheng
 * @Date 2020-05-20 19:12
 * @Description 数据
 */
@RestController
@RequestMapping("generator")
public class GeneratorController {

    @Autowired
    private GeneratorService generatorService;


    /**
     * 获取所有的数据库信息
     *
     * @return
     */
    @GetMapping("getAllDbs")
    public List<String> getAllDbs() {
        return generatorService.getAllDbs();
    }


    /**
     * 获取所有的表信息
     *
     * @return
     */
    @GetMapping("getAllTables")
    public Map<String, Object> getAllTables(TableQueryEntity tableQueryEntity) {
        return generatorService.getAllTables(tableQueryEntity);
    }


    /**
     * 生成代码
     *
     * @param generatorCreateConfig
     */
    @GetMapping("create")
    public String download(GeneratorCreateConfig generatorCreateConfig) throws IOException {
        // 校验数据
        if (generatorCreateConfig.validate()) {
            return generatorService.create(generatorCreateConfig);
        } else {
            return null;
        }
    }


    /**
     * 下载
     *
     * @param path
     * @param response
     */
    @GetMapping("download")
    public void download(String path, HttpServletResponse response) throws IOException {
        generatorService.download(path, response);
    }

}
