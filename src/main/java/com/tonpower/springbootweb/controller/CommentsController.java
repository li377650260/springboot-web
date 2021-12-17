package com.tonpower.springbootweb.controller;

import com.tonpower.springbootweb.domain.Comment;
import com.tonpower.springbootweb.service.CommentsService;
import com.tonpower.springbootweb.vo.CommentVo;
import com.tonpower.springbootweb.vo.Result;
import com.tonpower.springbootweb.vo.params.CommentParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description:
 * @author: li377650260
 * @date: 2021/10/24 19:26
 */

@RestController
@RequestMapping("/api/comments")
public class CommentsController {

    @Autowired
    CommentsService commentsService;

    @GetMapping("/article/{id}")
    public Result comments(@PathVariable("id") Long id){
        List<CommentVo> comments = commentsService.findCommentsByArticleId(id);

        return Result.success(comments);
    }

    @PostMapping("/create/change")
    public Result create(@RequestBody CommentParam commentParam){
        int count = commentsService.save(commentParam);
        if (count  > 0){
            return Result.success("评论成功");
        }else {
            return Result.fail(5000,"评论失败");
        }
    }
}
