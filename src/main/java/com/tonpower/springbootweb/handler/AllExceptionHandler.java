package com.tonpower.springbootweb.handler;

import com.tonpower.springbootweb.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description:
 * @author: li377650260
 * @date: 2021/10/19 21:52
 */

@ControllerAdvice
public class AllExceptionHandler {
    //
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handlerException(Exception ex){
        ex.printStackTrace();
        return Result.fail(555,"系统异常请稍后");
    }

}
