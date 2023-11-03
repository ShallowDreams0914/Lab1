package com.example.summerlearningspringboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.summerlearningspringboot.entity.Mod;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ModMapper extends BaseMapper<Mod> {

    @Select("SELECT mods.id as id, mods.name as name, mods.type as type, mods.author as author, mods.time as time, mods.introduction as introduction," +
            " mods.description as description, mods.file as file FROM `user` JOIN user_mod ON `user`.id = `user_mod`.userid " +
            "JOIN `mods` ON `mods`.id = `user_mod`.modid WHERE `user`.id = #{id} && `mods`.name like CONCAT('%',#{name},'%') ORDER BY `mods`.id DESC limit #{skipNum}, #{pageSize}")
    List<Mod> selectModByPage(@Param("id") int id, @Param("skipNum") int skipNum, @Param("pageSize") int pageSize, @Param("name") String name);

    @Select("SELECT mods.id as id, mods.name as name, mods.type as type, mods.author as author, mods.time as time, mods.introduction as introduction," +
            " mods.description as description, mods.file as file FROM `user` JOIN user_mod ON `user`.id = `user_mod`.userid " +
            "JOIN `mods` ON `mods`.id = `user_mod`.modid WHERE `user`.id = #{id} && `mods`.name like CONCAT('%',#{name},'%') ORDER BY `mods`.id DESC")
    List<Mod> selectMod(@Param("id") int id, @Param("name") String name);
}
