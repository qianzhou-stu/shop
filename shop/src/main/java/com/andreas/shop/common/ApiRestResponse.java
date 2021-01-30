package com.andreas.shop.common;

import com.andreas.shop.exception.ShopException;

/**
 * @Author andreaszhou
 * @ClassName ApiRestResponse
 * @Description 统一接口得规范
 * @date 2021/1/27 11:57
 * @Version 1.0
 */
/*
* 在这里我们设计了一个接口的规范。以后很多的东西我们都会在这个的接口实现
* */
public class ApiRestResponse<T> {
    private Integer status;
    private String msg;
    private T data;
    private static final int OK_CODE = 10000;
    private static final String OK_MSG = "SUCCESS";

    public ApiRestResponse(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ApiRestResponse(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
    public ApiRestResponse(){
        this(OK_CODE,OK_MSG);
    }

    /*
    * 成功：success 时候，没有返回数据data
    * */
    public static <T> ApiRestResponse<T> success(){
        return new ApiRestResponse<>();
    }

    /*
     * 成功：success 时候返回数据data
     * */
    public static <T> ApiRestResponse<T> success(T result){
        ApiRestResponse<T> response = new ApiRestResponse<>();
        response.setData(result);
        return response;
    }

    /*
    * 失败：failure  错误的时候返回错误的代码和错误的数据
    * */
    public static <T> ApiRestResponse<T> error(Integer code,String msg){
        ApiRestResponse<T> response = new ApiRestResponse<>(code,msg);
        return response;
    }

    /*
    * 失败：failure 错误的时候我们用自定义的异常类来做处理
    * */
    public static <T> ApiRestResponse<T> error(ShopException shopException){
        return new ApiRestResponse<>(shopException.getCode(),shopException.getMsg());
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static int getOkCode() {
        return OK_CODE;
    }

    public static String getOkMsg() {
        return OK_MSG;
    }

    @Override
    public String toString() {
        return "ApiRestResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
