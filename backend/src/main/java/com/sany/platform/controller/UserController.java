package com.sany.platform.controller;

import com.sany.platform.common.exception.BusinessException;
import com.sany.platform.common.response.Result;
import com.sany.platform.mapper.SysUserMapper;
import com.sany.platform.model.entity.SysUser;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Resource
    private SysUserMapper sysUserMapper;

    @PutMapping("/profile")
    public Result<Void> updateProfile(@RequestBody Map<String, Object> body) {
        String username = (String) body.get("username");
        if (username == null) throw new BusinessException(400, "用户名不能为空");
        SysUser user = sysUserMapper.selectByUsername(username);
        if (user == null) throw new BusinessException(404, "用户不存在");
        SysUser update = new SysUser();
        update.setId(user.getId());
        if (body.containsKey("realName")) update.setRealName((String) body.get("realName"));
        if (body.containsKey("phone")) update.setPhone((String) body.get("phone"));
        if (body.containsKey("email")) update.setEmail((String) body.get("email"));
        sysUserMapper.updateById(update);
        return Result.success();
    }
}
