package com.diandian.dubbo.facade.service.biz;

import com.diandian.dubbo.facade.dto.PageResult;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * @description:
 * @author: wsk
 * @create: 2019-08-31 16:16
 */
public interface BizFinancialRecordsService {

    PageResult listPage(Map<String, Object> params);

    PageResult listPageByMonth(Map<String, Object> params);

    void recordShopProfit() throws ParseException;

}
