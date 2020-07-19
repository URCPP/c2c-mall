package com.diandian.admin.business.modules.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diandian.admin.business.modules.biz.mapper.BizPaymentPicMapper;
import com.diandian.admin.business.modules.biz.service.BizPaymentPicService;
import com.diandian.admin.model.biz.BizPaymentPicModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 机构打款凭证表
 *
 * @author zweize
 * @date 2019/02/18
 */
@Service("bizPaymentPicService")
@Slf4j
public class BizPaymentPicServiceImpl extends ServiceImpl<BizPaymentPicMapper, BizPaymentPicModel> implements BizPaymentPicService {

	@Autowired
	private BizPaymentPicMapper bizPaymentPicMapper;


}
