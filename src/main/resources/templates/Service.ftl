package ${servicePackage};

import ${entityPackage}.${tableNameCamel}DTO;

/**
 * @Author ${author}
 * @Date ${date}
 * @Description ${((tableComment!'')?length>0)?string((tableComment!''),tableName)}
 */
public interface ${tableNameCamel}Service {

    /**
     * @Author ${author}
     * @Date ${date}
     * @Description 分页查询
     */
    Object list(${tableNameCamel}DTO dto);


    /**
     * @Author ${author}
     * @Date ${date}
     * @Description 新增
     */
    Object insert(${tableNameCamel}DTO dto);


    /**
     * @Author ${author}
     * @Date ${date}
     * @Description 删除
     */
    Object delete(Long id);


    /**
     * @Author ${author}
     * @Date ${date}
     * @Description 批量删除
     */
    Object deleteBatch(Long[] ids);


    /**
     * @Author ${author}
     * @Date ${date}
     * @Description 修改
     */
    Object update(${tableNameCamel}DTO dto);


    /**
     * @Author ${author}
     * @Date ${date}
     * @Description 详情
     */
    Object detail(Long id);

}