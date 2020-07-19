package com.diandian.admin.business.modules.biz.controller;

import com.diandian.dubbo.facade.service.biz.BizCategorySetService;
import com.diandian.dubbo.facade.vo.BizCategorySetVO;
import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.model.biz.BizCategorySetModel;
import com.diandian.dubbo.facade.dto.PageResult;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
     * 获取类目列表、分页
     * @param params
     * @return
     */
    @GetMapping("/listPage")
    @RequiresPermissions("biz:bizCategorySet:list")
    public RespData listPage(@RequestParam Map<String,Object> params){
        PageResult pageResult = categorySetService.listPage(params);
        return RespData.ok(pageResult);
   }

    /**
     * 获取一级类目列表
     * @return
     */
    @GetMapping("/listOneCategory")
    @RequiresPermissions("biz:bizCategorySet:list")
    public RespData listOneCategory(){
        List<BizCategorySetVO> list = categorySetService.listOneCategory();
        return RespData.ok(list);
    }


    /**
     * 根据id删除
     * @return
     */
    @GetMapping("/delete/{id}")
    @RequiresPermissions("biz:bizCategorySet:delete")
    public RespData deleteCategory(@PathVariable Long id){
        categorySetService.deleteCategory(id);
        return RespData.ok();
    }

    /**
     * 修改
     * @return
     */
    @PostMapping("/update")
    @RequiresPermissions("biz:bizCategorySet:update")
    public RespData deleteCategory(@RequestBody BizCategorySetModel categorySetModel){
        categorySetModel.setUpdateTime(new Date());
        categorySetService.updateCategory(categorySetModel);
        return RespData.ok();
    }

    /**
     * 新增
     * @return
     */
    @PostMapping("/save")
    @RequiresPermissions("biz:bizCategorySet:add")
    public RespData saveCategory(@RequestBody BizCategorySetModel categorySetModel){
        Integer categoryTypeFlag = categorySetModel.getCategoryTypeFlag();
        if (categoryTypeFlag==0) {
            categorySetModel.setParentCategoryId(Long.valueOf(0));
        }
        categorySetService.saveCategory(categorySetModel);
        return RespData.ok();
    }

    /**
     *
     * 功能描述: 获取完整树
     *
     * @param
     * @return
     * @author cjunyuan
     * @date 2019/2/28 15:55
     */
    @GetMapping("/tree")
    @RequiresPermissions("biz:bizCategorySet:list")
    public RespData getIntactTree(){
        return RespData.ok(categorySetService.getIntactTreeList());
    }
}
