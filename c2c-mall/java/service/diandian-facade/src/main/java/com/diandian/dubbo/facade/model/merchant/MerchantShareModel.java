package com.diandian.dubbo.facade.model.merchant;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("merchant_share")
public class MerchantShareModel extends BaseModel {

    /**
     * 商户id
     */
    @TableField("merchant_id")
    private Long merchantId;


    /**
     * 商品id
     */
    @TableField("product_id")
    private Long productId;

    /**
     * 自定义加价
     */
    @TableField("custom_time")
    private BigDecimal customPrice;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
}
