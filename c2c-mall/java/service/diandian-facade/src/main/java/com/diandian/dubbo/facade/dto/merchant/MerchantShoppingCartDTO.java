package com.diandian.dubbo.facade.dto.merchant;

import com.diandian.dubbo.facade.model.merchant.MerchantShoppingCartModel;
import com.diandian.dubbo.facade.model.product.ProductInfoModel;

import java.io.Serializable;

public class MerchantShoppingCartDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private MerchantShoppingCartModel merchantShoppingCartModel;

    private ProductInfoModel productInfoModel;
}
