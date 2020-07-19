package com.diandian.admin.business.modules.res.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.res.MoveGroupDTO;
import com.diandian.dubbo.facade.model.res.ResOssModel;
import com.diandian.dubbo.facade.service.res.ResOssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * @author x
 * @date 2019-02-15
 */
@Slf4j
@RestController
@RequestMapping("/resOss")
public class ResOssController {

    @Autowired
    private ResOssService resOssService;

    /**
     * 上传单张图片
     * @param map
     * @return
     * @throws Exception
     */
    @PostMapping("/uploadFile")
    public RespData uploadFile(@RequestBody Map<String,String> map) throws Exception{
        return RespData.ok(resOssService.uploadOneFile(map.get("urlData")));
    }

    /**
     * 上传素材
     * @param list
     * @return
     */
    @PostMapping("/upload")
    public RespData upload(@RequestBody List<Map<String, String>> list) {
        List<String> imageUrls = resOssService.upload(list);
        return RespData.ok(imageUrls);
    }

    /**
     * 分页列表
     * @param params
     * @return
     */
    @GetMapping("/listPage")
    public RespData listPage(@RequestParam Map<String, Object> params){
        PageResult pageResult = resOssService.listPage(params);
        return RespData.ok(pageResult);
    }
    @PostMapping("/updateById")
    public RespData updateById(@RequestBody ResOssModel resOssModel) {
        resOssService.updateById(resOssModel);
        return RespData.ok();
    }

    @PostMapping("/deleteById")
    public RespData deleteById(@RequestBody List<Long> ids) {
        resOssService.deleteById(ids);
        return RespData.ok();
    }
    @PostMapping("/moveGroup")
    public RespData moveGroup(@RequestBody MoveGroupDTO moveGroupDTO) {
        resOssService.moveGroup(moveGroupDTO);
        return RespData.ok();
    }
}
