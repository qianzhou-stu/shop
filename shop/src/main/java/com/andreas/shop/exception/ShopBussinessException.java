package com.andreas.shop.exception;

/**
 * @Author andreaszhou
 * @ClassName ShopBussinessException
 * @Description TODO
 * @date 2021/1/27 12:10
 * @Version 1.0
 */
public class ShopBussinessException extends Throwable {
    private final Integer code;
    private final String message;

    public ShopBussinessException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ShopBussinessException(ShopException shopException) {
        this(shopException.getCode(),shopException.getMsg());
    }

    @Override
    public String toString() {
        return "ShopBussinessException{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
