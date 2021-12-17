package com.tonpower.springbootweb.service;

import com.tonpower.springbootweb.vo.CommentVo;
import com.tonpower.springbootweb.vo.params.CommentParam;

import java.util.List;

public interface CommentsService {

    /**
     * 根据文章id来查询所有的评论列表
     * @param id
     * @return
     */
    List<CommentVo> findCommentsByArticleId(Long id);

    int save(CommentParam commentParam);
}
