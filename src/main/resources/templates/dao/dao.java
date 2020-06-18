package ${daoPackage};

import ${entityPackage}.${tableAllCamelCaseName}Bo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author ${author}
 * @Date ${time}
 * @Description ${comment}
 */
@Mapper
public interface ${tableAllCamelCaseName}Dao {

    /**
     * [新增] 插入对象
     *
     * @param ${tableCamelCaseName}Bo
     * @return
     */
    Integer insert(${tableAllCamelCaseName}Bo ${tableCamelCaseName}Bo);


    /**
     * [新增] 批量新增
     *
     * @param ${tableCamelCaseName}BoList
     * @return
     */
    Integer batchInsert(@Param("boList") List<${tableAllCamelCaseName}Bo> ${tableCamelCaseName}BoList);


    /**
     * [删除] 根据id删除
     *
     * @param id
     * @return
     */
    Integer deleteById(@Param("id") Long id);


    /**
     * [删除] 根据id批量删除
     *
     * @param ids
     * @return
     */
    Integer batchDeleteByIdList(@Param("ids") List<Long> ids);


    /**
     * [删除] 根据id批量删除
     *
     * @param ids
     * @return
     */
    Integer batchDeleteByIdArray(@Param("ids") Long[] ids);


    /**
     * [修改] 根据id修改
     *
     * @param ${tableCamelCaseName}Bo
     * @return
     */
    Integer updateById(${tableAllCamelCaseName}Bo ${tableCamelCaseName}Bo);


    /**
     * [修改] 根据id批量修改
     *
     * @param ${tableCamelCaseName}Bos
     * @return
     */
    Integer batchUpdateById(@Param("bos") List<${tableAllCamelCaseName}Bo> ${tableCamelCaseName}Bos);


    /**
     * [修改] 主键不冲突则新增，主键冲突则更新
     *
     * @param ${tableCamelCaseName}Bo
     * @return
     */
    Integer insertOrUpdate(${tableAllCamelCaseName}Bo ${tableCamelCaseName}Bo);


    /**
     * [查询] 根据id查询bo
     *
     * @param id
     * @return
     */
    ${tableAllCamelCaseName}Bo getById(@Param("id") Long id);


    /**
     * [查询] 根据id列表查询bo列表
     *
     * @param ids
     * @return
     */
    List<${tableAllCamelCaseName}Bo> listByIdList(@Param("ids") List<Long> ids);


    /**
     * [查询] 根据id数组查询bo列表
     *
     * @param ids
     * @return
     */
    List<${tableAllCamelCaseName}Bo> listByIdArray(@Param("ids") Long[] ids);


    /**
     * [查询] 根据bo获取一个bo
     *
     * @param ${tableCamelCaseName}Bo
     * @return
     */
    ${tableAllCamelCaseName}Bo getByBo(${tableAllCamelCaseName}Bo ${tableCamelCaseName}Bo);


    /**
     * [查询] 根据bo获取bo列表
     *
     * @param ${tableCamelCaseName}Bo
     * @return
     */
    List<${tableAllCamelCaseName}Bo> listByBo(${tableAllCamelCaseName}Bo ${tableCamelCaseName}Bo);


}
