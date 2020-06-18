package com.cw.generator.dao;

import com.cw.generator.entity.ColumnEntity;
import com.cw.generator.entity.TableEntity;
import com.cw.generator.entity.TableQueryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author weiChengCheng
 * @Date 2020-05-20 09:03
 * @Description MySQL
 */
@Mapper
public interface GeneratorDao {


    /**
     * 获取所有的数据库信息
     *
     * @return
     */
    @Select("select distinct TABLE_SCHEMA from information_schema.TABLES")
    List<String> getAllDbs();


    /**
     * 查询表的数量
     *
     * @param tableQueryEntity
     * @return
     */
    Integer countTable(TableQueryEntity tableQueryEntity);


    /**
     * 获取表信息
     *
     * @param tableQueryEntity
     * @return
     */
    List<TableEntity> getTables(TableQueryEntity tableQueryEntity);


    /**
     * 根据table信息获取列信息
     *
     * @param tableEntity
     * @return
     */
    List<ColumnEntity> getColumnsByTable(TableEntity tableEntity);


    /**
     * 根据数据库和表名来获取表信息
     *
     * @param dbName
     * @param tableNames
     * @return
     */
    List<TableEntity> getTableByDbAndName(@Param("dbName") String dbName, @Param("tableNames") String[] tableNames);

}
