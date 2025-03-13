package com.qfleaf.usercenter.model.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserLoginResponse {
    private String ip;
    private String type;
    private String currentAuthority;
    private String token;
    private Date loginTime;
}
