package com.andreas.shop.controller;

import com.andreas.shop.common.ApiRestResponse;
import com.andreas.shop.exception.ShopBussinessException;
import com.andreas.shop.exception.ShopException;
import com.andreas.shop.pojo.User;
import com.andreas.shop.service.UserService;
import com.mysql.cj.util.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @Author andreaszhou
 * @ClassName UserController
 * @Description TODO
 * @date 2021/1/26 18:53
 * @Version 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userServiceImp;

    /*
     * 这个只是测试使用的
     * */
    @ApiOperation("测试")
    @GetMapping("/user")
    public String getUser(@RequestParam("id") Integer id) {
        User user = userServiceImp.getUser(id);
        return user.toString();
    }

    /*
     * 在这里我们开始注册
     * */
    @ApiOperation("用户注册")
    @PostMapping("register")
    @ResponseBody
    public ApiRestResponse register(@RequestParam("username") String username, @RequestParam("password") String password) {
        if (StringUtils.isNullOrEmpty(username)) {
            return ApiRestResponse.error(ShopException.NEED_USER_NAME);
        }
        if (StringUtils.isNullOrEmpty(password)) {
            return ApiRestResponse.error(ShopException.NEED_PASSWORD);
        }

        if (password.length() < 8) {
            return ApiRestResponse.error(ShopException.PASSWORD_TOO_SHORT);
        }
        try {
            userServiceImp.registerUser(username, password);
        } catch (ShopBussinessException e) {
            e.printStackTrace();
            return ApiRestResponse.error(e.getCode(), e.getMessage());
        }
        return ApiRestResponse.success();
    }

    /*
     * 在这里开始做登录的操作。这个是后端登录的接口的处理的方法
     * */
    @ApiOperation("用户登录")
    @PostMapping("/login")
    @ResponseBody
    public ApiRestResponse login(String username, String password, HttpSession session) {
        if (StringUtils.isNullOrEmpty(username)) {
            return ApiRestResponse.error(ShopException.NEED_USER_NAME);
        }
        if (StringUtils.isNullOrEmpty(password)) {
            return ApiRestResponse.error(ShopException.NEED_PASSWORD);
        }
        User user;
        try {
            user = userServiceImp.checkLogin(username, password);
            user.setPassword(null);
            session.setAttribute("user", user);
        } catch (ShopBussinessException e) {
            e.printStackTrace();
            return ApiRestResponse.error(e.getCode(), e.getMessage());
        }
        return ApiRestResponse.success(user);
    }

    /*
     * 在这里我们开始做签名的更新操作
     * */
    @ApiOperation("更新用户签名")
    @PostMapping("/updateSignature")
    @ResponseBody
    public ApiRestResponse signature(String content, HttpSession session) {

        if (StringUtils.isNullOrEmpty(content)) {
            return ApiRestResponse.error(ShopException.NO_SIGNATURE);
        }
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ApiRestResponse.error(ShopException.NEED_LOGIN);
        }
        try {
            userServiceImp.updateSignature(user, content);
        } catch (ShopBussinessException e) {
            e.printStackTrace();
            return ApiRestResponse.error(e.getCode(), e.getMessage());
        }
        return ApiRestResponse.success();
    }


    /*
     * 在这里我们开始做用户退出的操作
     * */
    @ApiOperation("用户退出")
    @PostMapping("logout")
    @ResponseBody
    public ApiRestResponse logout(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ApiRestResponse.error(ShopException.NEED_LOGIN);
        }
        session.removeAttribute("user");
        return ApiRestResponse.success();
    }

    /*
    * 管理员登录的开发
    * */
    @ApiOperation("管理员登录")
    @PostMapping("admin")
    @ResponseBody
    public ApiRestResponse admin_login(HttpSession session, @RequestParam("username") String username, @RequestParam("password") String password) {
        if (StringUtils.isNullOrEmpty(username)) {
            return ApiRestResponse.error(ShopException.NEED_USER_NAME);
        }
        if (StringUtils.isNullOrEmpty(password)) {
            return ApiRestResponse.error(ShopException.NEED_PASSWORD);
        }
        User user;
        try {
            user = userServiceImp.checkLogin(username, password);
        } catch (ShopBussinessException e) {
            e.printStackTrace();
            return ApiRestResponse.error(e.getCode(), e.getMessage());
        }
        Boolean is_admin = userServiceImp.isAdmin(user);
        if (!is_admin) {
            return ApiRestResponse.error(ShopException.IS_ADMIN);
        }
        user.setPassword(null);
        session.setAttribute("user", user);
        return ApiRestResponse.success(user);
    }
}
