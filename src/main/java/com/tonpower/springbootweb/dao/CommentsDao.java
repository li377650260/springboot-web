package com.tonpower.springbootweb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tonpower.springbootweb.domain.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentsDao extends BaseMapper<Comment> {
}
