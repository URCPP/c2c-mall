package com.diandian.dubbo.facade.vo.merchant;

import com.diandian.dubbo.facade.vo.SkuVO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author jbuhuan
 * @date 2019/2/22 8:49
 */
@Data
public class MerchantProductInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;


    private Long id;

    /**
     * 商户ID
     */
    private Long merchantId;

    /**
     * 预售商品ID
     */
    private Long productId;



    /**
     * 兑换价格
     */
    private Integer exchangePrice;

    /**
     * 商品状态( 0 己下架 ;  1 己上架)
     */
    private Integer productStateFlag;

    /**
     * 排序号
     */
    private Integer sort;

    private String skuName;

    private String categoryName;



    /**
     *
     */
    private String remark;

    private Date createTime;

    private Date updateTime;

//    private ProductInfoModel productInfoModel;

    private Long skuId;

    private BigDecimal productCost;

    private Integer exchangeNum;

    private List<SkuVO> skuList;

    private SkuVO sku;

    private String transportIds;

    private String transportName;
}
