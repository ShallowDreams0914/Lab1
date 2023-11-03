package com.example.summerlearningspringboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Objects;

/**
 * 功能:
 * 作者: 浅雨梦梨
 * 日期: 2023/8/29 2:01
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("user")
public final class User {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String userName;
    private String password;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String avatar;//头像
    private String authority;//用户权限

    @TableField(exist = false)
    private String token;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(userName, user.userName) &&
                Objects.equals(password, user.password) && Objects.equals(name, user.name) &&
                Objects.equals(phone, user.phone) && Objects.equals(email, user.email) &&
                Objects.equals(address, user.address) && Objects.equals(avatar, user.avatar) &&
                Objects.equals(token, user.token) && Objects.equals(authority, user.authority);
    }


}
