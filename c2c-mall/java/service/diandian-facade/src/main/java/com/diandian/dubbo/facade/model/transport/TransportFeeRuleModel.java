package com.diandian.dubbo.facade.model.transport;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 2运输方式计费规则
 * </p>
 *
 * @author zzj
 * @since 2019-02-28
 */
@Data
@TableName("transport_fee_rule")
public class TransportFeeRuleModel  extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 运输方式ID
     */
    private Long transportId;

    /**
     * 首值
     */
    private BigDecimal firstValue;

    /**
     * 运费
     */
    private BigDecimal firstFee;

    /**
     * 续值
     */
    private BigDecimal extValue;

    /**
     * 续费
     */
    private BigDecimal extFee;

    /**
     * 保费（运输保险费）
     */
    private BigDecimal insurance;


    /**
     * 运输费用规则区域list
     */
    @TableField(exist = false)
    private List<Integer> transportFeeRuleAreaList;


}
