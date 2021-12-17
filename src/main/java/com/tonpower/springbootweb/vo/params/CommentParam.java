package com.tonpower.springbootweb.vo.params;

import lombok.Data;

/**
 * @description:
 * @author: li377650260
 * @date: 2021/10/24 21:51
 */
@Data
public class CommentParam {
    private Long articleId;

    private String content;

    private Long parent;

    private Long toUserId;
}
