package com.diandian.dubbo.facade.dto.merchant;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * @author cjunyuan
 * @date 2019/3/6 16:04
 */
@Getter
@Setter
@ToString
public class MerchantSoftInfoDTO implements Serializable {

    private static final long serialVersionUID = -853291334160450771L;

    private Long softTypeId;

    private Integer availableOpenNum;
}
