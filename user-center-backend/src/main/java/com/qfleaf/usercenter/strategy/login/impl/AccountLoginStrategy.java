package com.qfleaf.usercenter.strategy.login.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qfleaf.usercenter.common.ResponseCode;
import com.qfleaf.usercenter.common.exception.BusinessException;
import com.qfleaf.usercenter.mapper.UserMapper;
import com.qfleaf.usercenter.model.User;
import com.qfleaf.usercenter.model.dto.user.UserLoginRequest;
import com.qfleaf.usercenter.model.vo.LoginUserVO;
import com.qfleaf.usercenter.strategy.login.Constants;
import com.qfleaf.usercenter.strategy.login.LoginStrategy;
import com.qfleaf.usercenter.strategy.login.annotation.LoginType;
import com.qfleaf.usercenter.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;

@LoginType(Constants.LoginType.ACCOUNT)
public class AccountLoginStrategy implements LoginStrategy {
    private final UserMapper userMapper;

    @Autowired
    public AccountLoginStrategy(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public LoginUserVO doLogin(UserLoginRequest userLoginRequest) {
        User entity = userLoginRequest.toEntity();
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, entity.getUsername()));
        if (user == null || user.getIsDeleted()) {
            throw new BusinessException(ResponseCode.BAD_PARAMS, "用户不存在或已被禁用");
        }
        boolean matches = PasswordUtil.matches(userLoginRequest.getPassword(), user.getPassword());
        if (!matches) {
            throw new BusinessException(ResponseCode.BAD_AUTH, "用户名或密码错误");
        }
        return new LoginUserVO(user);
    }
}
