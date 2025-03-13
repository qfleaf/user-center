package com.qfleaf.usercenter.model.dto.user;

import com.qfleaf.usercenter.model.dto.Pageable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryRequest extends Pageable {
    private String username;
    private String phone;
    private String email;
    private Integer gender;
}
