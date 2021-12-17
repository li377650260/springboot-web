package com.tonpower.springbootweb.domain;

import lombok.Data;

/**
 * @description:
 * @author: li377650260
 * @date: 2021/10/23 16:04
 */
@Data
public class Category {

    private Long id;

    private String avatar;

    private String categoryName;

    private String description;
}
