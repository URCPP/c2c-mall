package com.diandian.dubbo.business.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.facade.model.biz.BizAccountModel;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Map;


/**
 * 机构账号表
 *
 * @author zweize
 * @date 2019/02/18
 */
public interface BizAccountMapper extends BaseMapper<BizAccountModel> {

    Page<BizAccountModel> listAccountPage(Page<BizAccountModel> page, @Param("params")Map<String,Object> params);

    int updateBalance(@Param("orgId") Long orgId, @Param("oldBalance")BigDecimal oldBalance,@Param("newBalance") BigDecimal newBalance);

    int updateCommission(@Param("orgId") Long orgId, @Param("oldCommission")BigDecimal oldCommission,@Param("newCommission") BigDecimal newCommission);

    int updateBonus(@Param("orgId")Long orgId, @Param("oldBonus")BigDecimal oldBonus,@Param("newBonus")BigDecimal newBonus);

    int updateBonusTransferBalance(@Param("orgId")Long orgId, @Param("oldBonus")BigDecimal oldBonus,@Param("newBonus")BigDecimal newBonus);

    int updateCommissionTransferBalance(@Param("orgId") Long orgId);
}
