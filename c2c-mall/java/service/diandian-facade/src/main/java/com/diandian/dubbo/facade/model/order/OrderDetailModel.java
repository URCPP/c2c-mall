package com.diandian.dubbo.facade.model.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import com.diandian.dubbo.facade.model.member.MemberOrderOptLogModel;
import com.diandian.dubbo.facade.model.merchant.MerchantProductInfoModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: yqingyuan
 * @Date: 2019/2/28 14:33
 * @Version 1.0
 */
@Data
@TableName("order_detail")
public class OrderDetailModel extends BaseModel {
    private static final long serialVersionUID = 1993429443271059530L;

    /**
     * 订单号
     */
    @TableField("order_no")
    private String orderNo;

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
     * 产品ID
     */
    @TableField("product_id")
    private Long productId;

    /**
     * 商户积分商城商品ID
     */
    @TableField("mch_product_id")
    private Long mchProductId;

    /**
     * 产品图片URL
     */
    @TableField("product_image_url")
    private String productImageUrl;

    /**
     * SKUID
     */
    @TableField("sku_id")
    private Long skuId;

    /**
     * sku名称
     */
    @TableField("sku_name")
    private String skuName;

    /**
     * 规格信息 逗号隔开
     */
    @TableField("spec_info")
    private String specInfo;

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
     * 价格
     */
    @TableField("price")
    private BigDecimal price;

    /**
     * 分享加价
     */
    @TableField("add_price")
    private BigDecimal addPrice;

    /**
     * 数量
     */
    @TableField("num")
    private Integer num;

    /**
     * 服务费
     */
    @TableField("service_fee")
    private BigDecimal serviceFee;

    /**
     * 收货人
     */
    @TableField("recv_name")
    private String recvName;

    /**
     * 收货电话
     */
    @TableField("recv_phone")
    private String recvPhone;

    /**
     * 收货地址
     */
    @TableField("recv_address")
    private String recvAddress;

    /**
     * 收货邮编
     */
    @TableField("recv_post_code")
    private String recvPostCode;

    /**
     * 运输费用
     */
    @TableField("transport_fee")
    private BigDecimal transportFee;

    /**
     * 运输费用介绍
     */
    @TableField("transport_fee_introduce")
    private String transportFeeIntroduce;

    /**
     * 运输方式ID
     */
    @TableField("transport_id")
    private Long transportId;

    /**
     * 运输方式名称
     */
    @TableField("transport_name")
    private String transportName;

    /**
     * 运输公司ID
     */
    @TableField("transport_company_id")
    private Long transportCompanyId;

    /**
     * 运输公司名称
     */
    @TableField("transport_company_name")
    private String transportCompanyName;

    /**
     * 售后标识 0正常 1售后状态
     */
    @TableField("after_sale_flag")
    private Integer afterSaleFlag;

    /**
     * 分润标识(0未分润 1已分润 2分润返还)
     */
    @TableField("share_flag")
    private Integer shareFlag;

    /**
     * 0待付款 1待发货 2已发货 3已完成 98关闭订单 99异常单
     */
    @TableField("state")
    private Integer state;


    /**
     * 支付时间
     */
    @TableField("pay_time")
    private Date payTime;

    /**
     * 发货时间
     */
    @TableField("transmit_time")
    private Date transmitTime;

    /**
     * 确认收货时间
     */
    @TableField("confirm_recv_time")
    private Date confirmRecvTime;

    /**
     * 完成时间
     */
    @TableField("complete_time")
    private Date completeTime;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 在售标识 M天猫 T淘宝 D京东
     */
    @TableField(exist = false)
    private String sellFlag;

    /**
     * 在售商品链接
     */
    @TableField(exist = false)
    private String sellUrl;

    @TableField(exist = false)
    private MerchantProductInfoModel merchantProductInfoModel;

    @TableField(exist = false)
    private MemberOrderOptLogModel memberOrderOptLog;


}
