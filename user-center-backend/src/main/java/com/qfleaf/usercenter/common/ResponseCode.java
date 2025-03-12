package com.qfleaf.usercenter.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    // region 成功
    SUCCESS(20000, "请求成功"),
    SUCCESS_LOGIN(20001, "登陆成功"),
    SUCCESS_REGISTER(20100, "注册成功"),
    // endregion

    // region 失败
    BAD_REQUEST(40000, "请求错误"),
    BAD_PARAMS(40001, "参数错误"),
    BAD_AUTH(40100, "认证错误"),
    BAD_LOGIN(40101, "未登陆"),
    BAD_PERMIT(40300, "权限错误"),
    // endregion

    // region 错误
    ERROR(50000, "服务器繁忙")
    // endregion
    ;
    private final int code;
    private final String msg;
}
