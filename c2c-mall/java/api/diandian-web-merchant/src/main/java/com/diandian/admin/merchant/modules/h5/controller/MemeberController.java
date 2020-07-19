package com.diandian.admin.merchant.modules.h5.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.dubbo.facade.dto.member.MemberInfoDTO;
import com.diandian.dubbo.facade.service.member.MemberInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wubc
 * @date 2019/2/20 20:25
 */
@RestController
@RequestMapping("/biz/h5/member")
@Slf4j
public class MemeberController extends BaseController {


    @Autowired
    private MemberInfoService memberInfoService;


    /**
     * 商户会员注册
     */
    @RequestMapping("/register")
    public RespData register(@RequestBody MemberInfoDTO dto) {
        memberInfoService.memberRegister(dto);
        return RespData.ok();
    }
}
