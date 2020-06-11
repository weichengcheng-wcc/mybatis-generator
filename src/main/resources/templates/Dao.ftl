package ${daoPackage}.dao;

import org.apache.ibatis.annotations.Mapper;
import ${entityPackage}.${tableNameCamel}DTO;
import ${entityPackage}.${tableNameCamel}DO;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @Author ${author}
 * @Date ${date}
 * @Description ${((tableComment!'')?length>0)?string((tableComment!''),tableName)}
 */
@Mapper
public interface ${tableNameCamel}Dao {

    /**
     * @Author ${author}
     * @Date ${date}
     * @Description 根据id查询
     */
    ${tableNameCamel}DO selectById(@Param("id") Long id);


    /**
     * @Author ${author}
     * @Date ${date}
     * @Description 查询一个
     */
    ${tableNameCamel}DO selectOne(${tableNameCamel}DO tableDo);


    /**
     * @Author ${author}
     * @Date ${date}
     * @Description 查询列表
     */
    List<${tableNameCamel}DO> selectList(${tableNameCamel}DO tableDo);


    /**
     * @Author ${author}
     * @Date ${date}
     * @Description 分页
     */
    List<${tableNameCamel}DO> selectPager(${tableNameCamel}DTO dto);


    /**
     * @Author ${author}
     * @Date ${date}
     * @Description 分页
     */
    List<${tableNameCamel}DO> selectAll();


    /**
     * @Author ${author}
     * @Date ${date}
     * @Description 查询所有数量
     */
    Long countAll();


    /**
     * @Author ${author}
     * @Date ${date}
     * @Description 查询分页数量
     */
    Long countPager(${tableNameCamel}DTO dto);


    /**
     * @Author ${author}
     * @Date ${date}
     * @Description 新增
     */
    Integer insert(${tableNameCamel}DO tableDo);


    /**
     * @Author ${author}
     * @Date ${date}
     * @Description 根据id删除
     */
    Integer delete(Long id);


    /**
     * @Author ${author}
     * @Date ${date}
     * @Description 根据id删除
     */
    Integer deleteBatch(@Param("ids")List<Long> ids);


    /**
     * @Author ${author}
     * @Date ${date}
     * @Description 更新
     */
    Integer update(${tableNameCamel}DO tableDo);

}