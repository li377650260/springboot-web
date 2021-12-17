package com.tonpower.springbootweb.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.tonpower.springbootweb.utils.LongToStringSerializer;
import lombok.Data;

import java.util.List;

@Data
public class ArticleVo {

//    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    private Integer weight;
    /**
     * 创建时间
     */
    private String createDate;

    private String author;
    private String avatar;
    private ArticleBodyVo body;

    private List<TagVo> tags;

    private CategoryVo category;

}