package com.tonpower.springbootweb.controller;

import com.tonpower.springbootweb.aop.cache.Cache;
import com.tonpower.springbootweb.aop.comment.LogAnnotation;
import com.tonpower.springbootweb.domain.Article;
import com.tonpower.springbootweb.domain.dos.Archives;
import com.tonpower.springbootweb.service.ArticleService;
import com.tonpower.springbootweb.vo.ArticleVo;
import com.tonpower.springbootweb.vo.Result;
import com.tonpower.springbootweb.vo.params.ArticleParam;
import com.tonpower.springbootweb.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: li377650260
 * @date: 2021/10/12 22:26
 */

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    /**
     * 首页文章列表刷新
     * @param pageParams
     * @return
     */
    @LogAnnotation(model="文章",operator = "获取文章列表")
//    @Cache(expire = 5*60*1000,name = "获取文章列表")
    @RequestMapping
    public Result pageList(@RequestBody PageParams pageParams){
        pageParams.setPageSize(10);
        return articleService.listArticle(pageParams);

    }

    @Cache(expire = 3*60*1000,name = "hot_article")
    @LogAnnotation(model = "文章",operator = "获取最热文章标题")
    @RequestMapping("/hot")
    public Result hot(){
        Result hots;
        int limit = 3;
        // 业务层查询最热文章列表
        List<ArticleVo> articles = articleService.hosts(limit);
        hots = Result.success(articles);
        return hots;
    }
    @Cache(expire = 5*60*1000,name = "new_article")
    @RequestMapping("/new")
    public Result newArticles(){
        Result hots;
        int limit = 3;
        // 业务层查询最热文章列表
        List<ArticleVo> articles = articleService.newArticles(limit);
        hots = Result.success(articles);
        return hots;
    }

    @RequestMapping("/listArchives")
    public Result listArchives(){
        List<Archives> archivesList = articleService.listArchives();
        return Result.success(archivesList);
    }

//    @Cache(expire = 5*60*1000,name = "article_detail")
    // ms 77
    // ms 5
    @LogAnnotation(model = "文章",operator = "获取文章详情")
    @PostMapping("/view/{id}")
    public Result view(@PathVariable("id") Long id){
        ArticleVo articleVo =articleService.findArticleById(id);
        return Result.success(articleVo);
    }

    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam){
        Map save = articleService.save(articleParam);
        return Result.success(save);
    }
}
