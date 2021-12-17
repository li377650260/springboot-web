package com.tonpower.springbootweb.vo.params;

import lombok.Data;

/**
 * @description:
 * @author: li377650260
 * @date: 2021/10/12 22:49
 */
@Data
public class PageParams {

    private int page = 1;
    private int pageSize = 10;


    private Long categoryId;

    private Long tagId;


    private String year;
    private String month;

    public String getMonth(){
        if (this.month != null && this.month.length() == 1){
            return "0"+this.month;
        }

        return this.month;
    }
}
