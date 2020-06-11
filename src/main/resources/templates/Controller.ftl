package ${controllerPackage};

import ${servicePackage}.${tableNameCamel}Service;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ${entityPackage}.${tableNameCamel}DTO;
import ${entityPackage}.PageResult;
import ${entityPackage}.PageResultEnum;

/**
 * @Author ${author}
 * @Date ${date}
 * @Description ${((tableComment!'')?length>0)?string((tableComment!''),tableName)}
 */
@Api(tags = "${((tableComment!'')?length>0)?string((tableComment!''),tableName)}")
@RestController
@RequestMapping("${prefixPackage}")
public class ${tableNameCamel}Controller {

    @Resource
    private ${tableNameCamel}Service service;

    @ApiOperation("分页查询")
    @GetMapping("list")
    public Object list(${tableNameCamel}DTO dto){
		if(!dto.validateList()){
			// 参数校验未通过
			return PageResult.builder().status(PageResultEnum.PARAM_ERROR.getCode()).msg(PageResultEnum.PARAM_ERROR.getDesc()).build();
		}
        return service.list(dto);
    }


    @ApiOperation("新增")
    @PostMapping("insert")
    public Object insert(${tableNameCamel}DTO dto){
        if(!dto.validateInsert()){
            // 参数校验未通过
            return PageResult.builder().status(PageResultEnum.PARAM_ERROR.getCode()).msg(PageResultEnum.PARAM_ERROR.getDesc()).build();
        }
        return service.insert(dto);
    }

    @ApiOperation("删除")
    @DeleteMapping("delete")
    public Object delete(Long id){
        if(id == null){
            // 参数校验未通过
            return PageResult.builder().status(PageResultEnum.PARAM_ERROR.getCode()).msg(PageResultEnum.PARAM_ERROR.getDesc()).build();
        }
        return service.delete(id);
    }

    @ApiOperation("批量删除")
    @DeleteMapping("deleteBatch")
    public Object deleteBatch(Long[] ids){
        if(ids == null || ids.length == 0){
            // 参数校验未通过
            return PageResult.builder().status(PageResultEnum.PARAM_ERROR.getCode()).msg(PageResultEnum.PARAM_ERROR.getDesc()).build();
        }
        return service.deleteBatch(ids);
    }

    @ApiOperation("修改")
    @PutMapping("update")
    public Object update(${tableNameCamel}DTO dto){
        if(!dto.validateUpdate()){
            // 参数校验未通过
            return PageResult.builder().status(PageResultEnum.PARAM_ERROR.getCode()).msg(PageResultEnum.PARAM_ERROR.getDesc()).build();
        }
        return service.update(dto);
    }

    @ApiOperation("详情")
    @GetMapping("detail")
    public Object detail(Long id){
        if(id == null){
            // 参数校验未通过
            return PageResult.builder().status(PageResultEnum.PARAM_ERROR.getCode()).msg(PageResultEnum.PARAM_ERROR.getDesc()).build();
        }
        return service.detail(id);
    }

}
