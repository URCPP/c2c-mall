package com.diandian.dubbo.business.service.impl.merchant;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.business.mapper.merchant.MerchantExchangeOrderLogMapper;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.model.merchant.MerchantExchangeOrderLogModel;
import com.diandian.dubbo.facade.service.merchant.MerchantExchangeOrderLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 商户的会员兑换订单记录表
 *
 * @author jbh
 * @date 2019/02/20
 */
@Service("merchantExchangeOrderLogService")
@Slf4j
public class MerchantExchangeOrderLogServiceImpl implements MerchantExchangeOrderLogService {
    @Autowired
    private MerchantExchangeOrderLogMapper exchangeOrderLogMapper;

    @Override
    public PageResult listPage(Map<String, Object> params) {
        String merchantName = (String) params.get("merchantName");
        String consignee = (String) params.get("consignee");
        String consigneePhone = (String) params.get("consigneePhone");
        String startTime = (String) params.get("startTime");
        String endTime = (String) params.get("endTime");
        String orderStateFlag = (String) params.get("orderStateFlag");
        Long merchantId = (Long) params.get("merchantId");
        Page<MerchantExchangeOrderLogModel> page = new PageWrapper<MerchantExchangeOrderLogModel>(params).getPage();
        QueryWrapper<MerchantExchangeOrderLogModel> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(merchantName), "merchant_name", merchantName);
        wrapper.eq(StringUtils.isNotBlank(consignee), "consignee", consignee);
        wrapper.eq(StringUtils.isNotBlank(consigneePhone), "consignee_phone", consigneePhone);
       /*// wrapper.eq(StringUtils.isNotBlank(orderStateFlag),"order_state_flag", Integer.valueOf(orderStateFlag));
        wrapper.eq("merchant_id", merchantId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        if (StringUtils.isNotBlank(startTime)) {
            Date newStartTime;
            try {
                newStartTime = sdf.parse(startTime);
                wrapper.gt("create_time", newStartTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (StringUtils.isNotBlank(endTime)) {
            Date newEndTime;
            try {
                newEndTime = sdf.parse(startTime);
                wrapper.lt("create_time", newEndTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }*/
        IPage<MerchantExchangeOrderLogModel> iPage = exchangeOrderLogMapper.selectPage(page, wrapper);
        return new PageResult(iPage);
    }
}
