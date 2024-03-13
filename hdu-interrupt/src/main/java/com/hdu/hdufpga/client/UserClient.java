package com.hdu.hdufpga.client;

import com.hdu.hdufpga.entity.Result;
import com.hdu.hdufpga.entity.po.UserPO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("hdu-account")
public interface UserClient {
    @RequestMapping("/co/user/create")
    Result create(UserPO userPO);

    @GetMapping("/co/user/getIdByUserName")
    List<Integer> getIdByUserName(List<String> userNameList);
}
