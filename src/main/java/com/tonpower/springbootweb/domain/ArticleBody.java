package com.tonpower.springbootweb.domain;

import lombok.Data;

/**
 * @description:
 * @author: li377650260
 * @date: 2021/10/23 16:02
 */
@Data
public class ArticleBody {

    private Long id;

    private String content;

    private String contentHtml;

    private Long articleId;

}
