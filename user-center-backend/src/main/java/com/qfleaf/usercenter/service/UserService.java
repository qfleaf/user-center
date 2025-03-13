package com.qfleaf.usercenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qfleaf.usercenter.model.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qfleaf.usercenter.model.dto.user.*;
import com.qfleaf.usercenter.model.vo.LoginUserVO;
import com.qfleaf.usercenter.model.vo.UserListVO;
import com.qfleaf.usercenter.model.vo.UserLoginResponse;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author qianfang
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2025-02-21 21:43:47
*/
public interface UserService extends IService<User> {
    Void register(UserRegisterRequest userRegisterRequest);
    UserLoginResponse login(UserLoginRequest userLoginRequest, HttpServletRequest request);
    LoginUserVO getCurrentUser(HttpServletRequest request);
    Void logout(HttpServletRequest request);
    void createUser(UserCreateRequest userCreateRequest);
    void updateUser(UserUpdateRequest userUpdateRequest);
    IPage<UserListVO> findUserListVo(UserQueryRequest userQueryRequest);
}
