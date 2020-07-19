package com.diandian.dubbo.facade.model.merchant;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商户软件开通申请记录表
 *
 * @author wbc
 * @date 2019/02/20
 */
@Data
@TableName("merchant_open_apply_log")
public class MerchantOpenApplyLogModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 申请类型，0-付费（申请），1-免费（使用免费名额开通）
     */
    @TableField("apply_type")
    private Integer applyType;

    /**
     * 开通申请单号
     */
    @TableField("bill_no")
    private String billNo;

    /**
     * 商户ID
     */
    @TableField("merchant_id")
    private Long merchantId;

    /**
     * 软件类型ID
     */
    @TableField("soft_type_id")
    private Long softTypeId;

    /**
     * 开通类型(0 开通； 1 续费)
     */
    @TableField("type_flag")
    private Integer typeFlag;

    /**
     * 费用
     */
    @TableField("fee")
    private BigDecimal fee;

    /**
     * 申请状态( 0 申请进行； 1 申请成功； 2 申请失败)
     */
    @TableField("apply_state_flag")
    private Integer applyStateFlag;

    /**
     * 审核人ID
     */
    @TableField("audit_user_id")
    private Long auditUserId;

    /**
     * 审核时间
     */
    @TableField("audit_time")
    private Date auditTime;

    /**
     * 审核失败原因
     */
    @TableField("audit_fail_reason")
    private String auditFailReason;

    /**
     * 推荐人类型，0-机构，1-软件
     */
    @TableField("recommend_type")
    private Integer recommendType;

    /**
     * 推荐人ID
     */
    @TableField("recommend_id")
    private Long recommendId;

    /**
     * 上级类型
     */
    @TableField("parent_type")
    private Integer parentType;

    /**
     * 上级ID
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 是否支付（0-否，1-是）
     */
    @TableField("pay_flag")
    private Integer payFlag;

    /**
     * 在线支付（0-否，1-是）
     */
    @TableField("online_pay")
    private Integer onlinePay;

    /**
     * 开通费用
     */
    @TableField("opening_cost")
    private BigDecimal openingCost;

    /**
     * 绑定的采购券数量
     */
    @TableField("purchase_voucher_num")
    private Integer purchaseVoucherNum;

    /**
     * 采购券比例
     */
    @TableField("purchase_voucher_exchange_ratio")
    private BigDecimal purchaseVoucherExchangeRatio;

}
