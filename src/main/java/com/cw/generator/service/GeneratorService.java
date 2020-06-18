package com.cw.generator.service;

import com.cw.generator.dao.GeneratorDao;
import com.cw.generator.entity.ColumnEntity;
import com.cw.generator.entity.GeneratorCreateConfig;
import com.cw.generator.entity.TableEntity;
import com.cw.generator.entity.TableQueryEntity;
import com.cw.generator.utils.FileUtil;
import com.cw.generator.utils.FreemarkerUtil;
import com.cw.generator.utils.StringUtil;
import com.cw.generator.utils.ZipUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author weiChengCheng
 * @Date 2020-05-20 09:03
 * @Description 业务
 */
@Slf4j
@Service
public class GeneratorService {

    @Resource
    private GeneratorDao generatorDao;

    @Value("${freemarker.template.file.dir}")
    private String freemarkerPath;

    @Value("${spring.data.type.mapping}")
    private String dataTypeMappingStr;

    /**
     * 获取所有的数据库信息
     *
     * @return
     */
    public List<String> getAllDbs() {
        return generatorDao.getAllDbs();
    }


    /**
     * 获取所有的表信息
     *
     * @return
     */
    public Map<String, Object> getAllTables(TableQueryEntity tableQueryEntity) {
        Map<String, Object> result = new HashMap<>(2);

        result.put("count", generatorDao.countTable(tableQueryEntity));
        result.put("data", generatorDao.getTables(tableQueryEntity));

        return result;
    }


    /**
     * 下载代码
     *
     * @param generatorCreateConfig
     */
    public String create(GeneratorCreateConfig generatorCreateConfig) throws IOException {
        // 获取freemarker配置
        Configuration configuration = FreemarkerUtil.getConfig();

        // 清空上一次的内容
        FileUtil.deleteFile(freemarkerPath);

        // 获取数据库信息
        List<TableEntity> tableEntityList = generatorDao.getTableByDbAndName(generatorCreateConfig.getDbName(), generatorCreateConfig.getTableNames().split(","));

        generatorCreateConfig.setTables(tableEntityList);

        String dbName = generatorCreateConfig.getTables().get(0).getDbName();

        String path = freemarkerPath + "/" + dbName;

        // 遍历需要生成的表来生成代码
        generatorCreateConfig.getTables().stream().forEach(table -> {
            initTable(table, generatorCreateConfig);
            // 生成代码
            createCode(path, table, configuration);
        });


        // 压缩文件
        File file = ZipUtils.toZip(path, path + ".zip", true);

        return file.getAbsolutePath();
    }


    /**
     * 生成代码
     *
     * @param path
     * @param table
     * @param configuration
     */
    private void createCode(String path, TableEntity table, Configuration configuration) {
        // controller
        processTemplate("/controller/controller.java",
                path + "/" + table.getTableCamelCaseName() + "/controller/" + table.getTableAllCamelCaseName() + "Controller.java",
                configuration, table);
        // service
        processTemplate("/service/service.java",
                path + "/" + table.getTableCamelCaseName() + "/service/" + table.getTableAllCamelCaseName() + "Service.java",
                configuration, table);
        processTemplate("/service/impl/serviceImpl.java",
                path + "/" + table.getTableCamelCaseName() + "/service/impl/" + table.getTableAllCamelCaseName() + "ServiceImpl.java",
                configuration, table);
        // dao
        processTemplate("/dao/dao.java",
                path + "/" + table.getTableCamelCaseName() + "/dao/" + table.getTableAllCamelCaseName() + "Dao.java",
                configuration, table);
        // entity
        processTemplate("/entity/bo.java",
                path + "/" + table.getTableCamelCaseName() + "/entity/" + table.getTableAllCamelCaseName() + "Bo.java",
                configuration, table);
        processTemplate("/entity/dto.java",
                path + "/" + table.getTableCamelCaseName() + "/entity/" + table.getTableAllCamelCaseName() + "Dto.java",
                configuration, table);
        processTemplate("/entity/vo.java",
                path + "/" + table.getTableCamelCaseName() + "/entity/" + table.getTableAllCamelCaseName() + "Vo.java",
                configuration, table);
        // mapper
        processTemplate("/mapper/dao.xml",
                path + "/" + table.getTableCamelCaseName() + "/mapper/" + table.getTableAllCamelCaseName() + "Dao.xml",
                configuration, table);
        // vue
        processTemplate("/vue/index.vue",
                path + "/" + table.getTableCamelCaseName() + "/vue/index.vue",
                configuration, table);
        processTemplate("/vue/add.vue",
                path + "/" + table.getTableCamelCaseName() + "/vue/add.vue",
                configuration, table);
        processTemplate("/vue/edit.vue",
                path + "/" + table.getTableCamelCaseName() + "/vue/edit.vue",
                configuration, table);
        processTemplate("/vue/detail.vue",
                path + "/" + table.getTableCamelCaseName() + "/vue/detail.vue",
                configuration, table);
    }


    /**
     * 处理模板
     *
     * @param templatePath
     * @param path
     * @param configuration
     * @param tableEntity
     */
    private void processTemplate(String templatePath, String path, Configuration configuration, TableEntity tableEntity) {
        refreshTableData(tableEntity);
        try {
            Template template = configuration.getTemplate(templatePath);
            FileUtil.createFile(path);
            Writer writer = new FileWriter(path);

            template.process(tableEntity, writer);

            writer.close();
        } catch (IOException e) {
            log.error("获取模板异常");
        } catch (TemplateException e) {
            log.error("模板处理异常");
        }
    }


    /**
     * 刷新表的一些需要刷新的信息
     *
     * @param tableEntity
     */
    private void refreshTableData(TableEntity tableEntity) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        tableEntity.setTime(simpleDateFormat.format(new Date()));
        tableEntity.setSerialVersionUID(String.valueOf(System.currentTimeMillis() + new Random().nextInt()));
    }


    /**
     * 填充table信息
     *
     * @param tableEntity
     */
    private void initTable(TableEntity tableEntity, GeneratorCreateConfig generatorCreateConfig) {
        // 填充列信息
        getTableColumn(tableEntity);
        // 设置表名camel
        tableEntity.setTableCamelCaseName(StringUtil.camelCase(tableEntity.getTableSourceName(), false));
        tableEntity.setTableAllCamelCaseName(StringUtil.camelCase(tableEntity.getTableSourceName(), true));
        // 设置需要导入的包
        setTableImport(tableEntity);
        // 设置包名、作者等
        tableEntity.setControllerPackage(generatorCreateConfig.getControllerPackage());
        tableEntity.setDaoPackage(generatorCreateConfig.getDaoPackage());
        tableEntity.setEntityPackage(generatorCreateConfig.getEntityPackage());
        tableEntity.setServicePackage(generatorCreateConfig.getServicePackage());
        tableEntity.setAuthor(generatorCreateConfig.getAuthor());
        tableEntity.setCamelCase(generatorCreateConfig.getCamelCase());
    }


    /**
     * 设置table的需要导入的包
     *
     * @param tableEntity
     */
    private void setTableImport(TableEntity tableEntity) {
        Set<String> imports = new HashSet<>();

        if (StringUtil.isNotBlank(dataTypeMappingStr)) {
            String[] mappings = dataTypeMappingStr.split(",");
            for (String mapping : mappings) {
                mapping = mapping.trim();
                String[] mapStr = mapping.split("=");
                if (mapStr.length == 2) {
                    for (ColumnEntity columnEntity : tableEntity.getColumnEntityList()) {
                        if (columnEntity.getDataType().toLowerCase().trim().equals(mapStr[0].trim().toLowerCase())) {
                            imports.add(mapStr[1].trim());
                        }
                    }
                }
            }
        }

        tableEntity.setImports(new ArrayList<>(imports));
    }


    /**
     * 填充表的列信息
     *
     * @param tableEntity
     */
    private void getTableColumn(TableEntity tableEntity) {
        List<ColumnEntity> columnEntityList = generatorDao.getColumnsByTable(tableEntity);

        if (CollectionUtils.isNotEmpty(columnEntityList)) {
            columnEntityList.stream().forEach(columnEntity -> {
                columnEntity.setColumnCamelCaseName(StringUtil.camelCase(columnEntity.getColumnSourceName(), false));
                columnEntity.setColumnAllCamelCaseName(StringUtil.camelCase(columnEntity.getColumnSourceName(), true));
            });
        }

        tableEntity.setColumnEntityList(columnEntityList);
    }


    /**
     * 下载
     *
     * @param path
     * @param response
     */
    public void download(String path, HttpServletResponse response) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            FileUtil.createFile(path);
        }
        String name = path.substring(path.lastIndexOf("/") + 1);
        // 下载文件
        response.reset();
        response.setHeader("content-disposition", "attachment;fileName=" + name);

        OutputStream outputStream = response.getOutputStream();

        InputStream inputStream = new FileInputStream(file);
        byte[] buffer = new byte[4096];
        Integer length = -1;
        while ((length = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }

        inputStream.close();
        outputStream.close();
        response.flushBuffer();
    }


}
