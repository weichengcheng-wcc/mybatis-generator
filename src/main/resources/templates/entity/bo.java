package ${entityPackage};

import lombok.Data;

import java.io.Serializable;

/**
 * @Author ${author}
 * @Date ${time}
 * @Description ${comment}
 */
@Data
public class ${tableAllCamelCaseName}Bo implements Serializable {

    private static final long serialVersionUID = ${serialVersionUID}L;


    /**
     * bo转vo对象
     * @return
     */
    public ${tableAllCamelCaseName}Vo boToVo(){
        ${tableAllCamelCaseName}Vo ${tableCamelCaseName}Vo = new ${tableAllCamelCaseName}Vo();
        return ${tableCamelCaseName}Vo;
    }

}
