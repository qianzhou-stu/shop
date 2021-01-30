package com.andreas.shop.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Author andreaszhou
 * @ClassName UpdateCategoryRequest
 * @Description TODO
 * @date 2021/1/30 12:25
 * @Version 1.0
 */
public class UpdateCategoryRequest {
    @NotNull(message = "id不能为null")
    private Integer id;
    @Size(min = 1,max = 5,message = "类别名大小在1到5之间")
    private String name;
    @Max(value = 3,message = "type最为为三个级别")
    private Integer type;
    private Integer parentId;
    private Integer orderNum;

    public UpdateCategoryRequest() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
