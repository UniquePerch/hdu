package com.hdu.hdufpga.client;

import com.hdu.entity.Result;
import com.hdu.entity.po.UserPO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("hdu-account")
public interface UserClient {
    @RequestMapping("/admin/user/create")
    Result create(UserPO userPO);

    @GetMapping("/admin/user/getIdByUserName")
    List<Integer> getIdByUserName(List<String> userNameList);
}
