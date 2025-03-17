package com.qfleaf.usercenter.common.annotation;

import com.qfleaf.usercenter.common.ResponseCode;
import com.qfleaf.usercenter.common.exception.BusinessException;
import com.qfleaf.usercenter.model.vo.LoginUserVO;
import com.qfleaf.usercenter.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class AuthorizedAnnotationSupport {

    private final UserService userService;
    private final HttpServletRequest request;

    @Autowired
    public AuthorizedAnnotationSupport(UserService userService, HttpServletRequest request) {
        this.userService = userService;
        this.request = request;
    }

    @Pointcut("@annotation(com.qfleaf.usercenter.common.annotation.Authorized)")
    void point() {
    }

    @Before("point()")
    void doBefore(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Authorized annotation = method.getAnnotation(Authorized.class);
        if (annotation != null) {
            int[] authority = annotation.authority();
            LoginUserVO currentUser = userService.getCurrentUser(request);
            Integer role = currentUser.getRole();
            boolean matches = Arrays.stream(authority).anyMatch(auth -> auth == role);
            if (!matches) {
                throw new BusinessException(ResponseCode.BAD_PERMIT, "权限不足");
            }
        }
    }
}
