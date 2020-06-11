package ${servicePackage}.impl;

import ${entityPackage}.${tableNameCamel}DTO;
import ${entityPackage}.${tableNameCamel}DO;
import ${servicePackage}.${tableNameCamel}Service;
import ${daoPackage}.${tableNameCamel}Dao;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;

/**
* @Author ${author}
* @Date ${date}
* @Description ${((tableComment!'')?length>0)?string((tableComment!''),tableName)}
*/
@Service("${tableNameCamel}Service")
public class ${tableNameCamel}ServiceImpl implements ${tableNameCamel}Service {

    @Resource
    private ${tableNameCamel}Dao dao;

    /**
     * @Author ${author}
     * @Date ${date}
     * @Description 分页查询
     */
    @Override
    public Object list(${tableNameCamel}DTO dto){
        Map<String,Object> result = new HashMap<>(2);

        // 查询数量
        Long count = dao.countPager(dto);
        if(count == null || count == 0){
            result.put("data",null);
        }else {
            result.put("data",dao.selectPager(dto));
        }
        result.put("count", count);

        return result;
    }


    /**
     * @Author ${author}
     * @Date ${date}
     * @Description 新增
     */
    @Override
    public Object insert(${tableNameCamel}DTO dto){
        // dto转DO
        ${tableNameCamel}DO DO = dtoToDO(dto);

        return dao.insert(DO) > 0;
    }


    /**
     * @Author ${author}
     * @Date ${date}
     * @Description 删除
     */
    @Override
    public Object delete(Long id){
        return dao.delete(id) > 0;
    }


    /**
     * @Author ${author}
     * @Date ${date}
     * @Description 批量删除
     */
    @Override
    public Object deleteBatch(Long[] ids){
        List<Long> idList = new ArrayList<>(ids.length);

        for(Long id : ids){
            idList.add(id);
        }

        return dao.deleteBatch(idList) > 0;
    }


    /**
     * @Author ${author}
     * @Date ${date}
     * @Description 修改
     */
    @Override
    public Object update(${tableNameCamel}DTO dto){
        // dto转DO
        ${tableNameCamel}DO DO = dtoToDO(dto);

        return dao.update(DO) > 0;
    }


    /**
     * @Author ${author}
     * @Date ${date}
     * @Description 详情
     */
    @Override
    public Object detail(Long id){
        return dao.selectById(id);
    }


    /**
     * @Author ${author}
     * @Date ${date}
     * @Description DTO对象转DO对象
     */
    private ${tableNameCamel}DO dtoToDO(${tableNameCamel}DTO dto){
        ${tableNameCamel}DO tem = new ${tableNameCamel}DO();

        <#list columnList as column>
        tem.set${column.nameCamelAll}(dto.get${column.nameCamelAll}());
        </#list>

        return tem;
    }

}