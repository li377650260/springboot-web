package com.tonpower.springbootweb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tonpower.springbootweb.domain.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagDao extends BaseMapper<Tag> {

    /**
     * 根据文章列表查询标签列表
     * @param articleId
     * @return
     */
    List<Tag> findTagsByArticleId(Long articleId);

    /**
     * 查询最热标签，取前n条
     * @param limit
     * @return
     */
    List<Long> findTagsByTagId(int limit);

    List<Tag> findTagsByTagIds(List<Long> list);
}
