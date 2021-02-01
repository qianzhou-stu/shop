package com.andreas.shop.serviceImp;

import com.andreas.shop.common.Constant;
import com.andreas.shop.dao.ProductMapper;
import com.andreas.shop.exception.ShopBussinessException;
import com.andreas.shop.exception.ShopException;
import com.andreas.shop.pojo.Product;
import com.andreas.shop.pojo.query.ProductListQuery;
import com.andreas.shop.pojo.request.AddProductRequest;
import com.andreas.shop.pojo.request.ListProductRequest;
import com.andreas.shop.pojo.request.UpdateProductRequest;
import com.andreas.shop.pojo.vo.CategoryVO;
import com.andreas.shop.service.CategoryService;
import com.andreas.shop.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author andreaszhou
 * @ClassName ProductServiceImpl
 * @Description 商品实现类--难点在于前端列表的显示
 * @date 2021/1/31 14:15
 * @Version 1.0
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductMapper productMapper;
    @Resource
    private CategoryService categoryServiceImp;

    @Override
    public void addProduct(AddProductRequest addProductRequest) throws ShopBussinessException {
        String name = addProductRequest.getName();
        Product target_product = productMapper.selectByName(name);
        if (target_product != null) {
            throw new ShopBussinessException(ShopException.PRODUCT_EXIST);
        }
        Product product = new Product();
        BeanUtils.copyProperties(addProductRequest, product);
        int i = productMapper.insertSelective(product);
        if (i == 0) {
            throw new ShopBussinessException(ShopException.INSERT_FAILED);
        }
    }

    @Override
    public void updateProduct(UpdateProductRequest updateProductRequest) throws ShopBussinessException {
        Product target_product = productMapper.selectByName(updateProductRequest.getName());
        /*同名不同id*/
        //同名且不同id，不能继续修改。同名的时候，查看id是不是一样。

        // 如果是名字不一样，target_product == null ,那么下面就不用执行了
        // 如果传入的名字是一样的，那么target_product!=null, 同名的时候我们的ID一定是要相同的，因为我们不存在id不同的
        // 同名的商品。因为商品名字也是唯一的。
        if (target_product != null && !target_product.getId().equals(updateProductRequest.getId())) {
            throw new ShopBussinessException(ShopException.NAME_EXISTED);
        }
        Product product = new Product();
        BeanUtils.copyProperties(updateProductRequest, product);
        int i = productMapper.updateByPrimaryKeySelective(product);
        if (i == 0) {
            throw new ShopBussinessException(ShopException.UPDATE_FAILED);
        }
    }

    @Override
    public void deleteProduct(Integer id) throws ShopBussinessException {
        int i = productMapper.deleteByPrimaryKey(id);
        if (i == 0) {
            throw new ShopBussinessException(ShopException.DELETE_FAILED);
        }
    }

    @Override
    public void batchUpdateSellStatus(Integer[] ids, Integer sellStatus) throws ShopBussinessException {
        int i = productMapper.batchUpdateSellStatus(ids, sellStatus);
        if (i == 0) {
            throw new ShopBussinessException(ShopException.UPDATE_FAILED);
        }
    }

    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Product> productList = productMapper.selectListForAdmin();
        PageInfo pageInfo = new PageInfo(productList);
        return pageInfo;
    }

    @Override
    public Product detail(Integer id) throws ShopBussinessException {
        Product product = productMapper.selectByPrimaryKey(id);
        if (product == null) {
            throw new ShopBussinessException(ShopException.NO_ID);
        }
        return product;
    }
    /*这个代码之后写三遍，这个代码之后写三遍*/
    @Override
    public PageInfo listForCustom(ListProductRequest listProductRequest) {
        // 构建Query对象
        ProductListQuery productListQuery = new ProductListQuery();
        // 搜索处理
        if (!StringUtils.isNullOrEmpty(listProductRequest.getKeyword())) {
            String keyword = String.valueOf(new StringBuilder().append("%").append(listProductRequest.getKeyword()).append("%"));
            productListQuery.setKeyword(keyword);
        }
        //目录处理：如果查某个目录下的商品，不仅是需要查出该目录下的，还要把所有子目录的所有商品都查出来，所以要拿到一个目录id的List

        if (listProductRequest.getCategoryId() != null) {
            /*使用递归的方式获取到所有的categoryVOList，这个递归的方法在前面我们已经写好了，所以在这里我们就不需要怎么去写了
            * 直接调用这个方法
            * */
            List<CategoryVO> categoryVOList = categoryServiceImp.listCategoryForCustomer(listProductRequest.getCategoryId());
            ArrayList<Integer> categoryIds = new ArrayList<>();
            categoryIds.add(listProductRequest.getCategoryId());
            getCategoryIds(categoryVOList, categoryIds);
            productListQuery.setCategoryIds(categoryIds);
        }

        String orderBy = listProductRequest.getOrderBy();
        if (Constant.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)) {
            PageHelper
                    .startPage(listProductRequest.getPageNum(), listProductRequest.getPageSize(), String.valueOf(orderBy));
        } else {
            PageHelper.startPage(listProductRequest.getPageNum(), listProductRequest.getPageSize());
        }
        List<Product> productList = productMapper.selectList(productListQuery);

        PageInfo pageInfo = new PageInfo(productList);
        return pageInfo;
    }

    private void getCategoryIds(List<CategoryVO> categoryVOList, ArrayList<Integer> categoryIds) {
        /*categoryVOList.size()只能for循环第一层的内容，对于里面的childCategory我们需要使用递归的方式才可以继续的获取到*/
        for (int i = 0; i < categoryVOList.size(); i++) {
            CategoryVO categoryVO = categoryVOList.get(i);
            if (categoryVO != null) {
                categoryIds.add(categoryVO.getId());
                getCategoryIds(categoryVO.getChildCategory(), categoryIds);
            }
        }
    }
}
