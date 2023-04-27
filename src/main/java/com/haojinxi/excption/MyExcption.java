package com.haojinxi.excption;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
//通过spring AOP拦截异常，交给GloabalExceptionHandler处理
@ControllerAdvice
public class MyExcption {

    @ExceptionHandler(value = Exception.class)
    public String defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        e.printStackTrace();
        return "服务器繁忙，请稍后再试！";
    }
}
