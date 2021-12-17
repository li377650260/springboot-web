package com.tonpower.springbootweb.controller;

import com.tonpower.springbootweb.domain.Category;
import com.tonpower.springbootweb.service.CategoryService;
import com.tonpower.springbootweb.vo.CategoryVo;
import com.tonpower.springbootweb.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description:
 * @author: li377650260
 * @date: 2021/10/25 16:21
 */

@RestController
@RequestMapping("/api/categorys")
public class CateGoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping
    public Result categories(){
        List<CategoryVo> categoryVos = categoryService.findAll();
        return Result.success(categoryVos);
    }

    @GetMapping("/detail")
    public Result categoriesDetail(){
        List<CategoryVo> categoryVos = categoryService.findAllDetail();
        return Result.success(categoryVos);
    }

    @GetMapping("/detail/{id}")
    public Result categoryById(@PathVariable("id") Long id){
        CategoryVo categoryVo = categoryService.findCategoryVoById(id);
        return Result.success(categoryVo);
    }
}
