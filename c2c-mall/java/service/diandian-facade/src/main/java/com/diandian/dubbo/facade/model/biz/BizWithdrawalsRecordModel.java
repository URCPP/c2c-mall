package com.diandian.dubbo.facade.model.biz;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import com.diandian.dubbo.facade.model.shop.ShopInfoModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @description:
 * @author: wsk
 * @create: 2019-09-10 21:00
 */
@Data
@TableName("biz_withdrawals_record")
public class BizWithdrawalsRecordModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 创建人
     */
    @TableField("create_by")
    private String createBy;

    /**
     * 更新人
     */
    @TableField("update_by")
    private String updateBy;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 删除标识
     */
    @TableField("del_flag")
    private Integer delFlag;

    /**
     * 申请店铺id
     */
    @TableField("shop_id")
    private Long shopId;

    /**
     * 提现金额
     */
    @TableField("cash_withdrawal_amount")
    private BigDecimal cashWithdrawalAmount;

    /**
     * 银行卡id
     */
    @TableField("bank_id")
    private Long bankId;

    /**
     * 状态（0：未发放，1：已发放，2：拒绝）
     */
    @TableField("state")
    private Integer state;

    /**
     * 手续费
     */
    @TableField("service_charge")
    private BigDecimal serviceCharge;

    @TableField(exist = false)
    private BizBankCardInformationModel bank;

    @TableField(exist = false)
    private ShopInfoModel shop;
}