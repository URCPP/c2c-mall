package com.diandian.dubbo.business.mapper.merchant;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.facade.dto.merchant.MerchantWeixinMpMaterialListDTO;
import com.diandian.dubbo.facade.dto.merchant.MerchantWeixinMpMaterialQueryDTO;
import com.diandian.dubbo.facade.model.merchant.MerchantWeixinMpMaterialModel;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * 商户微信公众号素材表 Mapper 接口
 * </p>
 *
 * @author cjunyuan
 * @since 2019-06-26
 */
public interface MerchantWeixinMpMaterialMapper extends BaseMapper<MerchantWeixinMpMaterialModel> {

    /**
     *
     * 功能描述: 公众号素材分页查询功能
     *
     * @param page
     * @param params
     * @return
     * @author cjunyuan
     * @date 2019/6/27 18:13
     */
    IPage<MerchantWeixinMpMaterialListDTO> listForPage(Page<MerchantWeixinMpMaterialListDTO> page, @Param("params") MerchantWeixinMpMaterialQueryDTO params);
}
