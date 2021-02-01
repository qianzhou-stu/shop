package com.andreas.shop.controller;

import com.andreas.shop.common.ApiRestResponse;
import com.andreas.shop.exception.ShopBussinessException;
import com.andreas.shop.filter.UserFilter;
import com.andreas.shop.pojo.vo.CartVO;
import com.andreas.shop.service.CartService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author andreaszhou
 * @ClassName CartCategory
 * @Description 购物车管理controller
 * @date 2021/2/1 15:12
 * @Version 1.0
 */
@RestController
public class CartController {
    /*在这里我们开始引入我们service*/
    @Resource
    private CartService cartServiceImp;

    @ApiOperation("用户添加购物车")
    @PostMapping("/cart/add")
    @ResponseBody
    public ApiRestResponse addCart(@RequestParam("productId") Integer productId, @RequestParam("count") Integer count){
        List<CartVO> cartVOS;
        try {
            cartVOS = cartServiceImp.addCart(UserFilter.current_user.getId(), productId, count);
        } catch (ShopBussinessException e) {
            e.printStackTrace();
            return ApiRestResponse.error(e.getCode(),e.getMessage());
        }
        return ApiRestResponse.success(cartVOS);
    }

    @ApiOperation("用户显示购物车")
    @GetMapping("/cart/list")
    @ResponseBody
    public ApiRestResponse listCart(){
        //内部获取用户ID，防止横向越权--如果我们不是内部获取用户的ID的值的话，那么前端的人员就可以随意的传入用户的id，
        //这个相当于一个十分危险的bug
        List<CartVO> cartVOS = cartServiceImp.listCart(UserFilter.current_user.getId());
        return ApiRestResponse.success(cartVOS);
    }

    @ApiOperation("用户删除购物车")
    @PostMapping("/cart/delete")
    @ResponseBody
    public ApiRestResponse deleteCart(@RequestParam("productId") Integer productId){
        List<CartVO> cartVOS = null;
        try {
            cartVOS = cartServiceImp.deleteCart(UserFilter.current_user.getId(), productId);
        } catch (ShopBussinessException e) {
            e.printStackTrace();
        }
        return ApiRestResponse.success(cartVOS);
    }

    @ApiOperation("用户更新购物车")
    @PostMapping("/cart/update")
    @ResponseBody
    public ApiRestResponse updateCart(@RequestParam("productId") Integer productId,@RequestParam("count") Integer count){
        List<CartVO> cartVOS= null;
        try {
            cartVOS = cartServiceImp.updateCart(UserFilter.current_user.getId(),productId,count);
        } catch (ShopBussinessException e) {
            e.printStackTrace();
        }
        return ApiRestResponse.success(cartVOS);
    }

    /*在这里我们书写了全选和全不选的操作，这个操作，我们可以有效
    * 的改正我们的购物车是否被选中的状态
    * */

    @PostMapping("/cart/select")
    @ApiOperation("选择/不选择购物车的某商品")
    @ResponseBody
    public ApiRestResponse select(@RequestParam("productId") Integer productId, @RequestParam("selected") Integer selected) {
        //不能传入userID，cartID，否则可以删除别人的购物车
        List<CartVO> cartVOList = null;
        try {
            cartVOList = cartServiceImp
                    .selectOrNot(UserFilter.current_user.getId(), productId, selected);
        } catch (ShopBussinessException e) {
            e.printStackTrace();
        }
        return ApiRestResponse.success(cartVOList);
    }

    @PostMapping("/cart/selectAll")
    @ApiOperation("全选择/全不选择购物车的某商品")
    @ResponseBody
    public ApiRestResponse selectAll(@RequestParam("selected") Integer selected) {
        //不能传入userID，cartID，否则可以删除别人的购物车
        List<CartVO> cartVOList = cartServiceImp
                .selectAllOrNot(UserFilter.current_user.getId(), selected);
        return ApiRestResponse.success(cartVOList);
    }
}
