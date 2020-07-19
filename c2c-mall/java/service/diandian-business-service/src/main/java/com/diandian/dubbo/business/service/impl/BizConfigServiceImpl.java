package com.diandian.dubbo.business.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.dubbo.business.mapper.BizConfigMapper;
import com.diandian.dubbo.common.oss.AliyunStorageUtil;
import com.diandian.dubbo.facade.common.exception.DubboException;
import com.diandian.dubbo.facade.model.biz.BizConfigModel;
import com.diandian.dubbo.facade.service.biz.BizConfigService;
import com.diandian.dubbo.facade.vo.BizConfigVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

/**
 * 平台总配置表
 *
 * @author jbh
 * @date 2019/03/14
 */
@Service("bizConfigService")
@Slf4j
public class BizConfigServiceImpl implements BizConfigService {

    @Autowired
    private BizConfigMapper bizConfigMapper;


    @Override
    public BizConfigModel getOne() {
        QueryWrapper<BizConfigModel> qw = new QueryWrapper<>();
        qw.eq("id", 1L);
        return bizConfigMapper.selectOne(qw);
    }

    @Override
    public void save(BizConfigVO vo) {
        String imageUrl = addApplyInfoFile(vo.getBusinessLicensePicBase64());
        BizConfigModel configModel = this.getOne();
        if (ObjectUtil.isNull(configModel)) {
            BizConfigModel model = new BizConfigModel();
            model.setAppUrl(vo.getAppUrl());
            model.setExchangeMallUrl(vo.getExchangeMallUrl());
            model.setHelpFlag(vo.getHelpFlag());
            model.setMallOpenFee(vo.getMallOpenFee());
            model.setMarginAmount(vo.getMarginAmount());
            model.setOfficialBusinessLicensePic(imageUrl);
            model.setOfficialTel(vo.getOfficialTel());
            model.setServerContact(vo.getServerContact());
            model.setId(1L);
            bizConfigMapper.insert(model);
        }else{
            configModel.setAppUrl(vo.getAppUrl());
            configModel.setExchangeMallUrl(vo.getExchangeMallUrl());
            configModel.setHelpFlag(vo.getHelpFlag());
            configModel.setMallOpenFee(vo.getMallOpenFee());
            configModel.setMarginAmount(vo.getMarginAmount());
            configModel.setOfficialBusinessLicensePic(imageUrl);
            configModel.setOfficialTel(vo.getOfficialTel());
            configModel.setServerContact(vo.getServerContact());
            bizConfigMapper.updateById(configModel);
        }
    }

    private String addApplyInfoFile(String base64Code) {
        if (!StringUtils.isBlank(base64Code)) {
            String suffix = base64Code.split(";")[0];
            String imagePattern = "data:image/(jpg|png|jpeg)";
            if (!Pattern.matches(imagePattern, suffix)) {
                throw new DubboException("素材格式错误");
            }
            String imageUrl = AliyunStorageUtil.uploadBase64Img("diandian-business", base64Code);
            return imageUrl;
        }
        return null;
    }
}
