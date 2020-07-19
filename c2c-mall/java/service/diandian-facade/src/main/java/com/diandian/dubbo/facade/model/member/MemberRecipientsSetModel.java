package com.diandian.dubbo.facade.model.member;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

/**
 * 会员收货地址设置表
 *
 * @author wbc
 * @date 2019/03/13
 */
@Data
@TableName("member_recipients_set")
public class MemberRecipientsSetModel extends BaseModel {

    private static final long serialVersionUID = 1L;


    /**
     * 状态 0 正常；1 禁用
     */
    @TableField("state")
    private Integer state;

    /**
     * 会员ID
     */
    @TableField("member_id")
    private Long memberId;

    /**
     *
     */
    @TableField("province_code")
    private Integer provinceCode;

    /**
     * 所在省
     */
    @TableField("province_name")
    private String provinceName;

    /**
     *
     */
    @TableField("city_code")
    private Integer cityCode;

    /**
     * 所在市
     */
    @TableField("city_name")
    private String cityName;

    /**
     *
     */
    @TableField("area_code")
    private Integer areaCode;

    /**
     * 所在区
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
     * 邮政编码
     */
    @TableField("zip_code")
    private String zipCode;

    /**
     * 是否默认( 0 否  ； 1 是)
     */
    @TableField("default_flag")
    private Integer defaultFlag;


    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

}
