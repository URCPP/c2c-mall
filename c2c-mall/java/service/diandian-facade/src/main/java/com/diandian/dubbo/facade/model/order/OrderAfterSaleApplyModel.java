package com.diandian.dubbo.facade.model.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

/**
 * 订单售后
 *
 * @author yqingyuan
 * @date 2019/03/05
 */
@Data
@TableName("order_after_sale_apply")
public class OrderAfterSaleApplyModel extends BaseModel {

	private static final long serialVersionUID = 1L;


 
 	/**
	 * 订单号
	 */
    @TableField("order_no")
	private String orderNo;
 
 	/**
	 * 订单详情ID
	 */
    @TableField("order_detail_id")
	private Long orderDetailId;
 
 	/**
	 * 店铺ID
	 */
    @TableField("shop_id")
	private Long shopId;
 
 	/**
	 * 店铺名称
	 */
    @TableField("shop_name")
	private String shopName;
 
 	/**
	 * 商户ID
	 */
    @TableField("merchant_id")
	private Long merchantId;
 
 	/**
	 * 商户名称
	 */
    @TableField("merchant_name")
	private String merchantName;
 
 	/**
	 * 理由
	 */
    @TableField("reason")
	private String reason;
 
 	/**
	 * 描述
	 */
    @TableField("introduce")
	private String introduce;
 
 	/**
	 * 图片
	 */
    @TableField("image_urls")
	private String imageUrls;
 
 	/**
	 * 售后类型 1退款 2退换货
	 */
    @TableField("after_sale_type")
	private Integer afterSaleType;
 
 	/**
	 * 0申请售后 1售后中 2售后完成 3拒绝售后 4撤销售后
	 */
    @TableField("state")
	private Integer state;
 
 
 
}
