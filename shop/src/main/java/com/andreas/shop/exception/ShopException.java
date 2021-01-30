package com.andreas.shop.exception;

/**
 * @Author andreaszhou
 * @ClassName ShopException
 * @Description TODO
 * @date 2021/1/27 12:14
 * @Version 1.0
 */
public enum ShopException {
    /**
     * 描述：     异常枚举
     */
    NEED_USER_NAME(10001,"用户名不能为空"),

    NEED_PASSWORD(10002,"密码不能为空"),

    PASSWORD_TOO_SHORT(10003,"密码长度不能小于8位"),

    NAME_EXISTED(10004,"不允许重名，注册失败"),

    INSERT_FAILED(10005,"插入失败，请重试"),

    WRONG_PASSWORD(10006,"密码错误"),

    NEED_LOGIN(10007,"用户未登录"),

    UPDATE_FAILED(10008,"更新失败"),

    NEED_ADMIN(10009,"无管理员权限"),

    NO_USER_EXISTED(10010,"用户不存在，需要注册"),

    NO_SIGNATURE(10011,"签名不能为空"),

    CATEGORY_EXIST(10012,"分类不允许重名"),

    NO_CATEGORY_NAME(10013,"分类目录名不能为空"),

    NO_CATEGORY_TYPE(10014,"分类目录级别不能为空"),

    NO_CATEGORY_PARENT_ID(10015,"分类的父目录不能为空"),

    NO_CATEGORY_ORDER(10016,"目录展示的顺序不能为空"),

    IS_ADMIN(10017,"用户不是管理员"),

    REQUEST_PARAM_ERROR(10018, "参数错误"),

    IS_CATEGORY(10019,"分类不存在"),

    UPDATE_CATEGORY_FAILURE(10020,"更新分类失败"),

    DELETE_FAILED(10021,"删除失败"),

    SYSTEM_ERROR(20000,"系统异常，请从控制台或日志中查看具体错误信息"),
    ;

    /**
     * 异常码
     */
    Integer code;
    /**
     * 异常信息
     */
    String msg;

    ShopException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

