package com.diandian.dubbo.facade.model.merchant;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商户的会员兑换订单记录表
 *
 * @author jbh
 * @date 2019/02/20
 */
@Data
@TableName("merchant_exchange_order_log")
public class MerchantExchangeOrderLogModel extends BaseModel {

    private static final long serialVersionUID = 1L;


    /**
     * 单号
     */
    @TableField("bill_no")
    private String billNo;

    /**
     * 商户ID
     */
    @TableField("merchant_id")
    private Long merchantId;

    /**
     * 商户名
     */
    @TableField("merchant_name")
    private String merchantName;

    /**
     * 会员ID
     */
    @TableField("member_id")
    private Long memberId;

    /**
     * 会员昵称
     */
    @TableField("member_nick_name")
    private String memberNickName;

    /**
     * 商品ID
     */
    @TableField("product_id")
    private Long productId;

    /**
     * 商品名称
     */
    @TableField("product_info")
    private String productInfo;

    /**
     * 商品图片
     */
    @TableField("product_pic_url")
    private String productPicUrl;

    /**
     * 商品单价
     */
    @TableField("product_unit_price")
    private BigDecimal productUnitPrice;

    /**
     *
     */
    @TableField("product_num")
    private Integer productNum;

    /**
     *
     */
    @TableField("freight_amount")
    private BigDecimal freightAmount;

    /**
     *
     */
    @TableField("total_amount")
    private BigDecimal totalAmount;

    /**
     *
     */
    @TableField("real_amount")
    private BigDecimal realAmount;


    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 收货人
     */
    @TableField("consignee")
    private String consignee;

    /**
     * 收货人电话
     */
    @TableField("consignee_phone")
    private String consigneePhone;

    /**
     * 收货人地址
     */
    @TableField("consignee_address")
    private String consigneeAddress;

    /**
     * 收货时间
     */
    @TableField("consignee_time")
    private Date consigneeTime;

    /**
     * 订单状态(0 待付款； 1 待发货； 2 待收货； 3 己完成； 4 己发货 ； 5 己取消)
     */
    @TableField("order_state_flag")
    private Integer orderStateFlag;

    /**
     * 物流单号
     */
    @TableField("track_number")
    private String trackNumber;

    /**
     * 支付状态(0 待付款，1 付款成功，2 付款失败)
     */
    @TableField("pay_state_flag")
    private Integer payStateFlag;

    /**
     * 发货人
     */
    @TableField("consigner")
    private String consigner;

    /**
     *
     */
    @TableField("consigner_phone")
    private String consignerPhone;

    /**
     * 发货地址
     */
    @TableField("consigner_address")
    private String consignerAddress;

    /**
     * 配送方式
     */
    @TableField("delivery_method")
    private String deliveryMethod;

    /**
     * 配送公司
     */
    @TableField("delivery")
    private String delivery;

}
