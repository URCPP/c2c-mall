package com.diandian.admin.business.job;

import com.diandian.dubbo.facade.model.biz.BizAccountModel;
import com.diandian.dubbo.facade.service.biz.BizAccountService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;


/**
 * 每月佣金结算转余额
 *
 * @author x
 * @date 2018/09/29
 */
@JobHandler(value="monthSaleSettle")
@Component
@Slf4j
public class MonthSaleSettleJobHandler extends IJobHandler {

	@Autowired
	BizAccountService bizAccountService;

	@Override
	public ReturnT<String> execute(String param) throws Exception {
        // 获取所有账号
		List<BizAccountModel> accountList = bizAccountService.list(new BizAccountModel());
		for (BizAccountModel bizAccountModel : accountList){
			//机构账号 销售佣金 转 可用余额
			if (bizAccountModel.getCommission().compareTo(BigDecimal.ZERO) > 0){
				bizAccountService.updateCommissionTransferBalance(bizAccountModel);
			}
		}
		return SUCCESS;
	}

}
