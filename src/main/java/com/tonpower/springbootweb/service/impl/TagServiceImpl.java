package com.tonpower.springbootweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tonpower.springbootweb.dao.TagDao;
import com.tonpower.springbootweb.domain.Tag;
import com.tonpower.springbootweb.service.TagService;
import com.tonpower.springbootweb.vo.Result;
import com.tonpower.springbootweb.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @description:
 * @author: li377650260
 * @date: 2021/10/14 22:13
 */

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagDao tagDao;

    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        List<Tag> tagsByArticleId = tagDao.findTagsByArticleId(articleId);
        return copyList(tagsByArticleId);
    }

    /**
     * 查询热度最高的标签列表，根据tag_id查询所有标签下文章的数量计数，从大到小进行降序排列，取前limit个
     * @param limit
     * @return
     */
    @Override
    public List<Tag> hots(int limit) {
        List<Long> list = tagDao.findTagsByTagId(limit);
        if (list == null){
            return Collections.emptyList();
        }
        List<Tag> tagList = tagDao.findTagsByTagIds(list);
        return tagList;
    }

    @Override
    public List<TagVo> findAll() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Tag::getId,Tag::getTagName);
        List<Tag> tags = tagDao.selectList(queryWrapper);
        return copyList(tags);
    }

    @Override
    public List<TagVo> findAllDetail() {
        List<Tag> tags = tagDao.selectList(new LambdaQueryWrapper<Tag>());
        return copyList(tags);
    }

    @Override
    public TagVo findAllDetailById(Long id) {
        Tag tag = tagDao.selectById(id);
        return copy(tag);
    }


    private List<TagVo> copyList(List<Tag> tagsByArticleId) {
        List<TagVo> tagVoList= new ArrayList<>();
        for (Tag tag:tagsByArticleId){
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

    private TagVo copy(Tag tag) {
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        tagVo.setId(String.valueOf(tag.getId()));
        return tagVo;
    }
}
