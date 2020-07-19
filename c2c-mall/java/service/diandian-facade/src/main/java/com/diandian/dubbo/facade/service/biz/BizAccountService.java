package com.diandian.dubbo.facade.service.biz;


import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.biz.AccountQueryDTO;
import com.diandian.dubbo.facade.model.biz.BizAccountModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 机构账号表
 *
 * @author zweize
 * @date 2019/02/18
 */
public interface BizAccountService {

    /**
     * 分页查询
     * @param params
     * @return
     */
    PageResult listPage(Map<String, Object> params);

    /**
     *
     * 功能描述: 根据ID获取
     *
     * @param accountId
     * @return
     * @author cjunyuan
     * @date 2019/2/20 20:28
     */
    BizAccountModel getById(Long accountId);

    /**
     *
     * 功能描述: 根据ID更新
     *
     * @param orgTypeStrategy
     * @return
     * @author cjunyuan
     * @date 2019/2/20 18:13
     */
    boolean updateById(BizAccountModel orgTypeStrategy);

    /**
     *
     * 功能描述: 获取单个账号
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/2/20 20:28
     */
    BizAccountModel getOne(AccountQueryDTO dto);


    /**
     * 获取账号列表
     * @param bizAccountModel
     * @return
     */
    List<BizAccountModel> list(BizAccountModel bizAccountModel);

    /**
     * 根据机构ID获取账号
     * @param orgId
     * @return
     */
    BizAccountModel getByOrgId(Long orgId);

    /**
     * 更新账号可用金额
     * @param orgId
     * @param oldBalance
     * @param newBalance
     * @return
     */
    boolean updateBalance(Long orgId, BigDecimal oldBalance,BigDecimal newBalance);

    /**
     * 更新账号待结算销售佣金
     * @param orgId
     * @param oldCommission
     * @param commission
     */
    boolean updateCommission(Long orgId, BigDecimal oldCommission,BigDecimal newCommission);

    /**
     * 更新奖金
     * @param orgId
     * @param oldBonus
     * @param newBonus
     * @return
     */
    boolean updateBonus(Long orgId, BigDecimal oldBonus,BigDecimal newBonus);

    /**
     * 账号，奖金转余额
     * @param orgId
     * @param oldBonus
     * @param newBonus
     * @return
     */
    boolean updateBonusTransferBalance(Long orgId, BigDecimal oldBonus,BigDecimal newBonus);

    /**
     * 账号，佣金赚余额
     * @param bizAccountModel
     * @return
     */
    boolean updateCommissionTransferBalance(BizAccountModel bizAccountModel);
}
