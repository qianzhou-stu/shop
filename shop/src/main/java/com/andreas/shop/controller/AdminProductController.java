package com.andreas.shop.controller;

import com.andreas.shop.common.ApiRestResponse;
import com.andreas.shop.common.Constant;
import com.andreas.shop.exception.ShopBussinessException;
import com.andreas.shop.exception.ShopException;
import com.andreas.shop.pojo.request.AddProductRequest;
import com.andreas.shop.pojo.request.UpdateProductRequest;
import com.andreas.shop.service.ProductService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

/**
 * @Author andreaszhou
 * @ClassName ProductController
 * @Description 商品控制器
 * @date 2021/1/31 14:09
 * @Version 1.0
 */
@RestController
public class AdminProductController {

    @Resource
    private ProductService productServiceImp;

    @ApiOperation("管理员添加商品")
    @PostMapping("/admin/product/add")
    public ApiRestResponse addProduct(@Valid @RequestBody AddProductRequest addProductRequest) {
        try {
            productServiceImp.addProduct(addProductRequest);
        } catch (ShopBussinessException e) {
            e.printStackTrace();
            return ApiRestResponse.error(e.getCode(), e.getMessage());
        }
        return ApiRestResponse.success();
    }

    @ApiOperation("管理员上传商品图片")
    @PostMapping("/admin/upload/file")
    public ApiRestResponse upload(HttpServletRequest httpServletRequest,
                                  @RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //生成文件名称UUID
        UUID uuid = UUID.randomUUID();
        String newFileName = uuid.toString() + suffixName;
        //创建文件夹--创建文件和文件夹的操作
        File fileDirectory = new File(Constant.FILE_UPLOAD_DIR);
        //创建图片文件
        File destFile = new File(Constant.FILE_UPLOAD_DIR + newFileName);

        if (!fileDirectory.exists()) {
            if (!fileDirectory.mkdir()) {
                return ApiRestResponse.error(ShopException.MKDIR_FAILED);
            }
        }
        try {
            file.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return ApiRestResponse
                    .success(getHost(new URI(httpServletRequest.getRequestURL() + "")) + "/images/"
                            + newFileName);
        } catch (URISyntaxException e) {
            return ApiRestResponse.error(ShopException.UPLOAD_FAILED);
        }
    }

    private URI getHost(URI uri) {
        URI effectiveURI;
        try {
            effectiveURI = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(),
                    null, null, null);
        } catch (URISyntaxException e) {
            effectiveURI = null;
        }
        return effectiveURI;
    }

    @ApiOperation("管理员更新商品")
    @PostMapping("/admin/product/update")
    @ResponseBody
    public ApiRestResponse updateProduct(@Valid @RequestBody UpdateProductRequest updateProductRequest) {
        try {
            productServiceImp.updateProduct(updateProductRequest);
        } catch (ShopBussinessException e) {
            e.printStackTrace();
            return ApiRestResponse.error(e.getCode(), e.getMessage());
        }
        return ApiRestResponse.success();
    }

    /*下面我们写删除商品的接口，但是在实际的时候我们很少会使用这个接口，我们一般会使用商品的上架和下架*/
    @ApiOperation("管理员删除商品")
    @PostMapping("/admin/product/delete")
    @ResponseBody
    public ApiRestResponse deleteProduct(@RequestParam("id") Integer id) {
        try {
            productServiceImp.deleteProduct(id);
        } catch (ShopBussinessException e) {
            e.printStackTrace();
            return ApiRestResponse.error(e.getCode(), e.getMessage());
        }
        return ApiRestResponse.success();
    }

    /*接下来我们来写商品的上架和下架的功能*/
    @ApiOperation("管理员上架下架商品")
    @PostMapping("/admin/product/batchUpdateSellStatus")
    @ResponseBody
    public ApiRestResponse batchUpdateSellStatus(@RequestParam Integer[] ids, @RequestParam Integer sellStatus) {
        try {
            productServiceImp.batchUpdateSellStatus(ids, sellStatus);
        } catch (ShopBussinessException e) {
            e.printStackTrace();
            return ApiRestResponse.error(e.getCode(), e.getMessage());
        }
        return ApiRestResponse.success();
    }

    /*
    * 后台商品列表
    * */
    @ApiOperation("后台商品列表接口")
    @PostMapping("/admin/product/list")
    @ResponseBody
    public ApiRestResponse list(@RequestParam("pageNum") Integer pageNum,@RequestParam("pageSize") Integer pageSize){
        if (pageNum==null){
            return ApiRestResponse.error(ShopException.NO_PAGE_NUM);
        }
        if (pageSize==null){
            return ApiRestResponse.error(ShopException.NO_PAGE_SIZE);
        }
        PageInfo pageInfo = productServiceImp.listForAdmin(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }
}
