package com.diandian.admin.merchant.modules.material.controller;

import cn.hutool.core.io.FileUtil;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.common.oss.AliyunStorageUtil;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.dubbo.facade.model.material.MaterialDetailModel;
import com.diandian.dubbo.facade.model.material.res.MaterialDetailRes;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.merchant.MerchantAttentionModel;
import com.diandian.dubbo.facade.model.merchant.MerchantCollectModel;
import com.diandian.dubbo.facade.service.material.MaterialDetailService;
import com.diandian.dubbo.facade.service.merchant.MerchantAttentionService;
import com.diandian.dubbo.facade.service.merchant.MerchantCollectService;
import com.diandian.dubbo.facade.service.merchant.MerchantInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/material")
public class MaterialDetailController extends BaseController {

    @Autowired
    private MaterialDetailService materialDetailService;

    @Autowired
    private MerchantCollectService merchantCollectService;

    @Autowired
    private MerchantAttentionService merchantAttentionService;

    @Autowired
    private MerchantInfoService merchantInfoService;

    @GetMapping("/findList")
    public RespData FindListByType(@RequestParam Map<String, Object> params) {
        Map map = new HashMap();
        Long merchantId = getMerchantInfoId();
        params.put("merchantId", merchantId);
        List<MaterialDetailModel> list = materialDetailService.FindList(params);
        List<MaterialDetailModel> ls=merchantCollectService.listCollect(merchantId,Long.valueOf(params.get("productId").toString()));
        map.put("me", materialDetailService.CountMe(Long.valueOf(params.get("productId").toString()), merchantId));
        map.put("news", materialDetailService.CountNews(Long.valueOf(params.get("productId").toString())));
        map.put("collect", ls.size());
        map.put("list", list);
        return RespData.ok(map);
    }

    @PostMapping("/clickCollect")
    public RespData clickCollect(Long id, Integer flag) {
        MerchantCollectModel model = new MerchantCollectModel();
        Long merchantId=getMerchantInfoId();
        if (flag == 0) {
            materialDetailService.clickCollect(id);
            model.setMaterialId(id);
            model.setMerchantId(getMerchantInfoId());
            merchantCollectService.saveCollect(model);
            return RespData.ok("收藏成功!");
        } else {
            materialDetailService.clickCollect(id);
            merchantCollectService.delCollect(id,merchantId);
            return RespData.ok("取消收藏成功!");
        }
    }


    @PostMapping("/upload")
    public RespData uploadAvatar(HttpServletRequest httpServletRequest) {
        List<String> list = new ArrayList<>(9);
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) httpServletRequest;
            MultiValueMap<String, MultipartFile> multiFileMap = multipartRequest.getMultiFileMap();
            for (List<MultipartFile> value : multiFileMap.values()) {
                for (MultipartFile file : value) {
                    if (file != null) {
                        String fileName = file.getOriginalFilename();
                        String fileExt = FileUtil.extName(fileName);
                        list.add(AliyunStorageUtil.uploadFile("diandian-business", file.getInputStream(), "." + fileExt));
                    }
                }
            }
        } catch (IOException e) {
            log.error("上传素材图片异常", e);
            RespData.fail(500, "上传失败");
        }
        return RespData.ok(list);
    }

//    @PostMapping("/upload")
//    public RespData upload(@RequestBody Map<String, String> params) {
//        return RespData.ok(AliyunStorageUtil.uploadBase64Img("diandian-business", params.get("file")));
//    }


    @GetMapping("/findMerchantMaterial")
    public RespData findMerchant(Long merchantId, Integer id) {
        Map<String, Object> map = new HashMap<>();
        MerchantInfoModel meId = getMerchantInfo();
        MerchantAttentionModel merchantAttentionModel = merchantAttentionService.isFollow(meId.getId(), merchantId);
        MerchantInfoModel merchantInfoModel = merchantInfoService.getMerchantInfoById(merchantId);
        List<MaterialDetailRes> list = materialDetailService.findMerchantMaterial(merchantId, id, meId);
        if (id == 0) {
            List<MaterialDetailRes> res = materialDetailService.findMerchantMaterial(merchantId, 1, meId);
            map.put("sourceMaterialCount", list.size());
            map.put("collectionCount", res.size());
        } else {
            List<MaterialDetailRes> res = materialDetailService.findMerchantMaterial(merchantId, 0, meId);
            map.put("sourceMaterialCount", res.size());
            map.put("collectionCount", list.size());
        }
        map.put("list", list);
        map.put("meId", merchantInfoModel);
        map.put("attention", merchantAttentionService.CountAttention(merchantId));
        map.put("fans", merchantAttentionService.CountFans(merchantId));
        map.put("isFollow", null == merchantAttentionModel ? 0 : merchantAttentionModel.getState());
        return RespData.ok(map);
    }


    @PostMapping("/issue")
    public RespData issue(@RequestBody MaterialDetailModel ma) {
        ma.setMerchantId(getMerchantInfoId());
        materialDetailService.save(ma);
        return RespData.ok();
    }


    @PostMapping("/download")
    public RespData download(Long id) {
        Long merchantId = getMerchantInfoId();
        materialDetailService.clickShare(id, merchantId);
        return RespData.ok();
    }
}