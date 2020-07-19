package com.diandian.dubbo.facade.model.merchant;

import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

/**
 * <p>
 * 购物车
 * </p>
 *
 * @author zzj
 * @since 2019-02-26
 */
@Data
@TableName("merchant_shopping_cart")
public class MerchantShoppingCartModel extends BaseModel {

    private static final long serialVersionUID = 1L;

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
     * 规格名称
     */
    private String specName;

    /**
     * 规格值
     */
    private String specValue;

}
