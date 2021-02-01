package com.andreas.shop.service;

import com.andreas.shop.exception.ShopBussinessException;
import com.andreas.shop.pojo.vo.CategoryVO;
import com.andreas.shop.pojo.request.AddCategoryRequest;
import com.andreas.shop.pojo.request.UpdateCategoryRequest;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Author andreaszhou
 * @ClassName CategoryService
 * @Description TODO
 * @date 2021/1/28 9:03
 * @Version 1.0
 */
public interface CategoryService {
    void addCategory(AddCategoryRequest addCategoryRequest) throws ShopBussinessException;

    void updateCategory(UpdateCategoryRequest updateCategoryRequest) throws ShopBussinessException;

    void delete(Integer id) throws ShopBussinessException;

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    List<CategoryVO> listCategoryForCustomer(Integer parentId);
}
