package com.qfleaf.usercenter.strategy.login.impl;

import com.qfleaf.usercenter.common.ResponseCode;
import com.qfleaf.usercenter.common.exception.BusinessException;
import com.qfleaf.usercenter.model.dto.user.UserLoginRequest;
import com.qfleaf.usercenter.model.vo.LoginUserVO;
import com.qfleaf.usercenter.strategy.login.Constants;
import com.qfleaf.usercenter.strategy.login.LoginStrategy;
import com.qfleaf.usercenter.strategy.login.annotation.LoginType;

@LoginType(Constants.LoginType.SMS)
public class SmsLoginStrategy implements LoginStrategy {
    @Override
    public LoginUserVO doLogin(UserLoginRequest userLoginRequest) {
        // todo 实现手机验证码登陆策略
        throw new BusinessException(ResponseCode.BAD_FUNCTION, "功能未启用");
    }
}
