package com.andreas.shop.controller;

import com.andreas.shop.common.ApiRestResponse;
import com.andreas.shop.exception.ShopBussinessException;
import com.andreas.shop.exception.ShopException;
import com.andreas.shop.pojo.Product;
import com.andreas.shop.pojo.request.ListProductRequest;
import com.andreas.shop.service.ProductService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author andreaszhou
 * @ClassName ProductController
 * @Description 前台商品管理
 * @date 2021/1/31 20:08
 * @Version 1.0
 */
@RestController
public class ProductController {
    /*
     * 前端商品的controller
     * */
    @Resource
    private ProductService productServiceImp;

    /*
     * 在这里我们写了一个前端的商品的详情页，那么在之后我们就要写前端的商品的显示的情况
     * */
    @ApiOperation("前台显示商品详情")
    @GetMapping("/product/detail")
    @ResponseBody
    public ApiRestResponse showDetail(@Param("id") Integer id) {
        if (id == null) {
            return ApiRestResponse.error(ShopException.NO_ID);
        }
        Product product;
        try {
            product = productServiceImp.detail(id);
        } catch (ShopBussinessException e) {
            e.printStackTrace();
            return ApiRestResponse.error(e.getCode(), e.getMessage());
        }
        return ApiRestResponse.success(product);
    }

    /*在这里我们要显示我们的前端的商品的列表的展示----难点--会用到递归的知识*/
    @ApiOperation("商品详情")
    @GetMapping("product/list")
    public ApiRestResponse list(ListProductRequest listProductRequest) {
        PageInfo pageInfo = productServiceImp.listForCustom(listProductRequest);
        return ApiRestResponse.success(pageInfo);
    }
}
