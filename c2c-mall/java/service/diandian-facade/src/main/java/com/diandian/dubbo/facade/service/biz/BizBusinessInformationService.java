package com.diandian.dubbo.facade.service.biz;

import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.biz.BizBusinessInformationModel;

import java.util.Map;

/**
 * @description:
 * @author: wsk
 * @create: 2019-09-06 15:56
 */
public interface BizBusinessInformationService {

    PageResult listPage(Map<String, Object> params);

    BizBusinessInformationModel getByShopId(Long shopId);
}
