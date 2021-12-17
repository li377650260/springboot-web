package com.tonpower.springbootweb.controller;

import com.tonpower.springbootweb.service.LoginService;
import com.tonpower.springbootweb.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: li377650260
 * @date: 2021/10/21 22:35
 */

@RestController
@RequestMapping("/api/logout")
public class LogoutController {

    @Autowired
    LoginService loginService;

    @GetMapping
    public Result logout(@RequestHeader("Authorization") String token){
        return loginService.logout(token);
    }
}
