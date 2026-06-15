package com.sany.platform.controller;

import com.sany.platform.common.response.Result;
import com.sany.platform.mapper.SysUserMapper;
import com.sany.platform.model.entity.SysUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {
    @Resource
    private SysUserMapper sysUserMapper;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostMapping("/api/v1/auth/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        if (username == null || password == null) {
            return Result.error(400, "用户名或密码不能为空");
        }
        SysUser user = sysUserMapper.selectByUsername(username);
        if (user == null || !encoder.matches(password, user.getPassword())) {
            return Result.error(401, "用户名或密码错误");
        }
        if (user.getStatus() != 1) {
            return Result.error(403, "账号已被禁用");
        }
        Map<String, Object> result = new HashMap<>();
        result.put("id", user.getId());
        result.put("username", user.getUsername());
        result.put("realName", user.getRealName());
        result.put("role", user.getUserType() == 0 ? "admin" : "engineer");
        result.put("token", "mock-" + user.getUsername());
        result.put("phone", user.getPhone());
        result.put("email", user.getEmail());
        return Result.success(result);
    }
}
