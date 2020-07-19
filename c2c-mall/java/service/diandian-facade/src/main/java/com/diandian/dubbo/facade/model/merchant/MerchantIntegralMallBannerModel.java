package com.diandian.dubbo.facade.model.merchant;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

import java.util.Objects;

/**
 * <p>
 * 商户积分商城banner表
 * </p>
 *
 * @author cjunyuan
 * @since 2019-04-30
 */
@Data
@TableName("merchant_integral_mall_banner")
public class MerchantIntegralMallBannerModel extends BaseModel {

    private static final long serialVersionUID = 7960814077680779367L;
    /**
     * 商户ID
     */
    @TableField("merchant_id")
    private Long merchantId;

    /**
     * banner图片路径
     */
    @TableField("banner_url")
    private String bannerUrl;

    /**
     * 跳转链接
     */
    @TableField("link_url")
    private String linkUrl;

    /**
     * 是否显示（0-否，1-是）
     */
    private Integer state;

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        MerchantIntegralMallBannerModel that = (MerchantIntegralMallBannerModel) o;
        return Objects.equals(super.getId(), that.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.getId());
    }
}
