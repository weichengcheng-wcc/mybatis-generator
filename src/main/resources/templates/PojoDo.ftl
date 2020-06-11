package ${entityPackage};

<#list imports as im>
import ${im};
</#list>
import lombok.Data;
import java.io.Serializable;

/**
 * @Author ${author}
 * @Date ${date}
 * @Description ${((tableComment!'')?length>0)?string((tableComment!''),tableName)}
 */
@Data
public class ${tableNameCamel}DO implements Serializable {

    private static final long serialVersionUID = ${serialVersionUID}L;

    <#list columnList as column>
    /*
     * ${((column.comment!'')?length>0)?string((column.comment!''),column.name)}
     */
    private ${column.type} ${column.nameCamel};

    </#list>

    public ${tableNameCamel}DO (){

    }

}