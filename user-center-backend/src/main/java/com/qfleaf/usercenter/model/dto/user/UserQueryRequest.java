package com.qfleaf.usercenter.model.dto.user;

import lombok.Data;

@Data
public class UserQueryRequest {
    private String username;
    private String phone;
    private String email;
    private Integer gender;
}
