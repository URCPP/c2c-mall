package com.diandian.dubbo.facade.model.biz;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 软件类型
 *
 * @author zweize
 * @date 2019/02/18
 */
@Data
@TableName("biz_software_type")
public class BizSoftwareTypeModel extends BaseModel {

    private static final long serialVersionUID = 1L;


    /**
     * 类型编号
     */
    @TableField("type_code")
    private String typeCode;

    /**
     * 类型名称
     */
    @TableField("type_name")
    private String typeName;

    /**
     * 类型说明
     */
    @TableField("type_explain")
    private String typeExplain;

    /**
     * 开通价格
     */
    @TableField("opening_cost")
    private BigDecimal openingCost;

    /**
     * 续费价格
     */
    @TableField("renew_cost")
    private BigDecimal renewCost;

    /***
     * 开通时长，单位月
     */
    @TableField("opening_time_length")
    private Integer openingTimeLength;

    /**
     * 续费时长，单位 月
     */
    @TableField("renew_time_length")
    private Integer renewTimeLength;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 删除标记
     */
    @TableField("del_flag")
    private Integer delFlag;

    /**
     * 是否底价购买（1是2否）
     */
    @TableField("is_floor_price")
    private Integer isFloorPrice;

}
