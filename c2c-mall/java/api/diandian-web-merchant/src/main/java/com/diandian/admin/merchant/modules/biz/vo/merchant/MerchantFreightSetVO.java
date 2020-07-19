package com.diandian.admin.merchant.modules.biz.vo.merchant;

import com.diandian.dubbo.facade.model.merchant.MerchantIntegralMallBannerModel;
import lombok.Data;

import java.util.List;

/**
 * @author jbuhuan
 * @date 2019/2/20 11:59
 */
@Data
public class MerchantFreightSetVO {
    private String freightTemplate;
    private Integer assumeFlag;
    private Integer productShowStyle;
    List<MerchantIntegralMallBannerModel> bannerList;
}
