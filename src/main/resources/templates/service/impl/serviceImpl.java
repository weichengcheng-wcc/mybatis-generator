package ${servicePackage}.impl;

import ${daoPackage}.${tableAllCamelCaseName}Dao;
import ${entityPackage}.${tableAllCamelCaseName}Bo;
import ${entityPackage}.${tableAllCamelCaseName}Dto;
import ${entityPackage}.${tableAllCamelCaseName}Vo;
import ${servicePackage}.${tableAllCamelCaseName}Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author ${author}
 * @Date ${time}
 * @Description ${comment}
 */
@Service("${tableAllCamelCaseName}Service")
public class ${tableAllCamelCaseName}ServiceImpl implements ${tableAllCamelCaseName}Service {

    @Resource
    private ${tableAllCamelCaseName}Dao ${tableCamelCaseName}Dao;


    @Override
    public Map<String, Object> list(${tableAllCamelCaseName}Dto ${tableCamelCaseName}Dto) {
        // 执行分页
        PageHelper.startPage(${tableCamelCaseName}Dto.getStart(), ${tableCamelCaseName}Dto.getSize());
        ${tableAllCamelCaseName}Bo ${tableCamelCaseName}Bo = ${tableCamelCaseName}Dto.dtoToBo();
        List<${tableAllCamelCaseName}Bo> ${tableCamelCaseName}BoList = ${tableCamelCaseName}Dao.listByBo(${tableCamelCaseName}Bo);
        PageInfo pageInfo = new PageInfo(${tableCamelCaseName}BoList);

        // 封装结果
        Map<String, Object> result = new HashMap<>(2);
        result.put("count", pageInfo.getTotal());
        result.put("data", ${tableCamelCaseName}BoList);

        return result;
    }

    @Override
    public ${tableAllCamelCaseName}Vo detail(Long id) {
        ${tableAllCamelCaseName}Bo ${tableCamelCaseName}Bo = ${tableCamelCaseName}Dao.getById(id);

        if (${tableCamelCaseName}Bo == null) {
            return null;
        }

        return ${tableCamelCaseName}Bo.boToVo();
    }

    @Override
    public Object add(${tableAllCamelCaseName}Dto ${tableCamelCaseName}Dto) {
        ${tableAllCamelCaseName}Bo ${tableCamelCaseName}Bo = ${tableCamelCaseName}Dto.dtoToBo();

        ${tableCamelCaseName}Dao.insert(${tableCamelCaseName}Bo);

        return true;
    }

    @Override
    public Object batchAdd(${tableAllCamelCaseName}Dto[] ${tableCamelCaseName}Dtos) {
        List<${tableAllCamelCaseName}Bo> ${tableCamelCaseName}BoList = new ArrayList<>(${tableCamelCaseName}Dtos.length);

        Arrays.stream(${tableCamelCaseName}Dtos).forEach(${tableCamelCaseName}Dto -> {
            ${tableCamelCaseName}BoList.add(${tableCamelCaseName}Dto.dtoToBo());
        });

        ${tableCamelCaseName}Dao.batchInsert(${tableCamelCaseName}BoList);

        return true;
    }

    @Override
    public Boolean delete(Long id) {
        ${tableCamelCaseName}Dao.deleteById(id);

        return true;
    }

    @Override
    public Boolean batchDelete(Long[] ids) {
        ${tableCamelCaseName}Dao.batchDeleteByIdArray(ids);

        return true;
    }

    @Override
    public Object update(dto dto) {
        ${tableAllCamelCaseName}Bo ${tableCamelCaseName}Bo = ${tableCamelCaseName}Dto.dtoToBo();

        ${tableCamelCaseName}Dao.updateById(${tableCamelCaseName}Do);

        return true;
    }
}
