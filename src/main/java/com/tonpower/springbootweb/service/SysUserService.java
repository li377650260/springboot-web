package com.tonpower.springbootweb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tonpower.springbootweb.domain.SysUser;
import com.tonpower.springbootweb.vo.Result;
import com.tonpower.springbootweb.vo.UserVo;

/**
 * @description:
 * @author: li377650260
 * @date: 2021/10/19 17:04
 */
public interface SysUserService {
    /**
     * 通过用户ID查询用户信息
     * @param id
     * @return
     */
    SysUser findUserById(Long id);

    /**
     * 通过token解析用户信息
     * @param token
     * @return
     * @throws JsonProcessingException
     */
    Result findUserByToken(String token) throws JsonProcessingException;

    /**
     * 通过用户名密码校验用户信息
     * @param account
     * @param password
     * @return
     */
    SysUser findUser(String account, String password);

    /**
     * 根据用户名查询账号信息
     * @param account
     * @return
     */
    SysUser findUserByAccount(String account);

    /**
     * 保存新的用户信息
     * @param sysUser
     */
    int save(SysUser sysUser);


    UserVo findUserVoById(Long authorId);
}
