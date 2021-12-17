package com.tonpower.springbootweb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tonpower.springbootweb.domain.SysUser;
import com.tonpower.springbootweb.vo.ErrorCode;
import com.tonpower.springbootweb.vo.Result;
import com.tonpower.springbootweb.vo.params.LoginParams;

/**
 * @description:
 * @author: li377650260
 * @date: 2021/10/21 18:21
 */
public interface LoginService {
    /**
     * 登录业务处理
     * @param loginParams
     * @return
     * @throws JsonProcessingException
     */
    Result login(LoginParams loginParams) throws JsonProcessingException;

    /**
     * 登录携带token的解析校验
     * @param token
     * @return
     * @throws JsonProcessingException
     */
    SysUser checkToken(String token) throws JsonProcessingException;

    /**
     * 登录推出业务
     * @param token
     * @return
     */
    Result logout(String token);

    /**
     * 账号注册业务
     * @param loginParams
     * @return
     */
    Result register(LoginParams loginParams);
}
