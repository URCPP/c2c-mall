package com.diandian.dubbo.business.mapper.merchant;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.facade.model.merchant.MerchantShopClassifyModel;
import com.diandian.dubbo.facade.vo.BizCategorySetListVO;
import com.diandian.dubbo.facade.vo.merchant.MerchantShopClassifyVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @Author: byp
 * @Description:
 * @Date: Created in 20:24 2019/9/24
 * @Modified By:
 */
@Repository
public interface MerchantShopClassifyMapper extends BaseMapper<MerchantShopClassifyModel> {
    IPage<MerchantShopClassifyVo> listPage(Page page, @Param("params") Map<String,Object> params);
}
