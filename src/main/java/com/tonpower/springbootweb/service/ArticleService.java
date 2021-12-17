package com.tonpower.springbootweb.service;


import com.tonpower.springbootweb.domain.dos.Archives;
import com.tonpower.springbootweb.vo.ArticleVo;
import com.tonpower.springbootweb.vo.Result;
import com.tonpower.springbootweb.vo.params.ArticleParam;
import com.tonpower.springbootweb.vo.params.PageParams;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    /**
     * 加载所有文章列表业务
     * @param pageParams
     * @return
     */
    Result listArticle(PageParams pageParams);

    /**
     * 加载最热文章标题业务
     * @param limit
     * @return
     */
    List<ArticleVo> hosts(int limit);

    /**
     * 加载最新创建的文章标题业务
     * @param limit
     * @return
     */
    List<ArticleVo> newArticles(int limit);

    /**
     * 对文章创建时间的归档业务
     * @return
     */
    List<Archives> listArchives();

    /**
     * 根据文章Id获取文章详细内容
     * @param id
     * @return
     */
    ArticleVo findArticleById(Long id);

    /**
     * 文章发布服务
     * @param articleParam
     * @return
     */
    Map save(ArticleParam articleParam);

    void updateCommentCountById(Long articleId);
}
