package com.diandian.dubbo.facade.model.shop;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 店铺信息
 *
 * @author zzhihong
 * @date 2019/02/26
 */
@Data
@TableName("shop_info")
public class ShopInfoModel extends BaseModel {

    private static final long serialVersionUID = 1L;


    /**
     * 店铺类型ID
     */
    @TableField("shop_type_id")
    private Long shopTypeId;

    /**
     * 店铺类型名称
     */
    @TableField(exist = false)
    private String shopTypeName;

    /**
     * 店铺头像
     */
    @TableField("shop_avatar")
    private String shopAvatar;

    /**
     * 店铺名称
     */
    @TableField("shop_name")
    private String shopName;

    /**
     * 店铺介绍
     */
    @TableField("introduce")
    private String introduce;

    /**
     * 商户Id
     */
    @TableField("merchant_id")
    private Long merchantId;

    /**
     * 主营一级分类Id
     */
    @TableField("classify_id_1")
    private String classifyId1;

    /**
     * 主营二级分类Id
     */
    @TableField("classify_id_2")
    private String classifyId2;

    /**
     *  单品质保金
     */
    @TableField("single_quality_deposit")
    private BigDecimal singleQualityDeposit;

    /**
     * 推荐码
     */
    @TableField("referral_code")
    private String referralCode;

    /**
     * 联系人
     */
    @TableField("contact_name")
    private String contactName;

    /**
     * 联系电话
     */
    @TableField("contact_phone")
    private String contactPhone;

    /**
     * 联系邮箱
     */
    @TableField("contact_email")
    private String contactEmail;

    /**
     * 联系QQ
     */
    @TableField("contact_qq")
    private String contactQQ;

    /**
     * 删除标识0未删除 1已删除
     */
    @TableField("del_flag")
    private Integer delFlag;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;


    /**
     * 创建人
     */
    @TableField("create_by")
    private Long createBy;

    /**
     * 更新人
     */
    @TableField("update_by")
    private Long updateBy;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 状态：0待审核，1成功，2拒绝
     */
    @TableField("state")
    private Integer state;

    /**
     * 平台抽点
     */
    @TableField("platform_profit")
    private BigDecimal platformProfit;

    /**
     * 底价计算百分比
     */
    @TableField("floor_price_proportion")
    private BigDecimal floorPriceProportion;

    /**
     * 代理抽点
     */
    @TableField("agent_profit")
    private BigDecimal agentProfit;

    /**
     * 支付状态: 0未交款，1已交款，2免交款
     */
    @TableField("pay_state")
    private Integer payState;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String password;

    @TableField(exist = false)
    private String reason;

    @TableField(exist = false)
    private Integer productNum;


}
