package com.diandian.admin.merchant.modules.biz.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.service.biz.BizCategorySetService;
import com.diandian.dubbo.facade.vo.BizCategorySetVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author jbuhuan
 * @date 2019/2/22 13:44
 */
@RestController
@RequestMapping("/biz/bizCategorySet")
@Slf4j
public class BizCategorySetController {
    @Autowired
    private BizCategorySetService categorySetService;

    /**
     * 获取一级类目列表
     *
     * @return
     */
    @GetMapping("/listOneCategory")
    public RespData listOneCategory() {
        List<BizCategorySetVO> list = categorySetService.listOneCategory();
        return RespData.ok(list);
    }

    /**
     * 获取二级类目列表
     *
     * @return
     */
    @GetMapping("/listTwoCategory/{id}")
    public RespData listTwoCategory(@PathVariable Long id) {
        List<BizCategorySetVO> list = categorySetService.listTwoCategory(id);
        return RespData.ok(list);
    }


}
