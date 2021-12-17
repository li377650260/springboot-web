package com.tonpower.springbootweb.service;

import com.tonpower.springbootweb.domain.Tag;
import com.tonpower.springbootweb.vo.TagVo;

import java.util.List;

/**
 * @description:
 * @author: li377650260
 * @date: 2021/10/14 22:12
 */
public interface TagService {

    /**
     * 通过文章id查询文章的标签功能
     * @param articleId
     * @return
     */
    List<TagVo> findTagsByArticleId(Long articleId);

    /**
     * 获取最热门标签列表
     * @param limit
     * @return
     */
    List<Tag> hots(int limit);

    List<TagVo> findAll();

    List<TagVo> findAllDetail();

    TagVo findAllDetailById(Long id);
}
