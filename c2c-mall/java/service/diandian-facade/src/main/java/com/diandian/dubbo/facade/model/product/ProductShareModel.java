package com.diandian.dubbo.facade.model.product;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 产品分享
 *
 * @author chensong
 * @date 2019/09/03
 */
@Data
@TableName("product_share")
public class ProductShareModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 商户ID
     */
    @TableField("merchant_id")
    private Long merchantId;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 商品skuID
     */
    @TableField("sku_id")
    private Long skuId;

    /**
     * 是否被分享
     */
    @TableField("flag")
    private Integer flag;

    /**
     * 加价
     */
    @TableField("add_price")
    private BigDecimal addPrice;

    /**
     * 加价类型
     */
    @TableField("price_style")
    private Integer priceStyle;
}
