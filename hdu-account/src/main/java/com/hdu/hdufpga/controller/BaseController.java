package com.hdu.hdufpga.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hdu.entity.BaseEntity;
import com.hdu.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Slf4j
public abstract class BaseController <S extends IService<T>,T extends BaseEntity>{
    //此处必须使用Autowired
    @Autowired
    protected S service;

    @PostMapping("/create")
    public Result create(T t){
        Date now = new Date();
        t.setCreateTime(now);
        t.setUpdateTime(now);
        return service.save(t) ? Result.ok() : Result.error();
    }

    @PostMapping("/delete")
    public Result delete(Integer id){
        return service.removeById(id) ? Result.ok() : Result.error();
    }

    @PostMapping("/update")
    public Result update(T t){
        t.setUpdateTime(new Date());
        return service.updateById(t) ? Result.ok() : Result.error();
    }

    @GetMapping("/get")
    public Result get(Integer id){
        try {
            return Result.ok(service.getById(id));
        } catch (Exception e){
            log.error(e.toString());
            return Result.error();
        }
    }
}
