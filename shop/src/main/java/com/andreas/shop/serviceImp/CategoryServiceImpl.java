package com.andreas.shop.serviceImp;

import com.andreas.shop.dao.CategoryMapper;
import com.andreas.shop.exception.ShopBussinessException;
import com.andreas.shop.exception.ShopException;
import com.andreas.shop.pojo.Category;
import com.andreas.shop.pojo.vo.CategoryVO;
import com.andreas.shop.request.AddCategoryRequest;
import com.andreas.shop.request.UpdateCategoryRequest;
import com.andreas.shop.service.CategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author andreaszhou
 * @ClassName CategoryServiceImpl
 * @Description TODO
 * @date 2021/1/28 9:05
 * @Version 1.0
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public void addCategory(AddCategoryRequest addCategoryRequest) throws ShopBussinessException {
        /*
         * 在这里判断分类的名称是不是已经存在了，存在了的话，我们就不能对这个进行操作了。
         * */
        String name = addCategoryRequest.getName();
        Category target_category = categoryMapper.selectByName(name);
        if (target_category != null) {
            throw new ShopBussinessException(ShopException.CATEGORY_EXIST);
        }
        Category category = new Category();
//        category.setName(addCategoryRequest.getName());
//        category.setType(addCategoryRequest.getType());
//        category.setParentId(addCategoryRequest.getType());
//        category.setOrderNum(addCategoryRequest.getOrderNum());
        // 上面这个方法是可以的，但是这样写的话未免可能有的时候有一些的麻烦。
        // 我们是不是有一种更快的方法来写这个接口的相关的属性的赋值呢
        /*这句话的意思就是将addCategoryRequest中的属性的值copy到category中*/
        /*下面的这一段代码就是不让我们重复的书写get 和 set的相关的方法*/
        BeanUtils.copyProperties(addCategoryRequest, category);
        int i = categoryMapper.insertSelective(category);
        if (i == 0) {
            throw new ShopBussinessException(ShopException.INSERT_FAILED);
        }
    }

    @Override
    public void updateCategory(UpdateCategoryRequest updateCategoryRequest) throws ShopBussinessException {
        Integer categoryId = updateCategoryRequest.getId();
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category == null) {
            throw new ShopBussinessException(ShopException.IS_CATEGORY);
        }
        BeanUtils.copyProperties(updateCategoryRequest, category);
        int i = categoryMapper.updateByPrimaryKeySelective(category);
        if (i == 0) {
            throw new ShopBussinessException(ShopException.UPDATE_CATEGORY_FAILURE);
        }
    }

    @Override
    public void delete(Integer id) throws ShopBussinessException {
        Category category = categoryMapper.selectByPrimaryKey(id);
        if (category == null) {
            throw new ShopBussinessException(ShopException.IS_CATEGORY);
        }
        int i = categoryMapper.deleteByPrimaryKey(id);
        if (i == 0) {
            throw new ShopBussinessException(ShopException.DELETE_FAILED);
        }
    }

    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize, "type, order_num");
        List<Category> categoryList = categoryMapper.selectList();
        PageInfo pageInfo = new PageInfo(categoryList);
        return pageInfo;
    }

    @Override
    @Cacheable(value = "listCategoryForCustomer")
    public List<CategoryVO> listCategoryForCustomer() {
        ArrayList<CategoryVO> categoryVOList = new ArrayList<>();
        recursivelyFindCategories(categoryVOList, 0);
        return categoryVOList;
    }

    private void recursivelyFindCategories(List<CategoryVO> categoryVOList, Integer parentId) {
        /*递归获取所有子类别，并组合成为一个“目录树*/
        /*
        * 这是一个递归实现的目录树，递归结束的条件是，当我们的categoryMapper.selectCategoriesByParentId(parentId)
        * 查找不到的时候，我们的递归就会结束。并不会无限的循环下去。这个代码是完全正确的代码。
        * 在这里我们要好好的学习和使用这个代码，因为这个代码真的太重要了。在这里我们要好好的学习和使用这一段代码。
        * */
        List<Category> categoryList = categoryMapper.selectCategoriesByParentId(parentId);
        if (!CollectionUtils.isEmpty(categoryList)) {
            for (int i = 0; i < categoryList.size(); i++) {
                Category category = categoryList.get(i);
                CategoryVO categoryVO = new CategoryVO();
                BeanUtils.copyProperties(category, categoryVO);
                categoryVOList.add(categoryVO);
                recursivelyFindCategories(categoryVO.getChildCategory(),categoryVO.getId());
            }
        }
    }
}
