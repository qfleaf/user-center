package com.qfleaf.usercenter.model;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import lombok.Data;

/**
 * 用户表
 * @TableName user
 */
// 由于user是pgsql的保留字，使用user作为表名在mybatis-plus中会错误解析，导致查询出现异常，此处改用全限定名来声明可以解决这个问题。
@TableName(value ="public.user")
@Data
public class User {
    @TableId
    private Integer userId;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String avatar;
    private Integer gender;
    private Date createTime;
    private Date updateTime;
    private Integer role;
    @TableLogic
    private Boolean isDeleted;
}