package com.diandian.dubbo.facade.model.merchant;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商户预售商品信息表
 *
 * @author jbh
 * @date 2019/02/21
 */
@Data
@TableName("merchant_product_info")
public class MerchantProductInfoModel extends BaseModel {

	private static final long serialVersionUID = 1L;


 
 	/**
	 * 商户ID
	 */
    @TableField("merchant_id")
	private Long merchantId;
 
 	/**
	 * 预售商品ID
	 */
    @TableField("product_id")
	private Long productId;

	/**
	 * sku
	 */
	@TableField("sku_id")
	private Long skuId;


	/**
	 * sku名称
	 */
	@TableField("sku_name")
	private String skuName;

	/**
	 * 商品分类ID
	 */
	@TableField("category_id")
	private Long categoryId;

	/**
	 * 商品分类
	 */
	@TableField("category_name")
	private String categoryName;

 	/**
	 * 兑换价格
	 */
    @TableField("exchange_price")
	private Integer exchangePrice;

 	/**
	 * 商品价格
	 */
    @TableField("product_cost")
	private BigDecimal productCost;

	/**
	 * 商品己兑换数量
	 */
	@TableField("exchange_num")
	private Integer exchangeNum;

 	/**
	 * 商品状态( 0 己下架 ;  1 己上架)
	 */
    @TableField("product_state_flag")
	private Integer productStateFlag;
 
 	/**
	 * 排序号
	 */
    @TableField("sort")
	private Integer sort;
 
 
 
 	/**
	 * 
	 */
    @TableField("remark")
	private String remark;

	@TableField("del_flag")
	private Integer delFlag;

	@TableField("transport_ids")
	private String transportIds;




}
