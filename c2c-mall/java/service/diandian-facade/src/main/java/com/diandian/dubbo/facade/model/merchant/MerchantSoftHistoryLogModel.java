package com.diandian.dubbo.facade.model.merchant;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

/**
 * 商户软件变动历史表
 *
 * @author wbc
 * @date 2019/02/19
 */
@Data
@TableName("merchant_soft_history_log")
public class MerchantSoftHistoryLogModel extends BaseModel {

    private static final long serialVersionUID = 1L;


    /**
     * 商户ID
     */
    @TableField("merchant_id")
    private Long merchantId;

    /**
     *
     */
    @TableField("merchant_name")
    private String merchantName;

    /**
     *
     */
    @TableField("soft_type_id")
    private Long softTypeId;

    /**
     *
     */
    @TableField("soft_type_name")
    private String softTypeName;

    /**
     * 更新前数量
     */
    @TableField("pre_num")
    private Integer preNum;

    /**
     * 变动数量
     */
    @TableField("num")
    private Integer num;

    /**
     * 更新后金额
     */
    @TableField("post_num")
    private Integer postNum;

    /**
     * 业务类型（0-开通赠送，1-退回，2-消耗，3-管理员修改）
     */
    @TableField("business_type")
    private Integer businessType;


}
