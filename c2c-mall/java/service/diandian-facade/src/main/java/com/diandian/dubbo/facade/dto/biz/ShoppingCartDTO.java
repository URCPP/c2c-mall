package com.diandian.dubbo.facade.dto.biz;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ShoppingCartDTO implements Serializable {

    private String shopName;

    private List<ProductInfoDTO> productInfoDTOList;
}
