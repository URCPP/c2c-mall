package com.diandian.dubbo.facade.model.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zweize
 * @date 2019/02/21
 */
@Data
@TableName("order_info")
public class OrderInfoModel extends BaseModel {

    private static final long serialVersionUID = 1L;


    /**
     * 订单编号
     */
    @TableField("order_no")
    private String orderNo;


    /**
     * 商户订单编号
     */
    @TableField("mch_order_no")
    private String mchOrderNo;

    /**
     * 订单金额
     */
    @TableField("order_amount")
    private BigDecimal orderAmount;

    /**
     * 实付金额
     */
    @TableField("actual_amount")
    private BigDecimal actualAmount;

    /**
     * 分享加价
     */
    @TableField("addprice")
    private BigDecimal addPrice;

    /**
     * 商户ID(购买者)
     */
    @TableField("merchant_id")
    private Long merchantId;

    /**
     * 商户名称(购买者)
     */
    @TableField("merchant_name")
    private String merchantName;

    /**
     * 商户软件版本ID
     */
    @TableField("merchant_software_type_id")
    private Long merchantSoftwareTypeId;

    /**
     * 商户软件版本名称
     */
    @TableField("merchant_software_type_name")
    private String merchantSoftwareTypeName;

    /**
     * 订单类型 0金额订单 1 积分订单
     */
    @TableField("order_type")
    private Integer orderType;

    /**
     * 运费承担者（0-会员，1-商户）
     */
    @TableField("freight_bearer")
    private Integer freightBearer;


    /**
     * 支付状态 0 未支付 1已支付
     */
    @TableField("pay_state")
    private Integer payState;


    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 订单详情
     */
    @TableField(exist = false)
    private List<OrderDetailModel> detailList;

    /**
     * 下单会员ID
     */
    @TableField("member_id")
    private Long memberId;

    /**
     * 完整的树ID结
     */
    @TableField("tree_str")
    private String treeStr;

    @TableField("public_order_no")
    private String publicOrderNo;

}
