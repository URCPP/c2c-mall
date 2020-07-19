package com.diandian.dubbo.business.task;

import com.diandian.dubbo.business.mapper.merchant.MerchantInfoMapper;
import com.diandian.dubbo.facade.model.biz.BizSoftwareTypeModel;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.merchant.MerchantAccountInfoModel;
import com.diandian.dubbo.facade.model.merchant.MerchantSoftInfoModel;
import com.diandian.dubbo.facade.model.merchant.MerchantWalletInfoModel;
import com.diandian.dubbo.facade.service.biz.BizFinancialRecordsDetailService;
import com.diandian.dubbo.facade.service.biz.BizFinancialRecordsService;
import com.diandian.dubbo.facade.service.biz.BizProductShareService;
import com.diandian.dubbo.facade.service.biz.BizSoftwareTypeService;
import com.diandian.dubbo.facade.service.merchant.MerchantAccountInfoService;
import com.diandian.dubbo.facade.service.merchant.MerchantInfoService;
import com.diandian.dubbo.facade.service.merchant.MerchantSoftInfoService;
import com.diandian.dubbo.facade.service.merchant.MerchantWalletInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

/**
 * @description:
 * @author: wsk
 * @create: 2019-09-05 14:55
 */
@Component
@Slf4j
public class taskService {

    @Autowired
    private BizFinancialRecordsDetailService bizFinancialRecordsDetailService;
    @Autowired
    private BizFinancialRecordsService bizFinancialRecordsService;

    /**
    * @Description: 结算余额，每天0：5分结算昨天的
    * @Param:
    * @return:
    * @Author: wsk
    * @Date: 2019/9/5
    */
    //@Scheduled(cron="0 */3 * * * ?")
    @Scheduled(cron="0 5 0 */1 * ?")
    public void grantFreezingBalance(){
        bizFinancialRecordsDetailService.grantFreezingBalance();
        //bizProductShareService.grantFreezingBalance();
    }

    /**
    * @Description:  每天记录财务记录，每天0：10分记录昨天的
    * @Param:
    * @return:
    * @Author: wsk
    * @Date: 2019/9/5
    */
    //@Scheduled(cron="0 */3 * * * ?")
    @Scheduled(cron="0 10 0 */1 * ?")
    public void recordShopProfit() throws ParseException {
        bizFinancialRecordsService.recordShopProfit();
    }
}