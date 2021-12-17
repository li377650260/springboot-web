package com.tonpower.springbootweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tonpower.springbootweb.dao.CommentsDao;
import com.tonpower.springbootweb.dao.SysUserDao;
import com.tonpower.springbootweb.domain.Comment;
import com.tonpower.springbootweb.domain.SysUser;
import com.tonpower.springbootweb.service.ArticleService;
import com.tonpower.springbootweb.service.CommentsService;
import com.tonpower.springbootweb.service.SysUserService;
import com.tonpower.springbootweb.utils.UserThreadLocal;
import com.tonpower.springbootweb.vo.ArticleVo;
import com.tonpower.springbootweb.vo.CommentVo;
import com.tonpower.springbootweb.vo.UserVo;
import com.tonpower.springbootweb.vo.params.CommentParam;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: li377650260
 * @date: 2021/10/24 19:28
 */

@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    CommentsDao commentsDao;
    @Autowired
    SysUserService sysUserService;
    @Autowired
    ArticleService articleService;

    @Override
    public List<CommentVo> findCommentsByArticleId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId,id);
        queryWrapper.eq(Comment::getLevel,1);
        List<Comment> comments = commentsDao.selectList(queryWrapper);
        List<CommentVo> commentVos = copyList(comments);
        return commentVos;
    }

    @Transactional
    @Override
    public int save(CommentParam commentParam) {
        SysUser sysUser = UserThreadLocal.get();
        Comment comment = new Comment();
        comment.setArticleId(commentParam.getArticleId());
        comment.setAuthorId(sysUser.getId());
        comment.setContent(commentParam.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent = commentParam.getParent();
        if (parent == null || parent == 0){
            comment.setLevel(1);
        }else {
            comment.setLevel(2);
        }
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        int insert = this.commentsDao.insert(comment);
        if (insert == 1){
            articleService.updateCommentCountById(commentParam.getArticleId());
        }
        return insert;
    }

    private List<CommentVo> copyList(List<Comment> comments) {
        List<CommentVo> commentVos = new ArrayList<>();
        for (Comment comment : comments){
            commentVos.add(copy(comment));
        }
        return commentVos;
    }

    private CommentVo copy(Comment comment) {
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment,commentVo);
        commentVo.setId(String.valueOf(comment.getId()));
        // 创建时间
        commentVo.setCreateDate(new DateTime(comment.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        // 作者信息
        Long authorId = comment.getAuthorId();
        UserVo userVo = sysUserService.findUserVoById(authorId);
        commentVo.setAuthor(userVo);
        // 子评论
        Integer level = comment.getLevel();
        if (level == 1){
            Long id = comment.getId();
            List<CommentVo> commentVos = findCommentsByParentId(id);
            commentVo.setChildrens(commentVos);
        }
        // toUser
        if (level > 1){
            Long toUid = comment.getToUid();
            UserVo userVoById = this.sysUserService.findUserVoById(toUid);
            commentVo.setToUser(userVoById);
        }
        return commentVo;
    }

    private List<CommentVo> findCommentsByParentId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId,id);
        queryWrapper.eq(Comment::getLevel,2);
        List<Comment> comments = this.commentsDao.selectList(queryWrapper);
        return this.copyList(comments);
    }
}
