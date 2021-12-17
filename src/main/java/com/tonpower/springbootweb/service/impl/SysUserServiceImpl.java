package com.tonpower.springbootweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.tonpower.springbootweb.dao.SysUserDao;
import com.tonpower.springbootweb.domain.SysUser;
import com.tonpower.springbootweb.service.LoginService;
import com.tonpower.springbootweb.service.SysUserService;
import com.tonpower.springbootweb.vo.ErrorCode;
import com.tonpower.springbootweb.vo.LoginVo;
import com.tonpower.springbootweb.vo.Result;
import com.tonpower.springbootweb.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @description:
 * @author: li377650260
 * @date: 2021/10/19 17:05
 */

@Service
@Transactional
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    SysUserDao sysUserDao;

    @Autowired
    LoginService loginService;

    @Override
    public SysUser findUserById(Long id) {
        SysUser sysUser = sysUserDao.selectById(id);
        if (sysUser == null){
            sysUser = new SysUser();
            sysUser.setNickname("tonpower");
            return sysUser;
        }
        return sysUser;
    }

    @Override
    public Result findUserByToken(String token) throws JsonProcessingException {
        /*
        1. token合法性校验，token是否为空，解析是否成功，redis中是否存在
        2. 如果校验失败返回错误信息
        3. 如果成功返回LoginVo
         */
        if (StringUtils.isEmpty(token) || "undefined".equals(token)){
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(),ErrorCode.TOKEN_ERROR.getMsg());
        }
        SysUser sysUser = loginService.checkToken(token);
        if (StringUtils.isEmpty(sysUser)){
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(),ErrorCode.TOKEN_ERROR.getMsg());
        }
        LoginVo loginVo = new LoginVo();
        loginVo.setAccount(sysUser.getAccount());
        loginVo.setId(String.valueOf(sysUser.getId()));
        loginVo.setAvatar(sysUser.getAvatar());
        loginVo.setNickname(sysUser.getNickname());
        return Result.success(loginVo);
    }

    @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.eq(SysUser::getPassword,password);
        queryWrapper.select(SysUser::getId,SysUser::getAccount,SysUser::getAvatar,SysUser::getNickname);
        queryWrapper.last("limit 1");
        SysUser sysUser = sysUserDao.selectOne(queryWrapper);
        return sysUser;
    }

    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.last("limit 1");
        SysUser sysUser = sysUserDao.selectOne(queryWrapper);
        return sysUser;
    }

    @Override
    public int save(SysUser sysUser) {
        int insert = sysUserDao.insert(sysUser);
        return insert;
    }

    @Override
    public UserVo findUserVoById(Long authorId) {
        SysUser sysUser = sysUserDao.selectById(authorId);
        if (sysUser == null){
            sysUser = new SysUser();
            sysUser.setNickname("tonpower");
            sysUser.setAvatar("/static/img/logo.447b788.png");
            sysUser.setId(1L);
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(sysUser,userVo);
        return userVo;
    }
}
