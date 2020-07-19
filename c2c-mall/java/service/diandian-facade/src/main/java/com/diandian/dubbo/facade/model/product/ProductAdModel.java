package com.diandian.dubbo.facade.model.product;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 产品广告图
 *
 * @author zzhihong
 * @date 2019/03/05
 */
@Data
@TableName("product_ad")
public class ProductAdModel extends BaseModel {

	private static final long serialVersionUID = 1L;



 	/**
	 * 广告名称
	 */
    @TableField("ad_name")
	private String adName;

	/**
	 * 类型 0轮播图 1单图 2品牌商家 3优选专区 4爆款特推
	 */
	@TableField("ad_type")
    private Integer adType;

 	/**
	 * 简介
	 */
    @TableField("introduce")
	private String introduce;

 	/**
	 * 图片多个逗号隔开
	 */
    @TableField("image_url")
	private String imageUrl;

 	/**
	 * 产品ID
	 */
    @TableField("product_id")
	private Long productId;

	/**
	 * 链接类型  默认0 内部链接  1 外部链接
	 */
	@TableField("link_type")
	private Integer linkType;

	/**
	 * 链接名字(外部)
	 */
	@TableField("link_name")
	private String linkName;

	/**
	 * 排序
	 */
	@TableField("sort")
	private Integer sort;

 	/**
	 * 状态 0正常 1禁用
	 */
    @TableField("state")
	private Integer state;

	/**
	 * sku列表
	 */
	@TableField(exist = false)
	private List<ProductSkuModel> skuList;

	/**
	 * 价格
	 */
	@TableField(exist = false)
	private BigDecimal priceBZ;

	/**
	 * 差价
	 */
	@TableField(exist = false)
	private BigDecimal priceDifference;

    /**
	 * 零售价
	 */
	@TableField(exist = false)
	private BigDecimal price;
	@TableField(exist = false)
	private String shareImg;

	@TableField("create_by")
	private String createBy;

	/**
	 * 虚拟销量
	 */
	@TableField(exist = false)
	private Integer virtualSaleVolume;

	/**
	 * 真实销量
	 */
	@TableField(exist = false)
	private Integer saleVolume;

}
