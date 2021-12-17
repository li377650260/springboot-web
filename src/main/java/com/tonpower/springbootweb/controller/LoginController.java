package com.tonpower.springbootweb.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tonpower.springbootweb.service.LoginService;
import com.tonpower.springbootweb.vo.ErrorCode;
import com.tonpower.springbootweb.vo.Result;
import com.tonpower.springbootweb.vo.params.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: li377650260
 * @date: 2021/10/21 18:19
 */

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping(produces = "application/json")
    public Result login(@RequestBody LoginParams loginParams) throws JsonProcessingException {
        Result login = loginService.login(loginParams);
        return login;
    }
}
