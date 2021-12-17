package com.tonpower.springbootweb.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tonpower.springbootweb.dao.ArticleDao;
import com.tonpower.springbootweb.domain.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description:
 * @author: li377650260
 * @date: 2021/10/24 14:47
 */

@Service
@Transactional
public class ThreadService {

    @Async("asyncServiceExecutor")
    public void updateArticleViewCounts(ArticleDao articleDao, Article article){
        Integer viewCounts = article.getViewCounts();
        Article updateArticle = new Article();
        updateArticle.setViewCounts(viewCounts + 1);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<Article>();
        queryWrapper.eq(Article::getId,article.getId());
        // 在做更新之前需要查询更新字段是否是之前的数据，避免多线程对数据多次改变，确保在、
        // 多线程的环境下线程安全问题（乐观锁机制）
        queryWrapper.eq(Article::getViewCounts,article.getViewCounts());
        articleDao.update(updateArticle, queryWrapper);
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @Async("asyncServiceExecutor")
    public void updateArticleCommentCounts(ArticleDao articleDao, Article article) {
        Integer commentCounts = article.getCommentCounts();
        Article updateArticle = new Article();
        updateArticle.setCommentCounts(commentCounts + 1);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<Article>();
        queryWrapper.eq(Article::getId,article.getId());
        // 在做更新之前需要查询更新字段是否是之前的数据，避免多线程对数据多次改变，确保在、
        // 多线程的环境下线程安全问题（乐观锁机制）
        queryWrapper.eq(Article::getCommentCounts,article.getCommentCounts());
        articleDao.update(updateArticle, queryWrapper);
    }
}
