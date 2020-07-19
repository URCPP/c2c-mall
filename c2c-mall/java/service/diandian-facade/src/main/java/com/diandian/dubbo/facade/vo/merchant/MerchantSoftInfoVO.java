package com.diandian.dubbo.facade.vo.merchant;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jbuhuan
 * @date 2019/2/20 16:46
 */
@Data
public class MerchantSoftInfoVO implements Serializable {

    /**
     * ID
     */
    private Long id;
    /**
     * 商户ID
     */
    private Long merchantId;

    /**
     * 软件类型ID
     */
    private Long softTypeId;

    /**
     * 可用开通数量
     */
    private Integer availableOpenNum;

    /**
     * 己开通数量
     */
    private Integer openNum;
    /**
     * 软件类型名称
     */
    private String typeName;
}
