package com.diandian.admin.merchant.modules.biz.vo.merchant;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MerchantUpgradeLevelVo {
    /**
     * 商城店铺名称
     */
    private String mallName;

    /**
     * 商城店铺LOGO
     */
    private String mallLogo;

    /**
     * 升级类型
     */
    private Integer level;

    /**
     *升级金额
     */
    private BigDecimal upgradeMoney;


}
