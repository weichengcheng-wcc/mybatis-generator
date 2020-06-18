package ${controllerPackage};

import com.cw.framestartercore.utils.PageResultUtils;
import ${entityPackage}.${tableAllCamelCaseName}Dto;
import ${servicePackage}.${tableAllCamelCaseName}Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author ${author}
 * @Date ${time}
 * @Description ${comment}
 */
@Api(tags = "${comment}")
@RestController
@RequestMapping("api/v1/${tableCamelCaseName}")
public class ${tableAllCamelCaseName}Controller {

    @Resource
    private ${tableAllCamelCaseName}Service ${tableCamelCaseName}Service;


    /**
     * 分页查询
     *
     * @param ${tableCamelCaseName}Dto
     * @return
     */
    @ApiOperation("分页列表")
    @GetMapping("list")
    public Object list(${tableAllCamelCaseName}Dto ${tableCamelCaseName}Dto) {
        if (!${tableCamelCaseName}Dto.validate()) {
            return PageResultUtils.buildParamsError();
        }
        return ${tableCamelCaseName}Service.list(${tableCamelCaseName}Dto);
    }


    /**
     * 详情
     *
     * @param id
     * @return
     */
    @ApiOperation("详情")
    @GetMapping("detail")
    public Object detail(Long id) {
        if (id == null) {
            return PageResultUtils.buildParamsError();
        }
        return ${tableCamelCaseName}Service.detail(id);
    }


    /**
     * 新增
     *
     * @param ${tableCamelCaseName}Dto
     * @return
     */
    @ApiOperation("新增数据")
    @PostMapping("add")
    public Object add(${tableAllCamelCaseName}Dto ${tableCamelCaseName}Dto) {
        if (!${tableCamelCaseName}Dto.validate()) {
            return PageResultUtils.buildParamsError();
        }
        return ${tableCamelCaseName}Service.add(${tableCamelCaseName}Dto);
    }


    /**
     * 批量新增数据
     *
     * @param ${tableCamelCaseName}Dtos
     * @return
     */
    @ApiOperation("批量新增")
    @PostMapping("batchAdd")
    public Object batchAdd(${tableAllCamelCaseName}Dto[] ${tableCamelCaseName}Dtos) {
        if (${tableCamelCaseName}Dtos == null) {
            return PageResultUtils.buildParamsError();
        }
        for (${tableAllCamelCaseName}Dto ${tableCamelCaseName}Dto : ${tableCamelCaseName}Dtos) {
            if (${tableCamelCaseName}Dto == null || !${tableCamelCaseName}Dto.validate()) {
                return PageResultUtils.buildParamsError();
            }
        }
        return ${tableCamelCaseName}Service.batchAdd(${tableCamelCaseName}Dtos);
    }


    /**
     * 删除
     *
     * @param id
     * @return
     */
    @ApiOperation("删除")
    @DeleteMapping("delete")
    public Object delete(Long id) {
        if (id == null) {
            return PageResultUtils.buildParamsError();
        }
        return ${tableCamelCaseName}Service.delete(id);
    }


    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @ApiOperation("批量删除")
    @DeleteMapping("batchDelete")
    public Object batchDelete(Long[] ids) {
        if (ids == null) {
            return PageResultUtils.buildParamsError();
        }
        for (Long id : ids) {
            if (id == null) {
                return PageResultUtils.buildParamsError();
            }
        }
        return ${tableCamelCaseName}Service.batchDelete(ids);
    }


    /**
     * 更新
     *
     * @param ${tableCamelCaseName}Dto
     * @return
     */
    @ApiOperation("更新")
    @PutMapping("update")
    public Object update(${tableAllCamelCaseName}Dto ${tableCamelCaseName}Dto) {
        if (!${tableCamelCaseName}Dto.validate()) {
            return PageResultUtils.buildParamsError();
        }
        return ${tableCamelCaseName}Service.update(${tableCamelCaseName}Dto);
    }


}
