package ${servicePackage};

import ${entityPackage}.${tableAllCamelCaseName}Dto;
import ${entityPackage}.${tableAllCamelCaseName}Vo;

import java.util.Map;

/**
 * @Author ${author}
 * @Date ${time}
 * @Description ${comment}
 */
public interface ${tableAllCamelCaseName}Service {


    /**
     * 列表查询
     *
     * @param ${tableCamelCaseName}Dto
     * @return
     */
    Map<String, Object> list(${tableAllCamelCaseName}Dto ${tableCamelCaseName}Dto);


    /**
     * 查看详情
     *
     * @param id
     * @return
     */
    ${tableAllCamelCaseName}Vo detail(Long id);


    /**
     * 新增
     *
     * @param ${tableCamelCaseName}Dto
     * @return
     */
    Object add(${tableAllCamelCaseName}Dto ${tableCamelCaseName}Dto);


    /**
     * 批量新增
     *
     * @param ${tableCamelCaseName}Dtos
     * @return
     */
    Object batchAdd(${tableAllCamelCaseName}Dto[] ${tableCamelCaseName}Dtos);


    /**
     * 删除
     *
     * @param id
     * @return
     */
    Boolean delete(Long id);


    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    Boolean batchDelete(Long[] ids);


    /**
     * 修改
     *
     * @param ${tableCamelCaseName}Dto
     * @return
     */
    Object update(${tableAllCamelCaseName}Dto ${tableCamelCaseName}Dto);


}
