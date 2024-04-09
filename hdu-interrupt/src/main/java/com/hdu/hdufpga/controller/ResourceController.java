package com.hdu.hdufpga.controller;

import com.hdu.hdufpga.entity.po.ResourcePO;
import com.hdu.hdufpga.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resource")
@Slf4j
public class ResourceController extends BaseController<ResourceService, ResourcePO> {
}
