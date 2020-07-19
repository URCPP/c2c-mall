package com.diandian.dubbo.facade.model.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商户帐户信息表
 *
 * @author wbc
 * @date 2019/02/18
 */
@Data
@TableName("user_account_info")
public class UserAccountInfoModel extends BaseModel {

    private static final long serialVersionUID = 1L;


    /**
     * 商户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 可用余额
     */
    @TableField("available_balance")
    private BigDecimal availableBalance;


    /**
     *
     */
    @TableField("remark")
    private String remark;

}
