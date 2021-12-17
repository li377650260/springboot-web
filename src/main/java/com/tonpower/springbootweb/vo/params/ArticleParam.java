package com.tonpower.springbootweb.vo.params;

import com.tonpower.springbootweb.vo.CategoryVo;
import com.tonpower.springbootweb.vo.TagVo;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: li377650260
 * @date: 2021/10/25 17:09
 */
@Data
public class ArticleParam {
    private Long id;

    private ArticleBodyParam body;

    private CategoryVo category;

    private String summary;

    private List<TagVo> tags;

    private String title;
}
