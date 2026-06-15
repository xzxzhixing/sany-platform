package com.sany.platform.mapper;

import com.sany.platform.model.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysUserMapper {
    SysUser selectByUsername(@Param("username") String username);
    SysUser selectById(@Param("id") Long id);
    int updateById(SysUser user);
}
