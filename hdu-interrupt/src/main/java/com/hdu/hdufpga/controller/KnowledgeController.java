package com.hdu.hdufpga.controller;

import com.hdu.controller.BaseController;
import com.hdu.hdufpga.entity.po.KnowledgePO;
import com.hdu.hdufpga.service.KnowledgeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/co/knowledge")
@RestController
@Slf4j
public class KnowledgeController extends BaseController<KnowledgeService, KnowledgePO> {
}
