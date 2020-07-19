package com.diandian.admin.merchant.modules.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diandian.admin.common.util.AssertUtil;
import com.diandian.admin.merchant.modules.biz.mapper.MerchantDebitCardInfoMapper;
import com.diandian.admin.merchant.modules.biz.service.MerchantDebitCardInfoService;
import com.diandian.admin.merchant.modules.biz.vo.merchant.MerchantDebitCardInfoVO;
import com.diandian.admin.merchant.modules.biz.vo.merchant.MerchantDebitCardPartInfoVO;
import com.diandian.admin.model.merchant.MerchantDebitCardInfoModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 商户银行卡信息表
 *
 * @author wbc
 * @date 2019/02/14
 */
@Service("merchantDebitCardInfoService")
@Slf4j
public class MerchantDebitCardInfoServiceImpl extends ServiceImpl<MerchantDebitCardInfoMapper, MerchantDebitCardInfoModel> implements MerchantDebitCardInfoService {
    @Override
    public void insertCardInfo(MerchantDebitCardInfoVO merchantDebitCardInfoVO) {
        MerchantDebitCardInfoModel merchantDebitCardInfoModel = new MerchantDebitCardInfoModel();
        BeanUtil.copyProperties(merchantDebitCardInfoVO, merchantDebitCardInfoModel);
        merchantDebitCardInfoModel.setDefaultFlag(1);
        this.baseMapper.insert(merchantDebitCardInfoModel);
    }

    @Override
    public List<MerchantDebitCardPartInfoVO> listCardInfo(Long id) {
        QueryWrapper<MerchantDebitCardInfoModel> wrapper = new QueryWrapper<>();
        wrapper.eq("merchant_id", id);
        List<MerchantDebitCardInfoModel> list = this.baseMapper.selectList(wrapper);
        List<MerchantDebitCardPartInfoVO> listCardPart = new ArrayList<>();
        if (list != null || list.size() != 0) {
            for (MerchantDebitCardInfoModel cardInfoModel : list) {
                MerchantDebitCardPartInfoVO cardPartInfoVO = new MerchantDebitCardPartInfoVO();
                cardPartInfoVO.setId(cardInfoModel.getId());
                String bankName = cardInfoModel.getBankName();
                AssertUtil.notBlank(bankName, "银行名不能为空");
                cardPartInfoVO.setBankName(bankName.length()>=4?bankName.substring(0, 4):bankName);
                String cardNumber = cardInfoModel.getCardNumber();
                cardPartInfoVO.setCardEndNumber(cardNumber.substring(cardNumber.length() - 4));
                cardPartInfoVO.setCardholderName(cardInfoModel.getCardholderName());
                cardPartInfoVO.setCardholderPhone(cardInfoModel.getCardholderPhone());
                cardPartInfoVO.setDefaultFlag(cardInfoModel.getDefaultFlag());
                listCardPart.add(cardPartInfoVO);
            }
        }
        if (listCardPart != null && listCardPart.size() == 1) {
            for (MerchantDebitCardPartInfoVO infoVO : listCardPart) {
                MerchantDebitCardInfoModel cardInfoModel = this.baseMapper.selectById(infoVO.getId());
                cardInfoModel.setDefaultFlag(0);
                infoVO.setDefaultFlag(0);
                this.baseMapper.updateById(cardInfoModel);
            }
        }
        return listCardPart;
    }

    @Override
    public void deleteCardInfo(Long id) {
        this.baseMapper.deleteById(id);
    }

    @Override
    public void updateCardInfo(Long cardId, Long merchantId) {
        QueryWrapper<MerchantDebitCardInfoModel> wrapper = new QueryWrapper<>();
        wrapper.eq("merchant_id", merchantId);
        List<MerchantDebitCardInfoModel> list = this.baseMapper.selectList(wrapper);
        AssertUtil.notEmpty(list, "没有绑定的银行卡信息");
        for (MerchantDebitCardInfoModel cardInfoModel : list) {
            Long id = cardInfoModel.getId();
            if (cardId.equals(id)) {
                cardInfoModel.setDefaultFlag(0);
            } else {
                cardInfoModel.setDefaultFlag(1);
            }
            this.baseMapper.updateById(cardInfoModel);
        }
    }

    @Override
    public MerchantDebitCardInfoModel getDebitCardInfo(String cardNumber) {
        QueryWrapper<MerchantDebitCardInfoModel> wrapper = new QueryWrapper<>();
        wrapper.eq("card_number", cardNumber);
        return this.baseMapper.selectOne(wrapper);
    }

    @Override
    public MerchantDebitCardInfoModel getDefaultCardInfo(Long merchantId, Integer defaultFlag) {
        QueryWrapper<MerchantDebitCardInfoModel> wrapper = new QueryWrapper<>();
        wrapper.eq("merchant_id", merchantId)
                .eq("default_flag", defaultFlag);
        return this.baseMapper.selectOne(wrapper);
    }
}
