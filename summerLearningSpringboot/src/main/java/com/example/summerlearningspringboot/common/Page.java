package com.example.summerlearningspringboot.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

/**
 * 功能:
 * 作者: 浅雨梦梨
 * 日期: 2023/10/2 11:15
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Page<T> {

    private List<T> list;
    private Integer total;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page<?> page = (Page<?>) o;
        return Objects.equals(list, page.list) && Objects.equals(total, page.total);
    }

}
