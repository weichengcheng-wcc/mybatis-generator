package ${entityPackage};

/**
 * @Author ${author}
 * @Date ${date}
 * @Description 操作结果枚举
 */
public enum PageResultEnum {


    SUCCESS(200, "操作成功"),
    FAIL(201, "操作失败"),
    ERROR(500, "服务器异常"),
    FORBIDDEN(403, "无权限操作"),
    LOGIN(-1, "未登录"),
    PARAM_ERROR(700, "参数错误"),
    TOKEN(0, "token异常");


    private Integer code;

    private String desc;


    private PageResultEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
