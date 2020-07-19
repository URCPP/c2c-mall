package com.diandian.dubbo.facade.model.merchant;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: byp
 * @Description:
 * @Date: Created in 19:58 2019/9/24
 * @Modified By:
 */

@Data
@TableName("merchant_shop_classify")
public class MerchantShopClassifyModel extends BaseModel {


    /**
     * 店铺编号
     */
    @TableField("shop_id")
    private Long shopId;


    /**
     * 分类名称
     */
    @TableField("classify_name")
    private  String  classifyName;


    /**
     * 上级分类
     */
    @TableField("category_parent")
    private Long categoryParent;


    /**
     * 分类类型
     */
    @TableField("category_type")
    private Integer categoryType;

    /**
     * 平台使用费
     */
    @TableField("platform_use_fee")
    private BigDecimal platformUseFee;

    /**
     * 技术服务费
     */
    @TableField("technology_service_fee")
    private BigDecimal technologyServiceFee;

    /**
     * 年度推广费
     */
    @TableField("annual_promotion_fee")
    private BigDecimal annualPromotionFee;

    /**
     * 单品保证金
     */
    @TableField("single_quality_deposit")
    private BigDecimal singleQualityDeposit;

    /**
     * 多品保证金
     */
    @TableField("multi_quality_insurance")
    private BigDecimal multiQualityInsurance;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
}
