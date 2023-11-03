package com.example.summerlearningspringboot.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 功能:
 * 作者: 浅雨梦梨
 * 日期: 2023/9/10 17:11
 */
@Getter
@Setter
public class ServiceException extends RuntimeException{

    private String code;
    public ServiceException(){}
    public ServiceException(String msg){
        super(msg);
        this.code = "500";
    }

    public ServiceException(String code, String msg){
        super(msg);
        this.code = code;
    }
}
