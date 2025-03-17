package com.qfleaf.usercenter.strategy.login;

import com.qfleaf.usercenter.common.ResponseCode;
import com.qfleaf.usercenter.common.exception.BusinessException;
import com.qfleaf.usercenter.strategy.login.annotation.LoginType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class LoginStrategyManager {
    private final Map<String, LoginStrategy> loginStrategiesMap = new ConcurrentHashMap<>();

    @Autowired
    public LoginStrategyManager(List<LoginStrategy> loginStrategies) {
        loginStrategies.forEach(loginStrategy -> {
            LoginType annotation = loginStrategy.getClass().getAnnotation(LoginType.class);
            if (annotation != null) {
                loginStrategiesMap.put(annotation.value(), loginStrategy);
            }
        });
        log.debug("loginStrategiesMap: {}", loginStrategiesMap);
    }

    public LoginStrategy getLoginStrategy(String loginType) {
        return Optional.ofNullable(loginStrategiesMap.get(loginType))
                .orElseThrow(() -> new BusinessException(ResponseCode.BAD_PARAMS, "不支持的登陆方式: " + loginType));
    }
}
