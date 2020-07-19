package com.diandian.dubbo.facade.service.merchant;

import com.diandian.dubbo.facade.dto.PageResult;

import java.util.Map;

/**
 * 商户开通操作记录表
 *
 * @author jbh
 * @date 2019/02/25
 */
public interface MerchantOpenOptLogService {

    PageResult listPage(Map<String, Object> params);
}
