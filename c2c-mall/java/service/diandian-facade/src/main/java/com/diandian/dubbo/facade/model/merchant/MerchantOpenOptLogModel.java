package com.diandian.dubbo.facade.model.merchant;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

/**
 * 商户开通操作记录表
 *
 * @author jbh
 * @date 2019/02/25
 */
@Data
@TableName("merchant_open_opt_log")
public class MerchantOpenOptLogModel extends BaseModel {

    private static final long serialVersionUID = 1L;


    /**
     * 单号
     */
    @TableField("bill_no")
    private String billNo;

    /**
     * 商户ID
     */
    @TableField("merchant_id")
    private Long merchantId;

    /**
     * 推荐人ID
     */
    @TableField("recommend_id")
    private Long recommendId;

    /**
     * 商户登陆名
     */
    @TableField("merchant_login_name")
    private String merchantLoginName;

    /**
     *
     */
    @TableField("soft_type_id")
    private Long softTypeId;

    /**
     * 操作类型(0 软件开通;)
     */
    @TableField("opt_type")
    private Integer optType;

    /**
     * 订单操作记录
     */
    @TableField("opt_record")
    private String optRecord;


    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

}
