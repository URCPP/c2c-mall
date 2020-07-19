package com.diandian.dubbo.facade.vo;

import com.diandian.dubbo.facade.model.product.ProductSkuPriceModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author wubc
 * @date 2019/2/14 23:25
 */
@Data
public class SkuVO implements Serializable {

    private Boolean isSelected = false;

    private Boolean isSelectedShow = false;


    /**
     * 总库存量
     */
    private Integer stockNumber;

    /**
     *
     */
    private Long skuId;

    /**
     * sku名称
     */
    private String skuName;


    /**
     * 产品ID
     */
    private Long productId;

    /**
     * 产品名称
     */
    private String productName;


    /**
     * 店铺ID
     */
    private Long shopId;

    /**
     * 店铺名称
     */
    private String shopName;


    /**
     * 规格名1
     */
    private String specName1;

    /**
     * 规格值1
     */
    private String specValue1;

    /**
     * 规格名2
     */
    private String specName2;

    /**
     * 规格值2
     */
    private String specValue2;

    /**
     * 规格名3
     */
    private String specName3;

    /**
     * 规格值3
     */
    private String specValue3;


    /**
     * 删除标识 0未删除 1已删除
     */
    private Integer delFlag;

    /**
     * sku串
     */
    private String skuStr;

    private Date createTime;

    private Date updateTime;

    /**
     * 价格列表
     */
    private List<ProductSkuPriceModel> priceList;


    /**
     * 类目ID
     */
    private Long categoryId;

    /**
     * 类目名
     */
    private String categoryName;

    /**
     * 商品简介
     */
    private String introduce;

    /**
     * 商品详情
     */
    private String productHtml;

    /**
     * 虚拟销量
     */
    private Integer virtualSaleVolume;

    /**
     * 真实销量
     */
    private Integer saleVolume;

    /**
     * 图片地址逗号隔开
     */
    private String imageUrls;

    /**
     * 兑换指导积分
     */
    private BigDecimal exchangeIntegral;

    /**
     * 指导售价
     */
    private BigDecimal price;

    /**
     * 状态 99=>下架,1=>上架,2=>预售,0=>未上架
     */
    private Integer state;

    /**
     * 是否推荐 0 否 1是
     */
    private Integer recommendFlag;

    /**
     * 是否热门 0 否 1是
     */
    private Integer hotFlag;

    /**
     * 是否新品 0否 1是
     */
    private Integer newFlag;

    /**
     * 是否精品 0否 1是
     */
    private Integer perfectFlag;

    /**
     * 最小购买数量
     */
    private Integer minBuyNum;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 运输方式(多种逗号隔开)
     */
    private String getTransportIds;

    /**
     * 服务费率
     */
    private BigDecimal serviceRate;

    /**
     * 重量（KG）
     */
    private BigDecimal weight;

    /**
     * 体积（立方）
     */
    private BigDecimal volume;

}
