package com.cw.generator.service;

import com.cw.generator.dao.DataDao;
import com.cw.generator.entity.Column;
import com.cw.generator.entity.CreateConfig;
import com.cw.generator.entity.Table;
import com.cw.generator.utils.ZipUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

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
@Service
public class DataService {

    @Resource
    private DataDao dataDao;


    @Value("${code.export.path}")
    public String path;

    @Value("${spring.data.type.mapping}")
    private String mapping;

    private static Map<String, String> mappings = null;

    /**
     * @Author weiChengCheng
     * @Date 2020-05-20 10:28
     * @Description 获取表列表
     */
    public Map<String, Object> getTables(String table, String db, Integer start, Integer size) {
        Map<String, Object> result = new HashMap<>(2);
        result.put("data", dataDao.getTables(table, db, start, size));
        result.put("count", dataDao.countTables(table, db));

        return result;
    }


    /**
     * @Author weiChengCheng
     * @Date 2020-05-20 23:02
     * @Description 获取数据库
     */
    public List<String> getDatabases() {
        return dataDao.getDatabases();
    }


    /**
     * @Author weiChengCheng
     * @Date 2020-05-22 08:55
     * @Description 导出
     */
    public String export(CreateConfig createConfig, Table[] tables) throws IOException, TemplateException {
        // 数据校验
        if (tables == null || tables.length == 0) {
            return null;
        }

        // 清空历史数据
        File file = new File(path);
        if (file.exists()) {
            deleteDir(path);
        }

        String dbName = null;
        List<String> tableNames = new ArrayList<>(tables.length);
        List<Table> tableList = new ArrayList<>(tableNames.size());
        for (Table table : tables) {
            if (table == null || table.getTableName() == null) {
                continue;
            }
            tableNames.add(table.getTableName());
            dbName = table.getDbName();
            tableList.add(table);
        }
        // 查询数据库相关列
        List<Column> columnList = dataDao.getColumns(tableNames, dbName);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 封装数据
        if (columnList != null && columnList.size() != 0) {
            for (Table table : tableList) {
                // 设置camel
                table.setTableNameCamel(getCamelString(table.getTableName(), true));
                List<Column> columnListTem = new ArrayList<>();
                Set<String> imports = new HashSet<>();
                for (Column column : columnList) {
                    if (table.getDbName().equals(column.getDbName()) && table.getTableName().equals(column.getTableName())) {
                        column.setNameCamel(getCamelString(column.getName(), false));
                        column.setNameCamelAll(getCamelString(column.getName(), true));
                        columnListTem.add(column);
                        // 设置映射类型
                        String mapType = setMapping(column);
                        if (mapType != null) {
                            column.setType(mapType.substring(mapType.lastIndexOf(".") + 1, mapType.length()));
                            // 设置导入包
                            if (!mapType.startsWith("java.lang.")) {
                                imports.add(mapType);
                            }
                        }
                    }
                }
                table.setColumnList(columnListTem);
                table.setImports(imports);
                table.setPrefixPackage(table.getTableName().substring(table.getTableName().lastIndexOf("_") + 1));
                table.setControllerPackage(createConfig.getControllerPackage() + "." + table.getPrefixPackage() + ".controller");
                table.setServicePackage(createConfig.getServicePackage() + "." + table.getPrefixPackage() + ".service");
                table.setDaoPackage(createConfig.getDaoPackage() + "." + table.getPrefixPackage() + ".dao");
                table.setEntityPackage(createConfig.getEntityPackage() + "." + table.getPrefixPackage());
                table.setAuthor(createConfig.getAuthor());
                table.setDate(simpleDateFormat.format(new Date()));
                table.setBoPackage(table.getEntityPackage() + "." + table.getTableNameCamel() + "DO");


                // 生成文件
                createCode(table);
            }

            // 压缩文件并写入
            file = new File(path + "/" + tableList.get(0).getDbName() + ".zip");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
            OutputStream outputStream = new FileOutputStream(file);
            ZipUtils.toZip(path + "/" + tableList.get(0).getDbName() + "/", outputStream, true);
            outputStream.close();

            return file.getAbsolutePath();
        }

        return null;

    }


    /**
     * @Author weiChengCheng
     * @Date 2020-05-22 11:28
     * @Description 生成代码
     */
    private void createCode(Table table) throws IOException, TemplateException {
        Configuration configuration = new Configuration(Configuration.getVersion());
        configuration.setClassForTemplateLoading(this.getClass(),"/templates/");
        // 导出pojo
        createPojo(configuration, table);
        // 导出mapper
        createMapper(configuration, table);
        // 导出dao
        createDao(configuration, table);
        // 导出service
        createService(configuration, table);
        // 导出controller
        createController(configuration, table);
        // 导出vue
        createVue(configuration, table);
        // 创建pageresult
        createPage(configuration,table);
    }


    /**
     * @Author C.W
     * @Date 2020/5/27 10:45 上午
     * @Description 创建pagerresult
     */
    private void createPage(Configuration configuration, Table table) throws IOException, TemplateException {
        // list列表
        Template template = configuration.getTemplate("PageResult.ftl","UTF-8");
        File file = new File(path + table.getDbName() + "/" + table.getTableNameCamel() + "/pojo/" + "PageResult.java");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        file.createNewFile();
        Writer writer = new FileWriter(file);

        template.process(table, writer);

        writer.close();

        // 新增
        template = configuration.getTemplate("PageResultEnum.ftl","UTF-8");
        file = new File(path + table.getDbName() + "/" + table.getTableNameCamel() + "/pojo/" + "PageResultEnum.java");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        file.createNewFile();
        writer = new FileWriter(file);

        template.process(table, writer);

        writer.close();
    }


    /**
     * @Author weiChengCheng
     * @Date 2020-05-24 13:30
     * @Description 创建vue
     */
    private void createVue(Configuration configuration, Table table) throws IOException, TemplateException {
        // list列表
        Template template = configuration.getTemplate("vue-list.ftl","UTF-8");
        File file = new File(path + table.getDbName() + "/" + table.getTableNameCamel() + "/vue/index.vue");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        file.createNewFile();
        Writer writer = new FileWriter(file);

        template.process(table, writer);

        writer.close();

        // 新增
        template = configuration.getTemplate("vue-add.ftl","UTF-8");
        file = new File(path + table.getDbName() + "/" + table.getTableNameCamel() + "/vue/add.vue");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        file.createNewFile();
        writer = new FileWriter(file);

        template.process(table, writer);

        writer.close();

        // 编辑
        template = configuration.getTemplate("vue-edit.ftl","UTF-8");
        file = new File(path + table.getDbName() + "/" + table.getTableNameCamel() + "/vue/edit.vue");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        file.createNewFile();
        writer = new FileWriter(file);

        template.process(table, writer);

        writer.close();

        // 详情
        template = configuration.getTemplate("vue-detail.ftl","UTF-8");
        file = new File(path + table.getDbName() + "/" + table.getTableNameCamel() + "/vue/detail.vue");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        file.createNewFile();
        writer = new FileWriter(file);

        template.process(table, writer);

        writer.close();
    }


    /**
     * @Author weiChengCheng
     * @Date 2020-05-24 13:30
     * @Description 创建controller
     */
    private void createController(Configuration configuration, Table table) throws IOException, TemplateException {
        Template template = configuration.getTemplate("Controller.ftl","UTF-8");
        File file = new File(path + table.getDbName() + "/" + table.getTableNameCamel() + "/controller/" + table.getTableNameCamel() + "Controller.java");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        file.createNewFile();
        Writer writer = new FileWriter(file);

        template.process(table, writer);

        writer.close();
    }


    /**
     * @Author weiChengCheng
     * @Date 2020-05-24 13:25
     * @Description 创建service
     */
    private void createService(Configuration configuration, Table table) throws IOException, TemplateException {
        Template template = configuration.getTemplate("Service.ftl","UTF-8");
        File file = new File(path + table.getDbName() + "/" + table.getTableNameCamel() + "/service/" + table.getTableNameCamel() + "Service.java");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        file.createNewFile();
        Writer writer = new FileWriter(file);

        template.process(table, writer);

        writer.close();

        template = configuration.getTemplate("ServiceImpl.ftl","UTF-8");
        file = new File(path + table.getDbName() + "/" + table.getTableNameCamel() + "/service/impl/" + table.getTableNameCamel() + "ServiceImpl.java");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        file.createNewFile();
        writer = new FileWriter(file);

        template.process(table, writer);

        writer.close();
    }


    /**
     * @Author weiChengCheng
     * @Date 2020-05-24 13:23
     * @Description 生成dao
     */
    private void createDao(Configuration configuration, Table table) throws IOException, TemplateException {
        Template template = configuration.getTemplate("Dao.ftl","UTF-8");
        File file = new File(path + table.getDbName() + "/" + table.getTableNameCamel() + "/dao/" + table.getTableNameCamel() + "Dao.java");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        file.createNewFile();
        Writer writer = new FileWriter(file);

        template.process(table, writer);

        writer.close();
    }


    /**
     * @Author weiChengCheng
     * @Date 2020-05-24 13:21
     * @Description 生成mapper
     */
    private void createMapper(Configuration configuration, Table table) throws IOException, TemplateException {
        Template template = configuration.getTemplate("Mapper.ftl","UTF-8");
        File file = new File(path + table.getDbName() + "/" + table.getTableNameCamel() + "/mapper/" + table.getTableNameCamel() + "Dao.xml");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        file.createNewFile();
        Writer writer = new FileWriter(file);

        template.process(table, writer);

        writer.close();
    }


    /**
     * @Author weiChengCheng
     * @Date 2020-05-22 12:47
     * @Description 导出pojo
     */
    private void createPojo(Configuration configuration, Table table) throws IOException, TemplateException {
        // 导出do
        Template template = configuration.getTemplate("PojoDo.ftl","UTF-8");
        File file = new File(path + table.getDbName() + "/" + table.getTableNameCamel() + "/pojo/" + table.getTableNameCamel() + "DO.java");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        file.createNewFile();
        Writer writer = new FileWriter(file);

        Random random = new Random();

        table.setSerialVersionUID(random.nextLong() + "");

        template.process(table, writer);

        writer.close();

        // 导出dto
        template = configuration.getTemplate("PojoDto.ftl","UTF-8");
        file = new File(path + table.getDbName() + "/" + table.getTableNameCamel() + "/pojo/" + table.getTableNameCamel() + "DTO.java");
        file.createNewFile();
        writer = new FileWriter(file);

        table.setSerialVersionUID(random.nextLong() + "");

        template.process(table, writer);

        writer.close();
        // 导出vo
        template = configuration.getTemplate("PojoVo.ftl","UTF-8");
        file = new File(path + table.getDbName() + "/" + table.getTableNameCamel() + "/pojo/" + table.getTableNameCamel() + "VO.java");
        file.createNewFile();
        writer = new FileWriter(file);

        table.setSerialVersionUID(random.nextLong() + "");

        template.process(table, writer);

        writer.close();
    }


    /**
     * @Author weiChengCheng
     * @Date 2020-05-22 23:35
     * @Description 下划线转驼峰
     */
    private String getCamelString(String text, boolean firstCamel) {
        StringBuffer result = new StringBuffer();

        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '_') {
                continue;
            }
            if (i > 0 && i < text.length() && text.charAt(i - 1) == '_') {
                result.append((text.charAt(i) + "").toUpperCase());
            } else {
                if (i == 0 && firstCamel) {
                    result.append((text.charAt(i) + "").toUpperCase());
                } else {
                    result.append(text.charAt(i));
                }
            }
        }

        return result.toString();
    }


    /**
     * @Author weiChengCheng
     * @Date 2020-05-23 20:51
     * @Description 获取字段映射的类型
     */
    private String setMapping(Column column) {
        if (mappings == null) {
            String[] mapArray = mapping.split(",");
            mappings = new HashMap<>(mapArray.length);
            for (String s : mapArray) {
                String[] map = s.split("=");
                if (map.length == 2) {
                    mappings.put(map[0].trim(), map[1].trim());
                }
            }

            if (mappings.keySet().size() == 0) {
                mappings = null;
            }
        }

        if (mappings != null) {
            return mappings.get(column.getType());
        }

        return null;
    }


    /**
     * @Author weiChengCheng
     * @Date 2020-05-24 14:16
     * @Description 下载
     */
    public void download(String path, HttpServletResponse response) throws IOException {
        File file = new File(path);

        if (file.exists()) {
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=" + path.substring(path.lastIndexOf("/") + 1, path.length()) + "");
            response.addHeader("Content-Length", "" + file.length());
            response.setContentType("application/octet-stream; charset=UTF-8");

            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

            byte[] buffer = new byte[2048];
            Integer length = -1;

            OutputStream outputStream = response.getOutputStream();

            while ((length = bufferedInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.flush();
            outputStream.close();
        }
    }


    /**
     * @Author weiChengCheng
     * @Date 2020-05-24 16:00
     * @Description 删除文件夹
     */
    private void deleteDir(String dirPath) {
        File file = new File(dirPath);
        if (file.isFile()) {
            file.delete();
        } else {
            File[] files = file.listFiles();
            if (files == null) {
                file.delete();
            } else {
                for (int i = 0; i < files.length; i++) {
                    deleteDir(files[i].getAbsolutePath());
                }
                file.delete();
            }
        }
    }

}
