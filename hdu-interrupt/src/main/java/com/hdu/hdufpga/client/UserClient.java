package com.hdu.hdufpga.client;

import com.hdu.entity.Result;
import com.hdu.entity.po.UserPO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("hdu-account")
public interface UserClient {
    @RequestMapping("/admin/user/create")
    Result create(UserPO userPO);
}
