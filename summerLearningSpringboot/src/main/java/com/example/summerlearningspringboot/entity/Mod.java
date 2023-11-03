package com.example.summerlearningspringboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Type;
import java.util.Objects;

/**
 * 功能:
 * 作者: 浅雨梦梨
 * 日期: 2023/10/1 3:41
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@TableName("mods")
public class Mod {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;
    private String author;
    private String introduction;
    private String description;
    private String file;
    private String time;
    private String type;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mod mod = (Mod) o;
        return Objects.equals(id, mod.id) && Objects.equals(name, mod.name) && Objects.equals(author, mod.author) && Objects.equals(introduction, mod.introduction) && Objects.equals(description, mod.description) && Objects.equals(file, mod.file) && Objects.equals(time, mod.time) && Objects.equals(type, mod.type);
    }




}
