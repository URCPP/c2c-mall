package com.diandian.admin.merchant.modules.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.admin.model.merchant.MerchantWithdrawApplyOptLogModel;
import org.apache.ibatis.annotations.Param;

import java.util.Map;


/**
 * 商家提现申请操用记录表
 *
 * @author jbh
 * @date 2019/02/26
 */
public interface MerchantWithdrawApplyOptLogMapper extends BaseMapper<MerchantWithdrawApplyOptLogModel> {

    /**
     * 提现操作记录列表、分页
     * @param page
     * @param params
     * @return
     */
   IPage<MerchantWithdrawApplyOptLogModel> listPage(Page page,@Param("params") Map<String,Object> params);
}
