package com.qfleaf.usercenter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qfleaf.usercenter.common.CommonResponse;
import com.qfleaf.usercenter.common.ResponseCode;
import com.qfleaf.usercenter.model.User;
import com.qfleaf.usercenter.model.dto.user.*;
import com.qfleaf.usercenter.model.vo.LoginUserVO;
import com.qfleaf.usercenter.model.vo.UserListVO;
import com.qfleaf.usercenter.model.vo.UserLoginResponse;
import com.qfleaf.usercenter.model.vo.UserVO;
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
    // region 用户接口
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
    // region 管理员接口
    @GetMapping("list")
    public CommonResponse<IPage<UserListVO>> list(UserQueryRequest userQueryRequest) {
        IPage<UserListVO> userListVo = userService.findUserListVo(userQueryRequest);
        return ResultUtil.success(ResponseCode.SUCCESS, userListVo);
    }

    @GetMapping("{id}")
    public CommonResponse<UserVO> find(@PathVariable Integer id) {
        User user = userService.getById(id);
        UserVO vo = user.toVo();
        return ResultUtil.success(ResponseCode.SUCCESS, vo);
    }

    @PostMapping
    public CommonResponse<Void> save(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        userService.createUser(userCreateRequest);
        return ResultUtil.success(ResponseCode.SUCCESS, null);
    }

    @PutMapping
    public CommonResponse<Void> update(@Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        userService.updateUser(userUpdateRequest);
        return ResultUtil.success(ResponseCode.SUCCESS, null);
    }

    @DeleteMapping
    public CommonResponse<Void> delete(@RequestParam Integer id) {
        userService.removeById(id);
        return ResultUtil.success(ResponseCode.SUCCESS, null);
    }
    // endregion

}
