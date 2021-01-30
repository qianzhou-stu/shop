package com.andreas.shop.serviceImp;

import com.andreas.shop.dao.UserMapper;
import com.andreas.shop.exception.ShopBussinessException;
import com.andreas.shop.exception.ShopException;
import com.andreas.shop.pojo.User;
import com.andreas.shop.service.UserService;
import com.andreas.shop.utils.Md5Utils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * @Author andreaszhou
 * @ClassName UserServiceImp
 * @Description TODO
 * @date 2021/1/26 18:53
 * @Version 1.0
 */
@Service
public class UserServiceImp implements UserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public User getUser(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public void registerUser(String username,String password) throws ShopBussinessException {
        /*
        * 1.用户名不能重名。做重名的判断
        * */
        User result = userMapper.selectByUserName(username);
        if (result!=null){
            throw new ShopBussinessException(ShopException.NAME_EXISTED);
        }
        /*
        * 2.开始书写注册逻辑
        * */
        User user = new User();
        /*
         * 在这里我们还要做MD5加密的操作
         * */
        String target_password = null;
        try {
            target_password = Md5Utils.md5Utils(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        user.setUsername(username);
        user.setPassword(target_password);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        int i = userMapper.insertSelective(user);
        if (i==0){
            throw new ShopBussinessException(ShopException.INSERT_FAILED);
        }
    }

    @Override
    public User checkLogin(String username, String password) throws ShopBussinessException{
        User user = userMapper.selectByUserName(username);
        if (user==null){
            throw new ShopBussinessException(ShopException.NO_USER_EXISTED);
        }
        String target_password = null;
        try {
            target_password = Md5Utils.md5Utils(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (!target_password.equals(user.getPassword())){
            throw new ShopBussinessException(ShopException.WRONG_PASSWORD);
        }
        return user;
    }


    /*
    * 跟新签名的操作
    * */
    @Override
    public void updateSignature(User user,String content) throws ShopBussinessException {
        User target = userMapper.selectByPrimaryKey(user.getId());
        target.setPersonalizedSignature(content);
        int i = userMapper.updateByPrimaryKeySelective(target);
        if (i==0){
            throw new ShopBussinessException(ShopException.UPDATE_FAILED);
        }
    }

    /*
    * logout：用户退出的操作
    * */


    /*
    * 判读是不是管理员的操作
    * */

    @Override
    public Boolean isAdmin(User user) {
        Integer role = user.getRole();
        boolean is_admin = role.equals(2);
        return is_admin;
    }
}
