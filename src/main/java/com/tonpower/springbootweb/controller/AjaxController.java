package com.tonpower.springbootweb.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tonpower.springbootweb.dao.SysUserDao;
import com.tonpower.springbootweb.domain.SysUser;
import com.tonpower.springbootweb.vo.Result;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: li377650260
 * @date: 2021/12/11 13:22
 */
@RestController
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
@RequestMapping("api/ajax/test")
public class AjaxController {

    @Autowired
    SysUserDao sysUserDao;
    private static final String slat = "Li!@#$%";

    @RequestMapping
    public Object ajaxTest(String userName, String passWord){
        passWord = DigestUtils.md5Hex(passWord + slat);
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,userName);
        queryWrapper.eq(SysUser::getPassword,passWord);
        queryWrapper.select(SysUser::getId,SysUser::getAccount,SysUser::getAvatar,SysUser::getNickname);
        queryWrapper.last("limit 1");
        SysUser sysUser = sysUserDao.selectOne(queryWrapper);
        if (sysUser == null){
            return Result.fail(5000,"用户账号密码错误");
        }
        return Result.success(sysUser);
    }
}
