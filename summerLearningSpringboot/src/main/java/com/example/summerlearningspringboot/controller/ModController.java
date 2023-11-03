package com.example.summerlearningspringboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.summerlearningspringboot.common.Page;
import com.example.summerlearningspringboot.common.Result;
import com.example.summerlearningspringboot.entity.Mod;
import com.example.summerlearningspringboot.service.ModService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 功能:
 * 作者: 浅雨梦梨
 * 日期: 2023/10/1 4:10
 */
@RestController
@RequestMapping(value = "/mod")
public class ModController {
    @Autowired
    ModService modService;

    @RequestMapping(value = "/selectByPage", method = RequestMethod.GET)
    public Result selectModByPage(@RequestParam String name, @RequestParam(defaultValue = "1") int pageNum,
                                  @RequestParam(defaultValue = "7") int pageSize){

        Page<Mod> page = modService.selectModByPage(name, pageNum, pageSize);
        return Result.success(page);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result addMod(@RequestBody Mod mod){
        return modService.addMod(mod);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result updateMod(@RequestBody Mod mod) {
        modService.updateMod(mod);
        return Result.success(mod);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result deleteMod(@RequestBody Mod mod){
        return modService.deleteMod(mod);
    }

}
