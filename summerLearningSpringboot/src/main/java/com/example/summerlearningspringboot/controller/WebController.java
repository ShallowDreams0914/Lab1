package com.example.summerlearningspringboot.controller;

import cn.hutool.core.util.StrUtil;
import com.example.summerlearningspringboot.common.AuthAccess;
import com.example.summerlearningspringboot.common.Result;
import com.example.summerlearningspringboot.entity.User;
import com.example.summerlearningspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能:
 * 作者: 浅雨梦梨
 * 日期: 2023/8/28 22:12
 */
@CrossOrigin
@RestController
public class WebController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody User userInfo){
        if (StrUtil.isBlank(userInfo.getUserName()) || StrUtil.isBlank(userInfo.getPassword())) {
            return Result.error("数据输入不合法");
        }
        User user = userService.login(userInfo);
        return Result.success(user);
    }

    @AuthAccess
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result register (@RequestBody User user) {
        if (StrUtil.isBlank(user.getUserName()) || StrUtil.isBlank(user.getPassword())) {
            return Result.error("数据输入不合法");
        }
        User rtUser = userService.registerUser(user);
        return Result.success(rtUser);
    }
}

