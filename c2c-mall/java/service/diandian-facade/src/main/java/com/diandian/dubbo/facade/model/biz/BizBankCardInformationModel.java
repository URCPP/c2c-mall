package com.diandian.dubbo.facade.model.biz;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

/**
 * @description:
 * @author: wsk
 * @create: 2019-09-08 11:14
 */
@Data
@TableName("biz_bank_card_information")
public class BizBankCardInformationModel extends BaseModel {

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
     * 店铺id
     */
    @TableField("shop_id")
    private Long shopId;

    /**
     * 银行名称
     */
    @TableField("bank_name")
    private String bankName;

    /**
     * 银行卡号
     */
    @TableField("bank_card_number")
    private String bankCardNumber;

    /**
     * 开户行
     */
    @TableField("opening_bank")
    private String openingBank;

    /**
     * 开户用户名称
     */
    @TableField("user_name")
    private String userName;

    /**
     * 开户预留手机号
     */
    @TableField("reserve_cell_phone_number")
    private String reserveCellPhoneNumber;

}