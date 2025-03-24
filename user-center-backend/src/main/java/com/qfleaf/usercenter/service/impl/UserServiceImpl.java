package com.qfleaf.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qfleaf.usercenter.common.ResponseCode;
import com.qfleaf.usercenter.common.exception.BusinessException;
import com.qfleaf.usercenter.mapper.UserMapper;
import com.qfleaf.usercenter.model.User;
import com.qfleaf.usercenter.model.dto.user.*;
import com.qfleaf.usercenter.model.vo.LoginUserVO;
import com.qfleaf.usercenter.model.vo.UserListVO;
import com.qfleaf.usercenter.model.vo.UserLoginResponse;
import com.qfleaf.usercenter.service.UserService;
import com.qfleaf.usercenter.strategy.login.LoginStrategy;
import com.qfleaf.usercenter.strategy.login.LoginStrategyManager;
import com.qfleaf.usercenter.utils.PasswordUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author qianfang
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2025-02-21 21:43:47
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    private final LoginStrategyManager loginStrategyManager;

    @Autowired
    public UserServiceImpl(LoginStrategyManager loginStrategyManager) {
        this.loginStrategyManager = loginStrategyManager;
    }

    // region 用户业务
    @Override
    public Void register(UserRegisterRequest userRegisterRequest) {
        User entity = userRegisterRequest.toEntity();
        boolean exists = baseMapper.exists(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, entity.getUsername()));
        if (exists) {
            throw new BusinessException(ResponseCode.BAD_PARAMS, "用户名已被使用");
        }
        entity.setPassword(PasswordUtil.encode(userRegisterRequest.getPassword()));
        save(entity);
        log.info("新用户注册, ID为: {}, 详细数据为: {}", entity.getUserId(), entity);
        return null;
    }

    @Override
    public UserLoginResponse login(UserLoginRequest userLoginRequest, HttpServletRequest request) {
        LoginStrategy loginStrategy = loginStrategyManager.getLoginStrategy(userLoginRequest.getLoginType());
        LoginUserVO loginUserVO = loginStrategy.doLogin(userLoginRequest);
        request.getSession().setAttribute("currentUser", loginUserVO);
        // todo 构建登陆响应
        return new UserLoginResponse();
    }

    @Override
    public LoginUserVO getCurrentUser(HttpServletRequest request) {
        Object currentUser = request.getSession().getAttribute("currentUser");
        if (!(currentUser instanceof LoginUserVO)) {
            throw new BusinessException(ResponseCode.BAD_AUTH, "请先登陆");
        }
        return (LoginUserVO) currentUser;
    }

    @Override
    public void logout(HttpServletRequest request) {
        request.getSession().removeAttribute("currentUser");
    }
    // endregion

    // region 管理员业务
    @Override
    public void createUser(UserCreateRequest userCreateRequest) {
        User entity = userCreateRequest.toEntity();
        boolean exists = baseMapper.exists(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, entity.getUsername()));
        if (exists) {
            throw new BusinessException(ResponseCode.BAD_PARAMS, "用户名已存在");
        }
        entity.setPassword(PasswordUtil.encode(userCreateRequest.getPassword()));
        save(entity);
    }

    @Override
    public void updateUser(UserUpdateRequest userUpdateRequest) {
        User entity = userUpdateRequest.toEntity();
        entity.setPassword(PasswordUtil.encode(userUpdateRequest.getPassword()));
        updateById(entity);
    }

    @Override
    public IPage<UserListVO> findUserListVo(UserQueryRequest userQueryRequest) {
        IPage<UserListVO> page = new Page<>(userQueryRequest.getCurrent(), userQueryRequest.getSize());
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .like(StringUtils.hasText(userQueryRequest.getUsername()), User::getUsername, userQueryRequest.getUsername())
                .like(StringUtils.hasText(userQueryRequest.getPhone()), User::getPhone, userQueryRequest.getPhone())
                .like(StringUtils.hasText(userQueryRequest.getEmail()), User::getEmail, userQueryRequest.getEmail())
                .eq(User::getIsDeleted, false);
        return baseMapper.selectUserListPageVo(page, queryWrapper);
    }
    // endregion
}
