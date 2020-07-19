package com.diandian.dubbo.facade.model.product;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 产品信息
 *
 * @author zzhihongproduct_info
 * @date 2019/02/18
 */
@Data
@TableName("product_info")
public class ProductInfoModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 店铺ID
     */
    @TableField("shop_id")
    private Long shopId;

    /**
     * 店铺名称
     */
    @TableField("shop_name")
    private String shopName;

    /**
     * 产品名称
     */
    @TableField("product_name")
    private String productName;

    /**
     * 类目ID
     */
    @TableField("category_id")
    private Long categoryId;

    /**
     * 类目名
     */
    @TableField(exist = false)
    private String categoryName;

    /**
     * 商品简介
     */
    @TableField("introduce")
    private String introduce;

    /**
     * 商品详情
     */
    @TableField("product_html")
    private String productHtml;

    /**
     * 虚拟销量
     */
    @TableField("virtual_sale_volume")
    private Integer virtualSaleVolume;

    /**
     * 真实销量
     */
    @TableField("sale_volume")
    private Integer saleVolume;

    /**
     * 图片地址逗号隔开
     */
    @TableField("image_urls")
    private String imageUrls;

    /**
     * 兑换积分
     */
    @TableField("exchange_integral")
    private BigDecimal exchangeIntegral;

    /**
     *
     */
    @TableField("price")
    private BigDecimal price;
    /**
     * 售价
     */
    @TableField("sales_price")
    private BigDecimal salesPrice;

    /**
     * 库存
     */
    @TableField("product_stock")
    private Integer productStock;


    /**
     * 服务费率%
     */
    @TableField("service_rate")
    private BigDecimal serviceRate;

    /**
     * 状态 99=>下架,1=>上架,2=>预售,0=>未上架
     */
    @TableField("state")
    private Integer state;

    /**
     * 是否推荐 0 否 1是
     */
    @TableField("recommend_flag")
    private Integer recommendFlag;

    /**
     * 是否热门 0 否 1是
     */
    @TableField("hot_flag")
    private Integer hotFlag;

    /**
     * 是否新品 0否 1是
     */
    @TableField("new_flag")
    private Integer newFlag;

    /**
     * 是否精品 0否 1是
     */
    @TableField("perfect_flag")
    private Integer perfectFlag;

    /**
     * 最小购买数量
     */
    @TableField("min_buy_num")
    private Integer minBuyNum;

    /**
     * 是否支持购买样品（0-否，1-是）
     */
    @TableField("support_buy_sample")
    private Integer supportBuySample;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 重量（KG）
     */
    @TableField("weight")
    private BigDecimal weight;

    /**
     * 体积（立方）
     */
    @TableField("volume")
    private BigDecimal volume;

    /**
     * 运输方式(多种逗号隔开)
     */
    @TableField("transport_ids")
    private String transportIds;

    /**
     * 与运输方式对应的免运费数量（多个逗号隔开）
     */
    @TableField("free_shipping_num")
    private String freeShippingNum;

    /**
     * 在售标识 M天猫 T淘宝 D京东
     */
    @TableField("sell_flag")
    private String sellFlag;

    /**
     * 在售商品链接
     */
    @TableField("sell_url")
    private String sellUrl;

    /**
     * 删除标识 0未删除 1已删除
     */
    @TableField("del_flag")
    private Integer delFlag;


    /**
     * 创建人
     */
    @TableField("create_by")
    private Long createBy;

    /**
     * 更新人
     */
    @TableField("update_by")
    private Long updateBy;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 商品折扣
     */
    @TableField("discount")
    private BigDecimal discount;

    /**
     * 品牌ID
     */
    @TableField("brand_id")
    private Long brandId;

    /**
     * 运费
     */
    @TableField("freight")
    private BigDecimal freight;

    /**
     * 封面图
     */
    @TableField("cover_map")
    private String coverMap;

    /**
     * sku列表
     */
    @TableField(exist = false)
    private List<ProductSkuModel> skuList;

    /**
     * 标准价
     */
    @TableField(exist = false)
    private BigDecimal standardPrice;

    /**
     * 价格
     */
    @TableField(exist = false)
    private BigDecimal priceBZ;

    /**
     * 差价
     */
    @TableField(exist = false)
    private BigDecimal priceDifference;

    /**
     * 专属用户手机
     */
    @TableField("exclusive_member_phone")
    private String exclusiveMemberPhone;

    /**
     * 专属费率
     */
    @TableField("exclusive_rate")
    private BigDecimal exclusiveRate;

}
