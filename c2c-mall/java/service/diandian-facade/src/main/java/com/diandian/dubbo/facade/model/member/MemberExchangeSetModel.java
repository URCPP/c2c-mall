package com.diandian.dubbo.facade.model.member;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 会员兑换券充值设置表
 *
 * @author wbc
 * @date 2019/02/13
 */
@Data
@TableName("member_exchange_set")
public class MemberExchangeSetModel extends BaseModel {

    private static final long serialVersionUID = 1L;


    /**
     * 商户ID
     */
    @TableField("merchant_id")
    private Long merchantId;


    /**
     * 状态 0 正常；1 禁用；
     */
    @TableField("state_flag")
    private Integer stateFlag;

    /**
     * 兑换基准金额
     */
    @TableField("amount_base")
    private BigDecimal amountBase;

    /**
     * 兑换券比率
     */
    @TableField("exchange_rate")
    private Integer exchangeRate;

    /**
     * 购物券比率
     */
    @TableField("shopping_rate")
    private BigDecimal shoppingRate;

    /**
     * 是否显示兑换券( 0 是；1否)
     */
    @TableField("is_show_exchange_flag")
    private Integer isShowExchangeFlag;

    /**
     * 是否显示购物券( 0 是；1否)
     */
    @TableField("is_show_shopping_flag")
    private Integer isShowShoppingFlag;

    /**
     *
     */
    @TableField("remark")
    private String remark;

}
