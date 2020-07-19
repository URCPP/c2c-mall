package com.diandian.dubbo.facade.service.biz;

import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.biz.BizFinancialRecordsDetailModel;
import com.diandian.dubbo.facade.model.biz.BizFinancialRecordsModel;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: wsk
 * @create: 2019-09-03 15:10
 */
public interface BizFinancialRecordsDetailService {

    void insert(BizFinancialRecordsDetailModel bizFinancialRecordsDetailModel);

    PageResult listPage(Map<String, Object> params);

    void grantFreezingBalance();

    List<BizFinancialRecordsDetailModel> getByShopId(Long shopId);
}
