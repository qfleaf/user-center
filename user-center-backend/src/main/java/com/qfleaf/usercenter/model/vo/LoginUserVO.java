package com.qfleaf.usercenter.model.vo;

import com.qfleaf.usercenter.model.User;
import lombok.Data;

import java.util.Date;

@Data
public class LoginUserVO {
    private Integer userId;
    private String username;
    private String phone;
    private String email;
    private String avatar;
    private Integer gender;
    private Integer role;
    private Date createTime;

    public LoginUserVO(User user) {
        setUserId(user.getUserId());
        setUsername(user.getUsername());
        setPhone(user.getPhone());
        setEmail(user.getEmail());
        setAvatar(user.getAvatar());
        setGender(user.getGender());
        setRole(user.getRole());
        setCreateTime(user.getCreateTime());
    }
}
