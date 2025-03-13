package com.qfleaf.usercenter.model.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserVO {
    private Integer userId;
    private String username;
    private String phone;
    private String email;
    private String avatar;
    private Integer gender;
    private Integer role;
    private Date createTime;
    private Date updateTime;
}
