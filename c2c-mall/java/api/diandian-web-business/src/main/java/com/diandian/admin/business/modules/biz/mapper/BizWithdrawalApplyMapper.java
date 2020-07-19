package com.diandian.admin.business.modules.biz.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.admin.model.biz.BizWithdrawalApplyModel;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 提现申请表
 *
 * @author zweize
 * @date 2019/02/18
 */
public interface BizWithdrawalApplyMapper extends BaseMapper<BizWithdrawalApplyModel> {

    Page<Map<String,Object>> listWithdrawalApplyPage(Page<Map<String,Object>> page, @Param("params")Map<String,Object> params);

}
