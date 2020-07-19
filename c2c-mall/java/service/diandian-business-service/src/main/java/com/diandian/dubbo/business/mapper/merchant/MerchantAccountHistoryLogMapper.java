package com.diandian.dubbo.business.mapper.merchant;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.facade.model.merchant.MerchantAccountHistoryLogModel;
import com.diandian.dubbo.facade.model.merchant.MerchantAccountInfoModel;
import org.apache.ibatis.annotations.Param;

import java.util.Map;


/**
 * 商户帐户流水
 * @author cjunyuan
 * @date 2019/2/22 13:33
 */
public interface MerchantAccountHistoryLogMapper extends BaseMapper<MerchantAccountHistoryLogModel> {

    MerchantAccountHistoryLogModel getFreezeBalanceByDate(@Param("merchantId")Long merchantId);

    IPage<MerchantAccountHistoryLogModel> listPage(Page page, @Param("params")Map params);

}
