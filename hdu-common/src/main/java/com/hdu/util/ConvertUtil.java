package com.hdu.util;

import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ConvertUtil {
    //多个实体的复制
    public static <T> List<T> copyList(List source, Class<T> clazz) {
        List<T> target = new ArrayList<>();
        if (!CollectionUtil.isEmpty(source)) {
            if (!CollectionUtil.isEmpty(source)) {
                for (Object c : source) {
                    T obj = copy(c, clazz);
                    target.add(obj);
                }
            }
        }
        return target;
    }

    //单个实体之间的复制
    public static <T> T copy(Object source, Class<T> clazz) {
        if (source == null) {
            return null;
        }
        T obj = null;
        try {
            obj = clazz.newInstance();
        } catch (Exception e) {
            log.error(e.toString());
        }
        if (obj != null) {
            BeanUtils.copyProperties(source, obj);
        }
        return obj;
    }
}