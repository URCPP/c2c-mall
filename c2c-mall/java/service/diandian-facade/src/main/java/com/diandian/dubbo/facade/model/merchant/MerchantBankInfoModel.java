package com.diandian.dubbo.facade.model.merchant;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

/**
 * @Author: byp
 * @Description:
 * @Date: Created in 11:36 2019/10/28
 * @Modified By:
 */

@Data
@TableName("merchant_bank_info")
public class MerchantBankInfoModel extends BaseModel {

    @TableField("merchant_id")
    private Long merchantId;

    /**
     * 银行卡号
     */
    @TableField("bank_card_no")
    private String bankCardNo;

    /**
     * 银行名称
     */
    @TableField("bank_name")
    private String bankName;

    /**
     * 银行支行名称
     */
    @TableField("bank_branch_name")
    private String bankBranchName;

    /**
     * 开户人手机号码
     */
    @TableField("phone")
    private String phone;

    /**
     * 真实姓名
     */
    @TableField("real_name")
    private String realName;

    /**
     * 身份证号码
     */
    @TableField("id_card_no")
    private String idCardNo;

    /**
     * 身份证正面图片
     */
    @TableField("id_card_front_pic")
    private String idCardFrontPic;

    /**
     * 身份证反面图片
     */
    @TableField("id_card_reverse_pic")
    private String idCardReversePic;

    /**
     * 是否默认（0-否，1-是）
     */
    @TableField("default_flag")
    private Integer defaultFlag;
}
