package com.example.summerlearningspringboot.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.summerlearningspringboot.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    /*
    @Insert("insert into `user` (userName, password, name, phone, email, address, avatar)" +
            "values (#{userName},#{password}, #{name}, #{phone}, #{email}, #{address}, #{avatar})")
    public void insert(User user);

    @Update("update `user` set userName = #{userName}, password = #{password}, name = #{name}, phone = #{phone}, " +
            "email = #{email}, address = #{address}, avatar = #{avatar} where id = #{id}")
    public void update(User user);

    @Delete("delete from `user` where id = #{id}")
    public void delete(Integer id);

    @Select("select * from `user` order by id desc ")
    List<User> selectAll();

    @Select("select * from `user` where id = #{id}")
    User selectById(Integer id);

    @Select("select * from `user` where userName = #{userName} and password = #{password} order by id desc")
    List<User> selectByUserNameAndPassword(@Param("userName") String userName, @Param("password") String password);

    @Select("select * from `user` where userName like concat('%', #{userName}, '%') and password = #{password} order by id desc")
    List<User> selectByFuzzy(@Param("userName") String userName, @Param("password") String password);

    @Select("select * from `user` where userName like concat('%', #{userName}, '%') and password = #{password} order by id desc limit #{skipNum}, #{pageSize}")
    List<User> selectByPage(@Param("userName") String userName, @Param("password") String password, @Param("skipNum") Integer skipNum, @Param("pageSize") Integer pageSize);

    @Select("select count(id) from `user` where userName like concat('%', #{userName}, '%') and password = #{password}")
    Integer selectCountByPage(@Param("userName") String userName, @Param("password") String password);

    @Select("select * from `user` where userName = #{userName}")
    User selectByUserName(@Param("userName") String userName);

    @Insert("insert into `user` (userName, password) values (#{userName},#{password})")
    void registerUser(User user);*/ //最初使用Mybatis时的写法



}
