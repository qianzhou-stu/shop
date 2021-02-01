package com.andreas.shop.pojo.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author andreaszhou
 * @ClassName UpdateProductRequest
 * @Description TODO
 * @date 2021/1/31 18:33
 * @Version 1.0
 */
public class UpdateProductRequest {

    @NotNull(message = "商品id不能为null")
    private int id;

    private String name;

    private String image;

    private String detail;

    private Integer categoryId;

    @Min(value = 1, message = "价格不能小于1分")
    private Integer price;

    @Max(value = 10000, message = "库存不能大于10000")
    private Integer stock;

    private Integer status;

    public UpdateProductRequest() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
