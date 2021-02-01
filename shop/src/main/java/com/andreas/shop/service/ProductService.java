package com.andreas.shop.service;

import com.andreas.shop.exception.ShopBussinessException;
import com.andreas.shop.pojo.Product;
import com.andreas.shop.pojo.request.AddProductRequest;
import com.andreas.shop.pojo.request.ListProductRequest;
import com.andreas.shop.pojo.request.UpdateProductRequest;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

/**
 * @Author andreaszhou
 * @ClassName ProductService
 * @Description TODO
 * @date 2021/1/31 14:15
 * @Version 1.0
 */
public interface ProductService {
    void addProduct(AddProductRequest addProductRequest) throws ShopBussinessException;

    void updateProduct(UpdateProductRequest updateProductRequest) throws ShopBussinessException;

    void deleteProduct(Integer id) throws ShopBussinessException;

    void batchUpdateSellStatus(Integer[] ids, Integer sellStatus) throws ShopBussinessException;

    PageInfo listForAdmin(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

    Product detail(Integer id) throws ShopBussinessException;

    PageInfo listForCustom(ListProductRequest listProductList);
}
