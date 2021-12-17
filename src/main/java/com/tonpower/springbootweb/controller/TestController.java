package com.tonpower.springbootweb.controller;

import com.tonpower.springbootweb.utils.UserThreadLocal;
import com.tonpower.springbootweb.vo.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: li377650260
 * @date: 2021/10/22 17:31
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping
    public Result test(){
        System.out.println(UserThreadLocal.get());
        return Result.success("");
    }
}
