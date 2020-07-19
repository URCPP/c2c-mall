package com.diandian.dubbo.business.service.impl.merchant;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.dubbo.business.mapper.merchant.MerchantRecipientsSetMapper;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.util.AssertUtil;
import com.diandian.dubbo.facade.model.merchant.MerchantRecipientsSetModel;
import com.diandian.dubbo.facade.service.merchant.MerchantRecipientsSetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商户收货地址设置表
 *
 * @author jbh
 * @date 2019/02/27
 */
@Service("merchantRecipientsSetService")
@Slf4j
public class MerchantRecipientsSetServiceImpl implements MerchantRecipientsSetService {

    @Autowired
    private MerchantRecipientsSetMapper merchantRecipientsSetMapper;


    @Override
    public boolean save(MerchantRecipientsSetModel model) {

        String concactName = model.getConcactName();
        AssertUtil.isTrue(StrUtil.isNotBlank(concactName), "收货人不能为空");
        String concactPhone = model.getConcactPhone();
        AssertUtil.isTrue(StrUtil.isNotBlank(concactPhone), "收货联系电话不能为空");

        if(model.getIsDefault()==1){
            /*MerchantRecipientsSetModel merchantRecipientsSetModel=merchantRecipientsSetMapper.selectOne(
                    new QueryWrapper<MerchantRecipientsSetModel>()
                            .eq("is_default",BizConstant.MerRecipientsDefault.DEFAULT)
                            .eq("merchant_id",model.getMerchantId())
            );
            if(null!=merchantRecipientsSetModel){
                merchantRecipientsSetModel.setIsDefault(0);
                merchantRecipientsSetMapper.updateById(merchantRecipientsSetModel);
            }*/
            List<MerchantRecipientsSetModel> merchantRecipientsSetModels=merchantRecipientsSetMapper.selectList(
                    new QueryWrapper<MerchantRecipientsSetModel>()
                            .eq("is_default",BizConstant.MerRecipientsDefault.DEFAULT.value())
                            .eq("merchant_id",model.getMerchantId())
            );
            for (MerchantRecipientsSetModel merchantRecipientsSetModel:merchantRecipientsSetModels){
                merchantRecipientsSetModel.setIsDefault(0);
                merchantRecipientsSetMapper.updateById(merchantRecipientsSetModel);
            }
            //AssertUtil.isTrue(null!=merchantRecipientsSetModel, "默认收货地址只能有一个");
        }

        model.setTypeFlag(BizConstant.MerRecipientsType.COMPANY.value());
        model.setStateFlag(BizConstant.MerRecipientsState.NORMAL.value());
        merchantRecipientsSetMapper.insert(model);
        return true;
    }

    @Override
    public boolean update(MerchantRecipientsSetModel model) {
        Long id = model.getId();
        if (null != id) {
            MerchantRecipientsSetModel set = merchantRecipientsSetMapper.selectById(id);
            if (ObjectUtil.isNotNull(set)) {
                if(model.getIsDefault()==1){
                    List<MerchantRecipientsSetModel> merchantRecipientsSetModels=merchantRecipientsSetMapper.selectList(
                            new QueryWrapper<MerchantRecipientsSetModel>()
                                    .eq("is_default",BizConstant.MerRecipientsDefault.DEFAULT.value())
                                    .eq("merchant_id",model.getMerchantId())
                    );
                    for (MerchantRecipientsSetModel merchantRecipientsSetModel:merchantRecipientsSetModels){
                        merchantRecipientsSetModel.setIsDefault(BizConstant.MerRecipientsDefault.UNDEFAULT.value());
                        merchantRecipientsSetMapper.updateById(merchantRecipientsSetModel);
                    }
                    /*if (null!=merchantRecipientsSetModel){
                        merchantRecipientsSetModel.setIsDefault(0);
                        merchantRecipientsSetMapper.updateById(merchantRecipientsSetModel);
                    }*/
                    /*AssertUtil.isTrue(null!=merchantRecipientsSetModel, "默认收货地址只能有一个");*/
                }
                set.setConcactName(model.getConcactName());
                set.setConcactPhone(model.getConcactPhone());
                set.setAddress(model.getAddress());
                set.setProvinceCode(model.getProvinceCode());
                set.setProvinceName(model.getProvinceName());
                set.setCityCode(model.getCityCode());
                set.setCityName(model.getCityName());
                set.setAreaCode(model.getAreaCode());
                set.setAreaName(model.getAreaName());
                set.setIsDefault(model.getIsDefault());
                merchantRecipientsSetMapper.updateById(set);
            }
        }
        return true;
    }

    @Override
    public List<MerchantRecipientsSetModel> list(Map<String, Object> params) {
        Long merchantId=(Long)params.get("merchantId");
        QueryWrapper<MerchantRecipientsSetModel> wrapper = new QueryWrapper<>();
        wrapper.eq(ObjectUtil.isNotNull(merchantId),"merchant_id",merchantId).orderByDesc("is_default");
        return merchantRecipientsSetMapper.selectList(wrapper);
    }

    @Override
    @Transactional
    public boolean setDefault(Long id) {

        AssertUtil.isTrue(ObjectUtil.isNotNull(id), "收货地址不存在");
        MerchantRecipientsSetModel set = merchantRecipientsSetMapper.selectById(id);
        AssertUtil.isTrue(ObjectUtil.isNotNull(set), "收货地址不存在");

        QueryWrapper<MerchantRecipientsSetModel> qw = new QueryWrapper<>();
        qw.eq("is_default", BizConstant.MerRecipientsDefault.DEFAULT.value());
        List<MerchantRecipientsSetModel> defaultList = merchantRecipientsSetMapper.selectList(qw);
        if (defaultList.size() > 0) {
            for (int i = 0; i < defaultList.size(); i++) {
                MerchantRecipientsSetModel merchantRecipientsSetModel = defaultList.get(i);
                merchantRecipientsSetModel.setIsDefault(BizConstant.MerRecipientsDefault.UNDEFAULT.value());
                merchantRecipientsSetMapper.updateById(merchantRecipientsSetModel);
            }
        }
        set.setIsDefault(BizConstant.MerRecipientsDefault.DEFAULT.value());
        merchantRecipientsSetMapper.updateById(set);
        return true;
    }

    @Override
    public MerchantRecipientsSetModel getMchDefaultShippingAddress(Long mchId){
        QueryWrapper<MerchantRecipientsSetModel> wrapper = new QueryWrapper<>();
        wrapper.eq("merchant_id", mchId);
        return merchantRecipientsSetMapper.selectOne(wrapper);
    }

    @Override
    public void delete(Long id) {
        merchantRecipientsSetMapper.deleteById(id);
    }

    @Override
    public MerchantRecipientsSetModel selectOneById(Long id) {
        return merchantRecipientsSetMapper.selectById(id);
    }

}
