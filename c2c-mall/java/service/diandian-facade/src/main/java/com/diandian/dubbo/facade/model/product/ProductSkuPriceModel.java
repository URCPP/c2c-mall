package com.diandian.dubbo.facade.model.product;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 产品sku商户价格
 *
 * @author zzhihong
 * @date 2019/02/20
 */
@Data
@TableName("product_sku_price")
public class ProductSkuPriceModel extends BaseModel {

	private static final long serialVersionUID = 1L;



 	/**
	 * SKU ID
	 */
    @TableField("sku_id")
	private Long skuId;

 	/**
	 * 软件类型ID
	 */
    @TableField("software_type_id")
	private Long softwareTypeId;

 	/**
	 * 软件类型名称
	 */
    @TableField("software_type_name")
	private String softwareTypeName;

 	/**
	 * 兑换积分
	 */
    @TableField("exchange_integral")
	private BigDecimal exchangeIntegral;

 	/**
	 * 售价
	 */
    @TableField("price")
	private BigDecimal price;



}
