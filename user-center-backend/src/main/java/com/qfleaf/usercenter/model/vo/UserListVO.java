package com.qfleaf.usercenter.model.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserListVO {
    private Integer userId;
    private String username;
    private String phone;
    private String email;
    private Integer gender;
    private Date createTime;
    private Date updateTime;
    private Integer role;
    private Boolean isDeleted;
}
