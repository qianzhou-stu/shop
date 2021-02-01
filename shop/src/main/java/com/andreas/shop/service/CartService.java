package com.andreas.shop.service;

import com.andreas.shop.exception.ShopBussinessException;
import com.andreas.shop.pojo.User;
import com.andreas.shop.pojo.vo.CartVO;

import java.util.List;

/**
 * @Author andreaszhou
 * @ClassName CartService
 * @Description TODO
 * @date 2021/2/1 15:13
 * @Version 1.0
 */
public interface CartService {
    List<CartVO> addCart(Integer userId, Integer productId, Integer count) throws ShopBussinessException;

    List<CartVO> listCart(Integer userId);

    List<CartVO> deleteCart(Integer userId, Integer productId) throws ShopBussinessException;

    List<CartVO> updateCart(Integer userId, Integer productId, Integer count) throws ShopBussinessException;

    List<CartVO> selectOrNot(Integer userId, Integer productId, Integer selected) throws ShopBussinessException;

    List<CartVO> selectAllOrNot(Integer userId, Integer selected);
}
