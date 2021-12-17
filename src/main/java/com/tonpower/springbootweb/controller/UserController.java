package com.tonpower.springbootweb.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tonpower.springbootweb.service.SysUserService;
import com.tonpower.springbootweb.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: li377650260
 * @date: 2021/10/21 18:13
 */

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    SysUserService sysUserService;



    @GetMapping("/currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token) throws JsonProcessingException {

        return sysUserService.findUserByToken(token);
    }

}
