package com.qfleaf.usercenter.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class CommonResponse<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Integer code;
    private String msg;
    private T data;
}
