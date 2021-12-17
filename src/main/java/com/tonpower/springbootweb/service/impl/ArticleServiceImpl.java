package com.tonpower.springbootweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tonpower.springbootweb.aop.cache.Cache;
import com.tonpower.springbootweb.dao.ArticleBodyDao;
import com.tonpower.springbootweb.dao.ArticleDao;
import com.tonpower.springbootweb.dao.ArticleTagDao;
import com.tonpower.springbootweb.domain.*;
import com.tonpower.springbootweb.domain.dos.Archives;
import com.tonpower.springbootweb.service.*;
import com.tonpower.springbootweb.utils.UserThreadLocal;
import com.tonpower.springbootweb.vo.*;
import com.tonpower.springbootweb.vo.params.ArticleParam;
import com.tonpower.springbootweb.vo.params.PageParams;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: li377650260
 * @date: 2021/10/14 12:04
 */
@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleDao articleDao;

    @Autowired
    ArticleBodyDao articleBodyDao;

    @Autowired
    ArticleTagDao articleTagDao;

    @Autowired
    TagService tagService;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    CategoryService categoryService;
    
    @Autowired
    ThreadService threadService;
    /**
     * 分页查询数据库article表数据
     * @param pageParams
     * @return Result
     */
    @Override
    public Result listArticle(PageParams pageParams) {
        if (pageParams.getMonth() != null && pageParams.getMonth().length() >0
        && pageParams.getYear() != null && pageParams.getMonth().length() >0){
            Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());

            IPage<Article> iPage = articleDao.listArticle(page,pageParams.getCategoryId(),pageParams.getTagId()
            ,pageParams.getYear(),pageParams.getMonth());
            List<Article> records = iPage.getRecords();
            return Result.success(copyList(records,true,true));
        }
        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        if (pageParams.getCategoryId() != null){
            queryWrapper.eq(Article::getCategoryId,pageParams.getCategoryId());
        }
        List<Long> articleIds = new ArrayList<>();
        if (pageParams.getTagId() != null){
            LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
            articleTagLambdaQueryWrapper.eq(ArticleTag::getTagId,pageParams.getTagId());
            List<ArticleTag> articleTags = articleTagDao.selectList(articleTagLambdaQueryWrapper);
            for (ArticleTag articleTag:articleTags) {
                articleIds.add(articleTag.getArticleId());
            }
            if (articleIds.size() > 0){
                queryWrapper.in(Article::getId,articleIds);
            }
//            else {
//                return Result.success(null);
//            }
        }

        // 根据权重判断文章是否置定
        queryWrapper.orderByDesc(Article::getWeight);
        // order by createDate desc
        queryWrapper.orderByDesc(Article::getCreateDate);
        Page<Article> articlePage = articleDao.selectPage(page, queryWrapper);
        List<Article> records =articlePage.getRecords();
//        System.out.println(records);
        List<ArticleVo> articleVoList = copyList(records,true,true);
        return Result.success(articleVoList);
    }

    @Override
    public List<ArticleVo> hosts(int limit) {
        List<Article> articles = articleDao.findArticlesById(limit);
        return copyList(articles,false,false);
    }

    @Override
    public List<ArticleVo> newArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+limit);
        List<Article> articles = articleDao.selectList(queryWrapper);
        return copyList(articles,false,false);
    }

    /**
     * 文章归档
     * @return
     */
    @Override
    public List<Archives> listArchives() {
        List<Archives> archivesList = articleDao.listArchives();
        return archivesList;
    }

    @Override
    public ArticleVo findArticleById(Long id) {
        /*
        1. 根据id查询文章信息
        2. 根据文章信息对文章主题分类进行多表查询
         */

        Article article = articleDao.selectById(id);
        // 更新阅读量操作并不需要实时显示，此次阅读时显示数据不需要将此次阅读加上
        // 同时在查看文章的过程中去进行更新操作再实时显示，因为更新操作需要加锁效
        // 率较低，所以不需要实时显示阅读量更新可以把更新操作放入线程池中进行，不
        // 影响主线程查看文章详情任务的效率，同时这样的话更新操作如果出现问题同样
        // 不会影响主线程任务

        threadService.updateArticleViewCounts(articleDao, article);
        ArticleVo articleVo = copy(article, true, true,true,true);
        return articleVo;
    }

    @Transactional
    @Override
    public Map<String, String> save(ArticleParam articleParam) {
        /*
        1. 发布文章构建article对象
            （1）当前登录用户的id
            （2）标签，将标签加入到关联表中
            （3）从本地线程变量中获取登录用户信息填充作者信息
            （4）根据参数填充Body内容
         */
        SysUser sysUser = UserThreadLocal.get();
        Article article = new Article();
        article.setWeight(Article.Article_Common);
        article.setViewCounts(0);
        article.setTitle(articleParam.getTitle());
        article.setSummary(articleParam.getSummary());
        article.setCategoryId(Long.parseLong(articleParam.getCategory().getId()));
        article.setCommentCounts(0);
        article.setCreateDate(System.currentTimeMillis());
        article.setAuthorId(sysUser.getId());
        // 执行插入生成文章的id
        articleDao.insert(article);
        List<TagVo> tags = articleParam.getTags();
        if (tags != null){
            Long id = article.getId();
            ArticleTag articleTag;
            for (TagVo tagVo:
                 tags) {
                articleTag = new ArticleTag();
                articleTag.setArticleId(id);
                articleTag.setTagId(Long.parseLong(tagVo.getId()));
                articleTagDao.insert(articleTag);
            }
        }
        ArticleBody articleBody = new ArticleBody();
        articleBody.setArticleId(article.getId());
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBodyDao.insert(articleBody);
        article.setBodyId(articleBody.getId());
        articleDao.updateById(article);
        Map<String,String> map = new HashMap<>();
        map.put("id",article.getId().toString());
        return map;
    }

    @Override
    public void updateCommentCountById(Long articleId) {
        Article article = articleDao.selectById(articleId);
        threadService.updateArticleCommentCounts(articleDao, article);
    }


    // 讲article对象复制到要返回给前端页面的数据articleVo对象上
    private List<ArticleVo> copyList(List<Article> records,boolean isTag,boolean isAuthor) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article article : records){
            ArticleVo copy = copy(article,isTag,isAuthor);
            articleVoList.add(copy);
        }
        return articleVoList;
    }

    private ArticleVo copy(Article article,boolean isTag,boolean isAuthor){
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article,articleVo);
        articleVo.setId(String.valueOf(article.getId()));
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        if (isTag){
            Long id = article.getId();
            List<TagVo> tagsByArticleId = tagService.findTagsByArticleId(id);
            articleVo.setTags(tagsByArticleId);
        }
        if (isAuthor){
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
        }
        return articleVo;
    }
    private ArticleVo copy(Article article,boolean isTag,boolean isAuthor,
                           boolean isBody, boolean isCategory){
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article,articleVo);
        articleVo.setId(String.valueOf(article.getId()));
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        if (isTag){
            Long id = article.getId();
            List<TagVo> tagsByArticleId = tagService.findTagsByArticleId(id);
            articleVo.setTags(tagsByArticleId);
        }
        if (isAuthor){
            Long authorId = article.getAuthorId();
            SysUser userById = sysUserService.findUserById(authorId);

            articleVo.setAuthor(userById.getNickname());
            articleVo.setAvatar(userById.getAvatar());
        }
        if (isBody){
            Long bodyId = article.getBodyId();
            ArticleBodyVo articleBodyVo = findArticleBodyById(bodyId);
            articleVo.setBody(articleBodyVo);
        }
        if (isCategory){
            Long categoryId = article.getCategoryId();
            Category category = categoryService.findCategoryById(categoryId);
            CategoryVo categoryVo = new CategoryVo();
            BeanUtils.copyProperties(category,categoryVo);
            articleVo.setCategory(categoryVo);
        }
        return articleVo;
    }

    private ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyDao.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }
}
