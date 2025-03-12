package com.qfleaf.usercenter.controller;

import com.qfleaf.usercenter.common.CommonResponse;
import com.qfleaf.usercenter.common.ResponseCode;
import com.qfleaf.usercenter.model.dto.user.*;
import com.qfleaf.usercenter.model.vo.LoginUserVO;
import com.qfleaf.usercenter.model.vo.UserLoginResponse;
import com.qfleaf.usercenter.service.UserService;
import com.qfleaf.usercenter.utils.ResultUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@Validated
public class UserController {
    @Resource
    private UserService userService;

    // todo 业务优化
    // 用户接口
    // region
    @PostMapping("register")
    public CommonResponse<Void> register(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        return ResultUtil.success(ResponseCode.SUCCESS_REGISTER, userService.register(userRegisterRequest));
    }

    @PostMapping("login")
    public CommonResponse<UserLoginResponse> login(@Valid @RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        return ResultUtil.success(ResponseCode.SUCCESS_LOGIN, userService.login(userLoginRequest, request));
    }

    @GetMapping("current")
    public CommonResponse<LoginUserVO> getCurrentUser(HttpServletRequest request) {
        return ResultUtil.success(ResponseCode.SUCCESS, userService.getCurrentUser(request));
    }

    @PostMapping("logout")
    public CommonResponse<Void> logout(HttpServletRequest request) {
        return ResultUtil.success(ResponseCode.SUCCESS, userService.logout(request));
    }
    // endregion

    // todo 权限校验
    // 管理员接口
    // region
    @GetMapping("list")
    public void list(UserQueryRequest userQueryRequest) {
        userService.list(); // todo 修改逻辑进行分页
    }

    @GetMapping("{id}")
    public void find(@PathVariable Integer id) {
        userService.getById(id);
    }

    @PostMapping
    public void save(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        userService.createUser(userCreateRequest);
    }

    @PutMapping
    public void update(@Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        userService.updateUser(userUpdateRequest);
    }

    @DeleteMapping
    public void delete(@RequestParam Integer id) {
        userService.removeById(id);
    }
    // endregion

}
