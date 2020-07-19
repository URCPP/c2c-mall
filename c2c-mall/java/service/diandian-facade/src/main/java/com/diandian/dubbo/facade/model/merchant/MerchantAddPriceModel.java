package com.diandian.dubbo.facade.model.merchant;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Auther chensong
 * @Date 2019/9/8
 */
@Data
@TableName("merchant_addprice")
public class MerchantAddPriceModel extends BaseModel {
    private static final long serialVersionUID = 1L;

    /**
     * 定单详情ID
     */
    @TableField("order_detail_id")
    private Long orderDetailId;

    /**
     * 分享人ID
     */
    @TableField("merchant_id")
    private Long merchantId;

    /**
     * 订单号
     */
    @TableField("order_no")
    private String orderNo;


    /**
     * 加价金额
     */
    @TableField("add_price")
    private BigDecimal addPrice;

    /**
     * 状态
     */
    @TableField("flag")
    private Integer flag;

}
