package com.tonpower.springbootweb.service;


import com.tonpower.springbootweb.domain.Category;
import com.tonpower.springbootweb.vo.CategoryVo;

import java.util.List;

public interface CategoryService {


    Category findCategoryById(Long categoryId);

    List<CategoryVo> findAll();

    List<CategoryVo> findAllDetail();

    CategoryVo findCategoryVoById(Long id);
}
