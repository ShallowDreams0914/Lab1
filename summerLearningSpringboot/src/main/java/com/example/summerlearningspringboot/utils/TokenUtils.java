package com.example.summerlearningspringboot.utils;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.summerlearningspringboot.entity.User;
import com.example.summerlearningspringboot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

/**
 * 功能:
 * 作者: 浅雨梦梨
 * 日期: 2023/9/15 19:48
 */
@Component
public class TokenUtils {

    @Autowired
    UserMapper userMapper;

    private static UserMapper staticUserMapper;

    @PostConstruct
    public void setUserService() {
        staticUserMapper = userMapper;
    }


    public static String creatToken (String userId, String sign) {
        return JWT.create().withAudience(userId).withExpiresAt(DateUtil.offsetHour(new Date(), 2)).sign(Algorithm.HMAC256(sign));
    }

    public static User getCurrentUser(){
        try {
            HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            String token = request.getHeader("token");
            if (StrUtil.isNotBlank(token)) {
                String userId = JWT.decode(token).getAudience().get(0);
                return staticUserMapper.selectById(Integer.valueOf(userId));
            }
        } catch (Exception e){
            return null;
        }
        return null;
    }


}
