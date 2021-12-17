package com.tonpower.springbootweb.domain;

import lombok.Data;

/**
 * @description:
 * @author: li377650260
 * @date: 2021/10/24 19:23
 */

@Data
public class Comment {

    private Long id;

    private String content;

    private Long createDate;

    private Long ArticleId;

    private Long parentId;

    private Long authorId;

    private Long toUid;

    private Integer level;
}
