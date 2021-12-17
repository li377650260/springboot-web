package com.tonpower.springbootweb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tonpower.springbootweb.domain.ArticleTag;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleTagDao extends BaseMapper<ArticleTag> {
}
