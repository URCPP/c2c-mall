package com.diandian.dubbo.business.mapper.merchant;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diandian.dubbo.facade.model.biz.BizFinancialRecordsDetailModel;
import com.diandian.dubbo.facade.model.merchant.MerchantAddPriceModel;

import java.util.List;

public interface MerchantAddPriceMapper extends BaseMapper<MerchantAddPriceModel> {

    /**
     * 查询一天前的记录
     */
    List<MerchantAddPriceModel> selectRecord();
}
