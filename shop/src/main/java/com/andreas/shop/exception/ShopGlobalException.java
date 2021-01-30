package com.andreas.shop.exception;

import com.andreas.shop.common.ApiRestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author andreaszhou
 * @ClassName ShopGlobalException
 * @Description 处理统一异常的handler
 * @date 2021/1/27 13:07
 * @Version 1.0
 */
@ControllerAdvice
public class ShopGlobalException {
    private final Logger log = LoggerFactory.getLogger(ShopGlobalException.class);

    /*
     * 这个是对系统进行统一处理异常的Handler
     * */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception e) {
        log.error("Default Exception: ", e);
        return ApiRestResponse.error(ShopException.SYSTEM_ERROR);
    }

    /*
     *
     * 这个是对业务逻辑进行统一处理异常的Handler
     * */

    @ExceptionHandler(ShopBussinessException.class)
    @ResponseBody
    public Object handleShopException(ShopBussinessException e) {
        log.error("ShopBussinessException: ", e);
        return ApiRestResponse.error(e.getCode(), e.getMessage());
    }


    /*
     * 在这里参数校验的统一格式的输出的形式
     * 将参数校验的统一格式进行输出
     * */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiRestResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException", e);
        return handleBindingResult(e.getBindingResult());
    }
    /*
    * BindingResult result 判定错误的结果，BindingResult 将错误结果放在一起
    * */
    private ApiRestResponse handleBindingResult(BindingResult result) {
        //把异常处理为对外暴露的提示
        //把异常处理为对外暴露的提示
        List<String> list = new ArrayList<>();
        if (result.hasErrors()) {
            List<ObjectError> allErrors = result.getAllErrors();
            for (ObjectError objectError : allErrors) {
                String message = objectError.getDefaultMessage();
                list.add(message);
            }
        }
        if (list.size() == 0) {
            return ApiRestResponse.error(ShopException.REQUEST_PARAM_ERROR);
        }
        return ApiRestResponse
                .error(ShopException.REQUEST_PARAM_ERROR.getCode(), list.toString());
    }
}
