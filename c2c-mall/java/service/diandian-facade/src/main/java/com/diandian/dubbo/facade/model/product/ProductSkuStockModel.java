package com.diandian.dubbo.facade.model.product;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

/**
 * 产品sku库存
 *
 * @author zzhihong
 * @date 2019/02/21
 */
@Data
@TableName("product_sku_stock")
public class ProductSkuStockModel extends BaseModel {

	private static final long serialVersionUID = 1L;



 	/**
	 * 仓库ID
	 */
    @TableField("repository_id")
	private Long repositoryId;

 	/**
	 * 仓库名称
	 */
    @TableField("repository_name")
	private String repositoryName;

 	/**
	 * SKU ID
	 */
    @TableField("sku_id")
	private Long skuId;

 	/**
	 * 库存
	 */
    @TableField("stock")
	private Integer stock;



}
