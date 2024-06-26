package com.hdu.hdufpga.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.base.MPJBaseService;
import com.hdu.hdufpga.entity.BaseEntity;
import com.hdu.hdufpga.entity.PageRecord;
import com.hdu.hdufpga.entity.Result;
import com.hdu.hdufpga.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
public abstract class BaseController<S extends MPJBaseService<T>, T extends BaseEntity> {
    //此处必须使用Autowired
    @Autowired
    protected S service;

    @PostMapping("/create")
    public Result create(@RequestBody T t) {
        try {
            t.setCreateTime(TimeUtil.getNowTime());
            t.setUpdateTime(TimeUtil.getNowTime());
            if (service.save(t)) {
                return Result.ok(t);
            } else {
                return Result.error();
            }
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error();
        }
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody T t) {
        try {
            if (service.removeById(t.getId())) {
                return Result.ok();
            } else {
                return Result.error();
            }
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error();
        }
    }

    @PostMapping("/update")
    public Result update(@RequestBody T t) {
        try {
            t.setUpdateTime(TimeUtil.getNowTime());
            if (service.updateById(t)) {
                return Result.ok(t);
            } else {
                return Result.error();
            }
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error();
        }
    }

    @GetMapping("/get")
    public Result get(Integer id) {
        try {
            T t = service.getById(id);
            if (t != null) {
                return Result.ok(t);
            } else {
                return Result.error("没有找到对应的信息");
            }
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error();
        }
    }

    @GetMapping("/listPage")
    public Result listPage(Integer current, Integer size) {
        try {
            Page<T> page = service.page(new Page<>(current, size));
            PageRecord<T> pageRecord = new PageRecord<>(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords());
            return Result.ok(pageRecord);
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error();
        }
    }
}