package ${entityPackage};

<#list imports as im>
import ${im};
</#list>
import lombok.Data;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author ${author}
 * @Date ${date}
 * @Description ${((tableComment!'')?length>0)?string((tableComment!''),tableName)}
 */
@Data
@ApiModel("${((tableComment!'')?length>0)?string((tableComment!''),tableName)}")
public class ${tableNameCamel}DTO implements Serializable {

    private static final long serialVersionUID = ${serialVersionUID}L;

    <#list columnList as column>
    @ApiModelProperty("${((column.comment!'')?length>0)?string((column.comment!''),column.name)}")
    private ${column.type} ${column.nameCamel};

    </#list>
    @ApiModelProperty("偏移量")
    private Long start;

    @ApiModelProperty("分页大小")
    private Integer size;

    @ApiModelProperty("排序")
    private String orderBy;

    public ${tableNameCamel}DTO (){

    }
	
	/**
	 * @Author ${author}
	 * @Date ${date}
	 * @Description 分页查询参数校验
	 */
    public boolean validateList(){
		return true;
    }

	/** 
	 * @Author ${author}
	 * @Date ${date}
	 * @Description 新增参数校验
	 */  
	public boolean validateInsert(){
		return true;
	}

	/**
	 * @Author ${author}
	 * @Date ${date}
	 * @Description 修改参数校验
	 */
	public boolean validateUpdate(){
		return true;
	}

}
