package com.tonpower.springbootweb.domain;

import lombok.Data;

/**
 * @description:
 * @author: li377650260
 * @date: 2021/10/25 17:23
 */
@Data
public class ArticleTag {

    private Long id;

    private Long tagId;

    private Long ArticleId;
}
