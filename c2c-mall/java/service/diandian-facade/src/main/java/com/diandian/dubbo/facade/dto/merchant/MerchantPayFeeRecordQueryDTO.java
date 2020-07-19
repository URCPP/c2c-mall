package com.diandian.dubbo.facade.dto.merchant;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 商户支付表查询对象
 * @author cjunyuan
 * @date 2019/3/7 21:41
 */
@Getter
@Setter
@ToString
public class MerchantPayFeeRecordQueryDTO implements Serializable {

    private Integer payType;

    private List<Long> merchantIdList;

    private String startTime;

    private String endTime;

    private String merchantName;

    private String merchantCode;

    private Integer curPage;

    private Integer pageSize;
}
