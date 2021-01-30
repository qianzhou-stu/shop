package com.andreas.shop.request;


import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Author andreaszhou
 * @ClassName AddCategoryRequest
 * @Description TODO
 * @date 2021/1/28 8:59
 * @Version 1.0
 */
/*
* 在前面我们的验证的过程是过于繁琐，在这里开始引入一个比较优秀的验证的方式。这个方式可以使得我们的操作变得更加的简洁
* */
public class AddCategoryRequest {

    @NotNull(message = "name不能为null")
    @Size(min = 1,max = 5,message = "类别名大小在1到5之间")
    private String name;
    @NotNull(message = "type不能为null")
    @Max(value = 3,message = "type最为为三个级别")
    private Integer type;
    @NotNull(message = "parentId不能为null")
    private Integer parentId;
    @NotNull(message = "orderNum不能为null")
    private Integer orderNum;

    public AddCategoryRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
}
