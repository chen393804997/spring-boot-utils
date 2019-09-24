package com.example.springbootutils.common.exception;

public enum ReturnInfoEnum implements RestStatus{
    /**
     * 成功
     */
    Success("000000", "成功"),
    /**
     * 失败
     */
    Fail("000001", "失败"),
    /**
     * 用户未登录
     */
    NotLogin("000999", "用户未登录"),

    /**
     * 用户未绑定手机号
     */
    NotBindPhone("000996", "未绑定手机号"),

    /**
     * 该账号已被封禁
     */
    HasBlocked("000997", "该账号已被封禁"),
    /**
     * 无权限
     */
    NotRight("000998", "无权限"),
    /**
     * 参数错误
     */
    parameterError("000990", "参数错误"),

    /**
     * 广告主未审核
     */
    NotCheck("001001", "广告主未审核"),
    /**
     * 业务异常
     */
    businessError("000995", "业务异常"),
    /**
     * 系统忙
     */
    UnknownError("999999", "系统忙");

    private String code;
    private String desc;

    ReturnInfoEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String desc() {
        return desc;
    }
}
