package com.qfleaf.usercenter.common.exception;

import com.qfleaf.usercenter.common.CommonResponse;
import com.qfleaf.usercenter.common.ResponseCode;
import com.qfleaf.usercenter.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BusinessException.class)
    public CommonResponse<BusinessException> handleBusinessException(BusinessException businessException) {
        log.info("用户业务异常: {}, 详情: {}", businessException.getMessage(), businessException.getDescription());
        return ResultUtil.failure(businessException.getResponseCode(), businessException.getDescription());
    }

    // 参数校验异常的处理
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public CommonResponse<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        log.debug("输入参数异常: {}", methodArgumentNotValidException.getMessage(), methodArgumentNotValidException);
        // 获取第一条字段错误信息
        String errorMessage = methodArgumentNotValidException.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse("参数错误");
        return ResultUtil.failure(ResponseCode.BAD_PARAMS, errorMessage);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public CommonResponse<Void> handleRuntimeException(RuntimeException runtimeException) {
        log.error("未知异常: {}", runtimeException.getMessage(), runtimeException);
        return ResultUtil.failure(ResponseCode.ERROR, "服务器繁忙");
    }
}
