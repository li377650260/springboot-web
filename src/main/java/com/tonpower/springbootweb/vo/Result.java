package com.tonpower.springbootweb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @description:
 * @author: li377650260
 * @date: 2021/10/12 22:51
 */

@Data
@AllArgsConstructor
public class Result {

    private boolean success;
    private int code;
    private String message;
    private Object data;

    public static Result success(Object data){
        return new Result(true,200,"success",data);
    }

    public static Result fail(int code,String msg){
        return new Result(false,code,msg,null);
    }
}
