package com.diandian.dubbo.facade.model.product;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 产品sku
 *
 * @author zzhihong
 * @date 2019/02/20
 */
@Data
@TableName("product_sku")
public class ProductSkuModel extends BaseModel {

    private static final long serialVersionUID = 1L;


    /**
     * 产品ID
     */
    @TableField("product_id")
    private Long productId;


    /**
     * 店铺ID
     */
    @TableField("shop_id")
    private Long shopId;

    /**
     * sku名称
     */
    @TableField("sku_name")
    private String skuName;

    /**
     * 规格名1
     */
    @TableField("spec_name1")
    private String specName1;

    /**
     * 规格值1
     */
    @TableField("spec_value1")
    private String specValue1;



    /**
     * 删除标识 0未删除 1已删除
     */
    @TableField("del_flag")
    private Integer delFlag;


    /**
     * 价格列表
     */
    @TableField(exist = false)
    private List<ProductSkuPriceModel> priceList;

    /**
     * 库存列表
     */
    @TableField(exist = false)
    private List<ProductSkuStockModel> stockList;

    @TableField(exist = false)
    private BigDecimal addPrice;

}
