package cn.hdustea.aha_server.enums;

/**
 * 错误码枚举
 *
 * @author STEA_YY
 **/
public enum ApiExceptionCode {
    TOKEN_NOT_FOUND(101, "未找到token！"),
    TOKEN_CHECK_FAIL(102, "token校验失败！"),
    TOKEN_EXPIRED(103, "token已过期！"),
    ACCESS_FORBIDDEN(104, "无权限访问！"),
    USER_NOT_FOUND(301, "用户不存在！"),
    INVALID_USER_PASSWORD(302, "用户名或密码错误！"),
    ACCOUNT_EXISTED(303, "该用户已经存在！"),
    WECHAT_UNAUTHORIZED(301, "未找到微信授权信息！"),
    INSERT_ERROR(401, "创建失败！"),
    UPDATE_ERROR(402, "修改失败！"),
    DELETE_ERROR(403, "删除失败"),
    MESSAGE_SEND_FAIL(501, "验证短信发送失败！"),
    MESSAGE_CHECK_FAIL(502, "短信验证码错误！");

    private final Integer value;
    private final String desc;

    ApiExceptionCode(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}