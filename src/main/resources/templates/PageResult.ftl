package ${entityPackage};

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @Author ${author}
 * @Date ${date}
 * @Description 查询结果
 */
@Data
@ApiModel("查询结果")
@Builder
public class PageResult implements Serializable {

    @ApiModelProperty("耗时")
    private Long time;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("返回的消息")
    private String msg;

    @ApiModelProperty("返回的内容")
    private Map<String, Object> body;

}
