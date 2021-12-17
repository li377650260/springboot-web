package com.tonpower.springbootweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tonpower.springbootweb.dao.CategoryDao;
import com.tonpower.springbootweb.domain.Category;
import com.tonpower.springbootweb.service.CategoryService;
import com.tonpower.springbootweb.vo.CategoryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: li377650260
 * @date: 2021/10/23 17:16
 */

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryDao categoryDao;

    @Override
    public Category findCategoryById(Long categoryId) {
        Category category = categoryDao.selectById(categoryId);
        return category;
    }

    @Override
    public List<CategoryVo> findAll() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Category::getId,Category::getCategoryName);
        List<Category> categories = categoryDao.selectList(queryWrapper);

        return copyList(categories);
    }

    @Override
    public List<CategoryVo> findAllDetail() {
        List<Category> categories = categoryDao.selectList(new LambdaQueryWrapper<>());
        return copyList(categories);
    }

    @Override
    public CategoryVo findCategoryVoById(Long id) {
        Category category = categoryDao.selectById(id);
        return copy(category);
    }

    private List<CategoryVo> copyList(List<Category> categories) {
        List<CategoryVo> categoryVos = new ArrayList<>();
        for (Category c :
                categories) {
            categoryVos.add(copy(c));
        }
        return categoryVos;
    }

    private CategoryVo copy(Category c) {
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(c,categoryVo);
        categoryVo.setId(String.valueOf(c.getId()));
        return categoryVo;
    }

}
