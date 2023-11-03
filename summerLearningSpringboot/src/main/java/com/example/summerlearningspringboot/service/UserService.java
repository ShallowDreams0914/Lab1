package com.example.summerlearningspringboot.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.summerlearningspringboot.common.Result;
import com.example.summerlearningspringboot.entity.User;
import com.example.summerlearningspringboot.entity.User_Mod;
import com.example.summerlearningspringboot.exception.ServiceException;

import com.example.summerlearningspringboot.mapper.ModMapper;
import com.example.summerlearningspringboot.mapper.UserMapper;
import com.example.summerlearningspringboot.mapper.User_ModMapper;
import com.example.summerlearningspringboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 功能:
 * 作者: 浅雨梦梨
 * 日期: 2023/9/8 19:34
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    @Autowired
    UserMapper userMapper;
    /*
    public void insertUser(User user) {
        userMapper.insert(user);
    }

    public void updateUser(User user) {
        userMapper.update(user);
    }

    public void deleteUser(Integer id) {
        userMapper.delete(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            userMapper.delete(id);
        }
    }

    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }

    public List<User> selectByUserNameAndPassword(String userName, String password) {
        return userMapper.selectByUserNameAndPassword(userName, password);
    }

    public List<User> selectByFuzzy(String userName, String password) {
        return userMapper.selectByFuzzy(userName, password);
    }

    public List<User> selectByPage(String userName, String password, Integer pageNum, Integer pageSize) {
        Integer skipNum = (pageNum - 1) * pageSize;
        return userMapper.selectByPage(userName, password, skipNum, pageSize);
    }

    public Integer selectCountByPage(String userName, String password) {
        return userMapper.selectCountByPage(userName, password);
    }*/ //之前使用myBatis的写法


    @Autowired
    User_ModMapper userModMapper;

    @Autowired
    ModMapper modMapper;

    public User selectByUserName(String userName){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName);
        User test = userMapper.selectOne(queryWrapper);
        return test;
    }

    public User login(User userInfo) {
        User dbUser = selectByUserName(userInfo.getUserName());
        if (dbUser == null) {
            throw new ServiceException("用户名或密码错误");
        }
        if (!Objects.equals(userInfo.getPassword(), dbUser.getPassword())) {
            throw new ServiceException("用户名或密码错误");
        }
        //登录成功后生成token
        String token = TokenUtils.creatToken(dbUser.getId().toString(),dbUser.getPassword());
        dbUser.setToken(token);
        return dbUser;
    }

    public User registerUser(User user) {
        User dbUser = selectByUserName(user.getUserName());
        if (dbUser != null) {
            throw new ServiceException("用户名已存在");
        } else {
            userMapper.insert(user);
        }
        return user;
    }

    public List<User> selectBatchIds(List<Integer> ids) {
        return userMapper.selectBatchIds(ids);
    }

    public Result deleteUser(Integer id) {
        this.removeById(id);
        QueryWrapper<User_Mod> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", id);
        List<User_Mod> userMods = userModMapper.selectList(queryWrapper);
        List<Integer> modIds = userMods.stream().map(User_Mod::getModid).collect(Collectors.toList());
        modMapper.deleteBatchIds(modIds);
        userModMapper.delete(queryWrapper);
        return Result.success();

    }

    public Result deleteBatchUser(List<Integer> ids) {
        userMapper.deleteBatchIds(ids);
        QueryWrapper<User_Mod> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("userid", ids);
        List<User_Mod> userMods = userModMapper.selectList(queryWrapper);
        if (!userMods.isEmpty()){
            List<Integer> modIds = userMods.stream().map(User_Mod::getModid).collect(Collectors.toList());
            modMapper.deleteBatchIds(modIds);
            userModMapper.delete(queryWrapper);
        }
        return Result.success();

    }
}
