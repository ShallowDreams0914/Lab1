package com.example.summerlearningspringboot.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.summerlearningspringboot.common.Page;
import com.example.summerlearningspringboot.common.Result;
import com.example.summerlearningspringboot.entity.Mod;
import com.example.summerlearningspringboot.entity.User;
import com.example.summerlearningspringboot.entity.User_Mod;
import com.example.summerlearningspringboot.exception.ServiceException;
import com.example.summerlearningspringboot.mapper.ModMapper;
import com.example.summerlearningspringboot.mapper.User_ModMapper;
import com.example.summerlearningspringboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能:
 * 作者: 浅雨梦梨
 * 日期: 2023/10/1 4:08
 */
@Service
public class ModService extends ServiceImpl<ModMapper, Mod> {
    @Autowired
    ModMapper modMapper;

    @Autowired
    User_ModMapper userModMapper;


    public Page<Mod> selectModByPage(String name, int pageNum, int pageSize) {
        //QueryWrapper<Mod> queryWrapper = new QueryWrapper<Mod>().orderByDesc("id");
        //queryWrapper.like(StrUtil.isNotBlank(name), "name", name);
        //Page<Mod> page = this.page(new Page<>(pageNum, pageSize), queryWrapper);
        User currentUser = TokenUtils.getCurrentUser();
        if (currentUser == null) throw new ServiceException("未获取到当前登录用户");
        int skipNum = (pageNum-1) * pageSize;
        List<Mod> mods = modMapper.selectModByPage(currentUser.getId(), skipNum, pageSize, name);
        List<Mod> allMods = modMapper.selectMod(currentUser.getId(), name);
        Page<Mod> page = new Page<>(mods, allMods.size());
        return page;
    }

    public Result addMod(Mod mod) {
        User currentUser = TokenUtils.getCurrentUser();
        if (currentUser == null) throw new ServiceException("未获取到当前登录用户");
        String now = DateUtil.today();
        mod.setTime(now);
        boolean isSave = this.save(mod);
        if (!isSave) return Result.error("插入失败");
        QueryWrapper<Mod> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", mod.getName());
        Mod finalMod = this.getOne(queryWrapper);
        User_Mod userMod = new User_Mod();
        userMod.setUserid(currentUser.getId());
        userMod.setModid(finalMod.getId());
        userModMapper.insert(userMod);
        return Result.success("插入成功");
    }

    public void updateMod(Mod mod) {
        boolean isSuccess = this.updateById(mod);
        if (!isSuccess) throw new ServiceException("更新失败");
    }

    public Result deleteMod(Mod mod) {
        boolean isDelete = this.removeById(mod);
        if (!isDelete){
            return Result.error("删除失败");
        }
        QueryWrapper<User_Mod> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("modid", mod.getId());
        userModMapper.delete(queryWrapper);
        return Result.success();
    }
}
