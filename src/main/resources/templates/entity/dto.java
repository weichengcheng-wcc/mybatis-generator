package ${entityPackage};

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author ${author}
 * @Date ${time}
 * @Description ${comment}
 */
@Data
@ApiModel(value = "${tableAllCamelCaseName}Dto", description = "${comment}")
public class ${tableAllCamelCaseName}Dto implements Serializable {

    private static final long serialVersionUID = ${serialVersionUID}L;


    /**
     * 分页偏移量
     */
    private Integer start;

    /**
     * 分页大小
     */
    private Integer size;

    /**
     * 参数校验
     *
     * @return
     */
    public boolean validate() {
        return true;
    }


    /**
     * dto转bo对象
     *
     * @return
     */
    public ${tableAllCamelCaseName}Bo dtoToBo() {
        return new ${tableAllCamelCaseName}Bo();
    }

}
