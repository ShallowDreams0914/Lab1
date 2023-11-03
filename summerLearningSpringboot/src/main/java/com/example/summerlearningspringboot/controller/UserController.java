package com.example.summerlearningspringboot.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.summerlearningspringboot.common.Result;
import com.example.summerlearningspringboot.entity.User;
import com.example.summerlearningspringboot.exception.ServiceException;
import com.example.summerlearningspringboot.service.UserService;
import com.example.summerlearningspringboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 功能:
 * 作者: 浅雨梦梨
 * 日期: 2023/9/8 19:35
 */


@RestController
@RequestMapping(value = "/user")
public class UserController {

    //修改3

    @Autowired
    UserService userService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(@RequestBody User user){
        try{
            userService.save(user);
        }catch (Exception e){
            if (e instanceof DuplicateKeyException) {
                return Result.error("数据库插入错误");
            } else {
                return Result.error("系统错误");
            }
        }
        return Result.success(user);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(@RequestBody User user){
        userService.updateById(user);
        return Result.success(user);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public Result deleteUser(@PathVariable Integer id){
        User currentUser = TokenUtils.getCurrentUser();
        if (currentUser != null && Objects.equals(currentUser.getId(), id)) {
            throw new ServiceException("不能删除当前登录的用户");
        }

        return userService.deleteUser(id);
    }

    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    public Result deleteBatchUser(@RequestBody List<Integer> ids){
        User currentUser = TokenUtils.getCurrentUser();
        if (currentUser != null && ids.contains(currentUser.getId())){
            throw new ServiceException("不能删除当前登录的用户");
        }
        return userService.deleteBatchUser(ids);
    }

    @RequestMapping(value = "/selectAll", method = RequestMethod.GET)
    public Result selectAll(){
        List<User> userList = userService.list(new QueryWrapper<User>().orderByDesc("id"));
        return Result.success(userList);
    }

    @RequestMapping(value = "/selectById/{id}", method = RequestMethod.GET)
    public Result selectById(@PathVariable Integer id){
        User user = userService.getById(id);
        return Result.success(user);
    }


    @RequestMapping(value = "/confirmUserName", method = RequestMethod.POST)
    public Result confirmUserName(@RequestBody Map<String, String> map){
        User user = userService.selectByUserName(map.get("userName"));
        if (user == null){
            return Result.success();
        } else {
            return Result.error("用户名已存在");
        }
    }

    /*@RequestMapping(value = "/selectByUserNameAndPassword", method = RequestMethod.GET)
    public Result selectByUserNameAndPassword(@RequestParam String userName, @RequestParam String password){
        List<User> userList = userService.selectByUserNameAndPassword(userName, password);
        return Result.success(userList);
    }

    @RequestMapping(value = "/selectByFuzzy", method = RequestMethod.GET)
    public Result selectByFuzzy(@RequestParam String userName, @RequestParam String password){
        List<User> userList = userService.selectByFuzzy(userName, password);
        return Result.success(userList);
    }*/

    /**
     * 功能: 分页查询
     * 作者: 浅雨梦梨
     * 日期: 2023/9/8 19:35
     * param: pageNum 页码
     *        pageSize 每一页的数量
     */

    @RequestMapping(value = "/selectByPage", method = RequestMethod.GET)
    public Result SelectByPage (@RequestParam String userName, @RequestParam String name,
                                @RequestParam Integer pageNum, @RequestParam Integer pageSize){
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().orderByDesc("id");
        queryWrapper.like(StrUtil.isNotBlank(userName), "user_name", userName);
        queryWrapper.like(StrUtil.isNotBlank(name), "name", name);
        Page<User> page = userService.page(new Page<>(pageNum, pageSize), queryWrapper);
        return Result.success(page);
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void exportData(@RequestParam String ids ,HttpServletResponse response) throws IOException {
        //QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        List<Integer> idsArr1 = Arrays.stream(ids.split(",")).map(Integer::valueOf).collect(Collectors.toList());

        //queryWrapper.in("id", idsArr1);
        //List<User> list;
        //list = userService.list(queryWrapper);

        List<User> userList = userService.selectBatchIds(idsArr1);

        ExcelWriter writer = ExcelUtil.getWriter();
        writer.write(userList,true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("用户信息表", "UTF-8") + ".xlsx");
        ServletOutputStream outputStream = response.getOutputStream();
        writer.flush(outputStream, true);
        outputStream.flush();
        outputStream.close();
        writer.close();
    }

}
