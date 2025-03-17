package com.qfleaf.usercenter.strategy.login;

import com.qfleaf.usercenter.model.dto.user.UserLoginRequest;
import com.qfleaf.usercenter.model.vo.LoginUserVO;

public interface LoginStrategy {
    /**
     * 执行登陆策略
     *
     * @param userLoginRequest 登陆请求参数
     * @return 登陆用户视图实体
     */
    LoginUserVO doLogin(UserLoginRequest userLoginRequest);
}
