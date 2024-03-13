package com.hdu.hdufpga.client;

import com.hdu.hdufpga.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("hdu-interrupt")
public interface ClassClient {
    @RequestMapping("/co/class/getStudentListByClassId")
    Result getStudentListByClassId();
}
