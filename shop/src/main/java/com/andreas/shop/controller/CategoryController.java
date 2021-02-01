package com.andreas.shop.controller;

import com.andreas.shop.common.ApiRestResponse;
import com.andreas.shop.pojo.vo.CategoryVO;
import com.andreas.shop.exception.ShopBussinessException;
import com.andreas.shop.exception.ShopException;
import com.andreas.shop.pojo.request.AddCategoryRequest;
import com.andreas.shop.pojo.request.UpdateCategoryRequest;
import com.andreas.shop.service.CategoryService;
import com.andreas.shop.service.UserService;
import com.github.pagehelper.PageInfo;
import com.mysql.cj.util.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * @Author andreaszhou
 * @ClassName CategoryController
 * @Description TODO
 * @date 2021/1/28 8:52
 * @Version 1.0
 */
@RestController
public class CategoryController {
    @Resource
    CategoryService categoryServiceImp;
    @Resource
    UserService userServiceImp;

    /*
    * 后台得分类得管理
    * */
    /*
    * 1.添加分类
    * */
    @ApiOperation("管理员添加分类")
    @PostMapping("/admin/category/add")
    @ResponseBody
    /*记住校验参数的技巧，必须在前面加上@Vaild注解，不然是没有用的。我们写的校验注解的方式就会出现问题*/
    public ApiRestResponse addCategory(HttpSession session, @Valid @RequestBody AddCategoryRequest addCategoryRequest){
        /*
        * 下面是一些值的属性的判断
        * */
        if (StringUtils.isNullOrEmpty(addCategoryRequest.getName())){
            return ApiRestResponse.error(ShopException.NO_CATEGORY_NAME);
        }
        if (addCategoryRequest.getType()==null){
            return ApiRestResponse.error(ShopException.NO_CATEGORY_TYPE);
        }
        if (addCategoryRequest.getParentId()==null){
            return ApiRestResponse.error(ShopException.NO_CATEGORY_PARENT_ID);
        }
        if (addCategoryRequest.getOrderNum()==null){
            return ApiRestResponse.error(ShopException.NO_CATEGORY_ORDER);
        }
//        User admin_user = (User) session.getAttribute("user");
//        if (admin_user==null){
//            return ApiRestResponse.error(ShopException.NEED_LOGIN);
//        }
//
//        /*
//        * 因为这个是我们的后台的管理。所以我们在这里需要判断用户是不是管理员
//        * 如果不是管理员，我们是不能进行操作的。
//        * */
//        Boolean is_admin = userServiceImp.isAdmin(admin_user);
//        if (!is_admin){
//            return ApiRestResponse.error(ShopException.IS_ADMIN);
//        }
        try {
            categoryServiceImp.addCategory(addCategoryRequest);
        } catch (ShopBussinessException e) {
            e.printStackTrace();
            return ApiRestResponse.error(e.getCode(),e.getMessage());
        }
        return ApiRestResponse.success();
    }


    /*
    * 后台管理更新分类
    *
    * */
    @ApiOperation("管理员更新分类")
    @PostMapping("/admin/category/update")
    @ResponseBody
    /*Required request body is missing:表示的是我们前端没有传入参数，导致我们的@RequestBody注解没有获取到值*/
    public ApiRestResponse updateCategory(HttpSession session, @Valid @RequestBody  UpdateCategoryRequest updateCategoryRequest){

//        User admin_user = (User)session.getAttribute("user");
//        if (admin_user==null){
//            return ApiRestResponse.error(ShopException.NEED_LOGIN);
//        }
//        Boolean is_admin = userServiceImp.isAdmin(admin_user);
//        if (!is_admin){
//            return ApiRestResponse.error(ShopException.IS_ADMIN);
//        }
        try {
            categoryServiceImp.updateCategory(updateCategoryRequest);
        } catch (ShopBussinessException e) {
            e.printStackTrace();
            return ApiRestResponse.error(e.getCode(),e.getMessage());
        }
        return ApiRestResponse.success();
    }

    @ApiOperation("后台删除目录")
    @PostMapping("admin/category/delete")
    @ResponseBody
    public ApiRestResponse deleteCategory(@RequestParam("id") Integer id){
        try {
            categoryServiceImp.delete(id);
        } catch (ShopBussinessException e) {
            e.printStackTrace();
            return ApiRestResponse.error(e.getCode(),e.getMessage());
        }
        return ApiRestResponse.success();
    }

    @ApiOperation("后台目录列表")
    @PostMapping("admin/category/list")
    @ResponseBody
    public ApiRestResponse listCategoryForAdmin(@RequestParam("pageNum") Integer pageNum,@RequestParam("pageSize") Integer pageSize){
        if (pageNum==null){
            return ApiRestResponse.error(ShopException.NO_PAGE_NUM);
        }
        if (pageSize==null){
            return ApiRestResponse.error(ShopException.NO_PAGE_SIZE);
        }
        PageInfo pageInfo = categoryServiceImp.listForAdmin(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }


    @ApiOperation("前台目录列表")
    @PostMapping("category/list")
    @ResponseBody
    public ApiRestResponse listCategoryForCustomer() {
        List<CategoryVO> categoryVOS = categoryServiceImp.listCategoryForCustomer(0);
        return ApiRestResponse.success(categoryVOS);
    }
}
