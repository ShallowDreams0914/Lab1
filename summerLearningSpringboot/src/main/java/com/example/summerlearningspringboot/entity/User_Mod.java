package com.example.summerlearningspringboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * 功能:
 * 作者: 浅雨梦梨
 * 日期: 2023/10/2 6:50
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_mod")
public class User_Mod {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userid;
    private Integer modid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User_Mod userMod = (User_Mod) o;
        return Objects.equals(id, userMod.id) && Objects.equals(userid, userMod.userid) && Objects.equals(modid, userMod.modid);
    }

}
