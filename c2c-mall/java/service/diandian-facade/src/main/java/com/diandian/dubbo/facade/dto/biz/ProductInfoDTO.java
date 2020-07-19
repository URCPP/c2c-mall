package com.diandian.dubbo.facade.dto.biz;

import com.diandian.dubbo.facade.model.product.ProductInfoModel;
import com.diandian.dubbo.facade.model.product.ProductSkuModel;
import com.diandian.dubbo.facade.model.product.ProductSkuPriceModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class ProductInfoDTO implements Serializable {
    /**
     * ID
     */
    private Long id;
    /**
     * 商户ID
     */
    private Long merchantId;

    /**
     * 产品ID
     */
    private Long productId;

    /**
     * SKU ID
     */
    private Long skuId;

    /**
     * 分享Id
     */
    private Long shareId;

    /**
     * 数量
     */
    private Integer num;

    /**
     * 仓库ID
     */
    private Long repositoryId;

    /**
     * 运输方式ID
     */
    private Long transportId;

    /**
     * 省钱
     */
    private BigDecimal provinceMoney;

    /**
     * 规格名称
     */
    private String specName;

    /**
     * 规格值
     */
    private String specValue;

    private ProductInfoModel productInfo;
    private ProductSkuModel productSku;
    private ProductSkuPriceModel productSkuPrice;
//    private ProductSkuStockModel productSkuStock;
}
