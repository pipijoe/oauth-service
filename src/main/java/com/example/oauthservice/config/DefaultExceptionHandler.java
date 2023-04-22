package com.example.oauthservice.config;

import cn.les.oauth2auth.domain.ResultCode;
import cn.les.oauth2auth.domain.ResultJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Joetao
 * @date 2022/5/30
 */
@RestControllerAdvice
@Slf4j
public class DefaultExceptionHandler {
    /**
     * 处理InvalidGrantException
     */
    @ExceptionHandler(InvalidGrantException.class)
    public ResultJson<?> handleCustomException(InvalidGrantException e, HttpServletRequest request) {
        return ResultJson.failure(ResultCode.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(InvalidClientException.class)
    public ResultJson<?> handleCustomException(InvalidClientException e, HttpServletRequest request) {
        return ResultJson.failure(ResultCode.INVALID_CLIENT, e.getMessage());
    }
}
