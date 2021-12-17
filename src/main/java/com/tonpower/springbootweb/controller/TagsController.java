package com.tonpower.springbootweb.controller;

import com.tonpower.springbootweb.domain.Tag;
import com.tonpower.springbootweb.service.TagService;
import com.tonpower.springbootweb.vo.Result;
import com.tonpower.springbootweb.vo.TagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description:
 * @author: li377650260
 * @date: 2021/10/19 17:42
 */

@RestController
@RequestMapping("/api/tags")
public class TagsController {

    @Autowired
    TagService tagService;

    @RequestMapping("/hot")
    public Result hot(){
        Result hots;
        // 最热标签数量
        int limit = 6;
        // 业务层查询最热标签列表
        List<Tag> list = tagService.hots(limit);
        hots = Result.success(list);
        return hots;
    }

    @GetMapping("")
    public Result findAll(){
        List<TagVo> tagVos = tagService.findAll();
        return Result.success(tagVos);
    }

    @GetMapping("/detail")
    public Result detail(){
        List<TagVo> tagVos =tagService.findAllDetail();
        return Result.success(tagVos);
    }
    @GetMapping("/detail/{id}")
    public Result detailById(@PathVariable("id") Long id){
        TagVo tagVo =tagService.findAllDetailById(id);
        return Result.success(tagVo);
    }

}
