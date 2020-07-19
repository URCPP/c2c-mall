package com.diandian.dubbo.business.service.impl.merchant;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.diandian.dubbo.business.mapper.merchant.MerchantShopClassifyMapper;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.model.merchant.MerchantShopClassifyModel;
import com.diandian.dubbo.facade.service.merchant.MerchantShopClassifyService;
import com.diandian.dubbo.facade.vo.merchant.MerchantShopClassifyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 商户商铺分类服务实现类
 *
 *
 * @Author: byp
 * @Description:
 * @Date: Created in 21:04 2019/9/24
 * @Modified By:
 */

@Service("merchantShopClassifyService")
public class MerchantShopClassifyServiceImpl implements MerchantShopClassifyService {

   @Autowired
   private MerchantShopClassifyMapper merchantShopClassifyMapper;


    @Override
    public List<MerchantShopClassifyModel> listOne() {
        return merchantShopClassifyMapper.selectList(new QueryWrapper<MerchantShopClassifyModel>()
                .eq("category_type",0)
        );
    }

    @Override
    public List<MerchantShopClassifyModel> listTwo(Long id) {
        return merchantShopClassifyMapper.selectList(new QueryWrapper<MerchantShopClassifyModel>()
                .eq(ObjectUtil.isNotNull(id),"category_parent",id)

        );
    }

    @Override
    public MerchantShopClassifyModel getCategoryById(Long id) {
        return merchantShopClassifyMapper.selectOne(new QueryWrapper<MerchantShopClassifyModel>()
        .eq("id",id)
        );
    }

    @Override
    public PageResult listPage(Map<String, Object> params) {
//            Long shopId=Long.parseLong(params.get("shopId").toString());
        IPage<MerchantShopClassifyVo> classifyModelIPage=merchantShopClassifyMapper.listPage(
                new PageWrapper<MerchantShopClassifyVo>(params).getPage(),
                params);
        return new PageResult(classifyModelIPage);
    }

    @Override
    public void save(MerchantShopClassifyModel merchantShopClassifyModel) {
        merchantShopClassifyMapper.insert(merchantShopClassifyModel);
    }

    @Override
    public void deleteById(Long id) {
        merchantShopClassifyMapper.deleteById(id);
    }

    @Override
    public void updateById(MerchantShopClassifyModel merchantShopClassifyModel) {
        if (merchantShopClassifyModel.getPlatformUseFee()!=null){
            merchantShopClassifyModel.setPlatformUseFee(merchantShopClassifyModel.getPlatformUseFee().divide(BigDecimal.valueOf(100)));
        }
        if(merchantShopClassifyModel.getAnnualPromotionFee()!=null){
            merchantShopClassifyModel.setAnnualPromotionFee(merchantShopClassifyModel.getAnnualPromotionFee().divide(BigDecimal.valueOf(100)));
        }
        if (merchantShopClassifyModel.getTechnologyServiceFee()!=null){
            merchantShopClassifyModel.setTechnologyServiceFee(merchantShopClassifyModel.getTechnologyServiceFee().divide(BigDecimal.valueOf(100)));
        }
        merchantShopClassifyMapper.updateById(merchantShopClassifyModel);
    }
}
