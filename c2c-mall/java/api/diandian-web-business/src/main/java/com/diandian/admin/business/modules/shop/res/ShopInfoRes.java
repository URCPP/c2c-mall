package com.diandian.admin.business.modules.shop.res;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @description:
 * @author: wsk
 * @create: 2019-09-02 14:48
 */
@Data
public class ShopInfoRes {

    /**
     * 店铺类型ID
     */
    private Long shopTypeId;

    /**
     * 店铺类型名称
     */
    private String shopTypeName;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 店铺介绍
     */
    private String introduce;

    /**
     * 联系人
     */
    private String contactName;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 删除标识0未删除 1已删除
     */
    private Integer delFlag;

    /**
     * 备注
     */
    private String remark;


    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 更新人
     */
    private Long updateBy;

    /**
     * 更新人
     */
    private Long merchantId;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 商家收益百分比
     */
    private BigDecimal profitProportion;

    /**
     * 底价计算百分比
     */
    private BigDecimal floorPriceProportion;

    /**
     * 后台登入用户名
     */
    private String loginName;

    /**
     * 密码
     */
    private String password;

    /**
     * 1管理员开通，2自行开通
     */
    private Integer type;
}