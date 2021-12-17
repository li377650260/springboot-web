package com.tonpower.springbootweb.controller;

import com.tonpower.springbootweb.service.LoginService;
import com.tonpower.springbootweb.vo.ErrorCode;
import com.tonpower.springbootweb.vo.Result;
import com.tonpower.springbootweb.vo.params.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: li377650260
 * @date: 2021/10/21 22:40
 */

@RestController
@RequestMapping("/api/register")
public class RegisterController {

    @Autowired
    LoginService loginService;

    @PostMapping()
    public Result register(@RequestBody LoginParams loginParams){
        return Result.fail(ErrorCode.INTERFACE_CLOSED.getCode(),ErrorCode.INTERFACE_CLOSED.getMsg());
//        return loginService.register(loginParams);
    }
}
