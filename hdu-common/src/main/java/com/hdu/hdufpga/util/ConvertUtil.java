package com.hdu.hdufpga.util;

import cn.hutool.core.convert.Convert;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ConvertUtil {
    //多个实体的复制
    public static <T> List<T> copyList(List source, Class<T> clazz) {
        List<T> target = new ArrayList<>();
        for (Object o : source) {
            target.add(copy(o, clazz));
        }
        return target;
    }

    //单个实体之间的复制
    public static <T> T copy(Object source, Class<T> clazz) {
        return Convert.convert(clazz, source);
    }
}