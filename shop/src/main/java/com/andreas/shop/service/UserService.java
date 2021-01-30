package com.andreas.shop.service;

import com.andreas.shop.exception.ShopBussinessException;
import com.andreas.shop.pojo.User;

import java.security.NoSuchAlgorithmException;

/**
 * @Author andreaszhou
 * @ClassName UserService
 * @Description TODO
 * @date 2021/1/26 18:53
 * @Version 1.0
 */
public interface UserService {
    /*这个是用来测试的*/
    User getUser(Integer id);
    /*注册*/
    void registerUser(String username,String password) throws ShopBussinessException;
    /*登录*/
    User checkLogin(String username,String password) throws ShopBussinessException;
    void updateSignature(User user,String content) throws ShopBussinessException;

    Boolean isAdmin(User user);
}
