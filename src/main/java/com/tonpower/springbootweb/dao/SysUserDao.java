package com.tonpower.springbootweb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tonpower.springbootweb.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserDao extends BaseMapper<SysUser> {

}
