package com.cw.generator.dao;

import com.cw.generator.entity.Table;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.cw.generator.entity.Column;

import java.util.List;

/**
 * @Author weiChengCheng
 * @Date 2020-05-20 09:03
 * @Description MySQL
 */
@Mapper
public interface DataDao {

    /**
     * @Author weiChengCheng
     * @Date 2020-05-21 18:54
     * @Description 获取表数量
     */
    Integer countTables(@Param("table") String table, @Param("db") String db);

    /**
     * @Author weiChengCheng
     * @Date 2020-05-20 09:03
     * @Description 获取表
     */
    List<Table> getTables(@Param("table") String table, @Param("db") String db, @Param("start") Integer start, @Param("size") Integer size);


    /**
     * @Author weiChengCheng
     * @Date 2020-05-20 23:03
     * @Description 获取数据库
     */
    @Select("select distinct TABLE_SCHEMA from information_schema.TABLES order by TABLE_SCHEMA asc")
    List<String> getDatabases();


    /**
     * @Author weiChengCheng
     * @Date 2020-05-22 08:56
     * @Description 查询列
     */
    List<Column> getColumns(@Param("tables")List<String> tables,@Param("db")String db);


}
