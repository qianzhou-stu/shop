package com.andreas.shop.dao;

import com.andreas.shop.pojo.Category;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    Category selectByName(@Param("name") String name);

    List<Category> selectList();

    List<Category> selectCategoriesByParentId(@Param("parentId") Integer parentId);
}