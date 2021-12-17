package com.tonpower.springbootweb.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tonpower.springbootweb.dao.SysUserDao;
import com.tonpower.springbootweb.domain.SysUser;
import com.tonpower.springbootweb.service.LoginService;
import com.tonpower.springbootweb.service.SysUserService;
import com.tonpower.springbootweb.utils.JwtUtils;
import com.tonpower.springbootweb.vo.ErrorCode;
import com.tonpower.springbootweb.vo.Result;
import com.tonpower.springbootweb.vo.params.LoginParams;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: li377650260
 * @date: 2021/10/21 18:21
 */

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    SysUserService sysUserService;

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    private static final String slat = "Li!@#$%";
    /**
     * 登录请求检验
     * @param loginParams
     * @return
     */
    @Override
    public Result login(LoginParams loginParams) throws JsonProcessingException {
        /*
        1. 检查参数是否合法
        2. 根据用户名和密码去user表里查询用户数据
        3. 如果不存在登录失败
        4. 如果存在的话返回新生成的token
        5. 将token放入到redis中去，在redis中关联token与user的信息，设置token过期时间
           登录时需检查token字符串是否合法，然后再去redis中认证是否存在和是否过期等
         */
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        if (StringUtils.isEmpty(account) || StringUtils.isEmpty(password)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        password = DigestUtils.md5Hex(password + slat);
        SysUser sysUser = sysUserService.findUser(account,password);
        if (sysUser == null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        String jwt = JwtUtils.createToken(sysUser.getId());
        ObjectMapper om = new ObjectMapper();
        String string = om.writeValueAsString(sysUser);
        redisTemplate.opsForValue().set("TOKEN_"+jwt,string,1, TimeUnit.DAYS);

        return Result.success(jwt);
    }

    @Override
    public SysUser checkToken(String token) throws JsonProcessingException {
        if (StringUtils.isEmpty(token)) {
            return null;
        }

        Map<String, Object> map = JwtUtils.checkToken(token);

        if (StringUtils.isEmpty(map)){
            return null;
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN_"+token);

        if (StringUtils.isEmpty(userJson)){
            return null;
        }
        ObjectMapper om = new ObjectMapper();
        SysUser sysUser = om.readValue(userJson,SysUser.class);
        return sysUser;
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_"+token);
        return Result.success(null);
    }

    @Override
    public Result register(LoginParams loginParams) {
        /*
        1. 判断参数是否合法
        2. 判断用户名是否存在
        3. 不存在进行用户注册
        4. 生成用户信息的token
        5. 存入redis数据库
        6. 为注册业务方法添加事务处理
         */

        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        String nickname = loginParams.getNickname();
        if (StringUtils.isEmpty(account) || StringUtils.isEmpty(password)
        || StringUtils.isEmpty(nickname)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        SysUser sysUser = sysUserService.findUserByAccount(account);
        if (sysUser != null){
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(),ErrorCode.ACCOUNT_EXIST.getMsg());
        }
        sysUser = new SysUser();
        sysUser.setAccount(account);
        sysUser.setNickname(nickname);
        sysUser.setPassword(DigestUtils.md5Hex(password + slat));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setAvatar("/static/img/logo.447b788.png");
        sysUser.setEmail("");
        sysUser.setStatus("");
        sysUser.setSalt("");
        sysUser.setAdmin(1);
        sysUser.setDeleted(0);
        sysUser.setLastLogin(System.currentTimeMillis());
//        sysUser.setId(Long.valueOf(UUID.randomUUID().toString().replaceAll("-","")));
        sysUser.setMobilePhoneNumber("");
        int save = sysUserService.save(sysUser);
        return Result.success(save);
    }
}
