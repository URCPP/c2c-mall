package com.diandian.dubbo.facade.model.merchant;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

import java.util.Date;

/**
 * 商户收货地址设置表
 *
 * @author jbh
 * @date 2019/02/27
 */
@Data
@TableName("merchant_recipients_set")
public class MerchantRecipientsSetModel extends BaseModel {

    private static final long serialVersionUID = 1L;


    /**
     * 类型 0 公司 ；1
     */
    @TableField("type_flag")
    private Integer typeFlag;

    /**
     * 状态 0 正常；1 禁用
     */
    @TableField("state_flag")
    private Integer stateFlag;

    /**
     * 商户ID
     */
    @TableField("merchant_id")
    private Long merchantId;

    /**
     *省份ID
     */
    @TableField("province_code")
    private Integer provinceCode;

    /**
     * 省份
     */
    @TableField("province_name")
    private String provinceName;

    /**
     *城市ID
     */
    @TableField("city_code")
    private Integer cityCode;

    /**
     * 城市
     */
    @TableField("city_name")
    private String cityName;

    /**
     *区域ID
     */
    @TableField("area_code")
    private Integer areaCode;

    /**
     * 区域
     */
    @TableField("area_name")
    private String areaName;

    /**
     * 收货地址
     */
    @TableField("address")
    private String address;

    /**
     * 收货联系人
     */
    @TableField("concact_name")
    private String concactName;

    /**
     * 收货联系电话
     */
    @TableField("concact_phone")
    private String concactPhone;

    /**
     * 过期时间   null 为永久
     */
    @TableField("expire_time")
    private Date expireTime;

    /**
     * 是否默认( 0 否  ； 1 是)
     */
    @TableField("is_default")
    private Integer isDefault;


    /**
     * 备注
     */
    @TableField("remark")
    private String remark;


    /**
     * ip地址
     */
    @TableField("ip_address")
    private String ipAddress;

}
