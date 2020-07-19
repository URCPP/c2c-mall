package com.diandian.dubbo.facade.model.transport;

import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

/**
 * <p>
 * 3运输方式计费规则区域关系
 * </p>
 *
 * @author zzj
 * @since 2019-02-28
 */
@Data
@TableName("transport_fee_rule_area")
public class TransportFeeRuleAreaModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 运输方式计费规则ID
     */
    private Long transportRuleId;

    /**
     * 地区编码
     */
    private Integer regionCode;

}
