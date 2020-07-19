package com.diandian.dubbo.business.service.impl.merchant;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.diandian.dubbo.business.mapper.merchant.MerchantShoppingCartMapper;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.model.merchant.MerchantShoppingCartModel;
import com.diandian.dubbo.facade.model.product.ProductShareModel;
import com.diandian.dubbo.facade.service.merchant.MerchantShoppingCartService;
import com.diandian.dubbo.facade.service.product.ProductShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 购物车 服务实现类
 * </p>
 *
 * @author zzj
 * @since 2019-02-26
 */
@Service("merchantShoppingCartService")
public class MerchantShoppingCartServiceImpl implements MerchantShoppingCartService {
    @Autowired
    private MerchantShoppingCartMapper merchantShoppingCartMapper;

    @Override
    public PageResult listPage(Map<String, Object> params) {
        Long merchantId=(Long)params.get("merchantId");
        IPage<MerchantShoppingCartModel> merchantShoppingCartModelIPage = merchantShoppingCartMapper.selectPage(
                new PageWrapper<MerchantShoppingCartModel>(params).getPage(),
                new QueryWrapper<MerchantShoppingCartModel>().eq("merchant_id",merchantId).orderByDesc("id"));
        return new PageResult(merchantShoppingCartModelIPage);
    }
    @Override
    public MerchantShoppingCartModel getById(Long id) {
        return merchantShoppingCartMapper.selectById(id);
    }
    @Override
    public void updateById(MerchantShoppingCartModel merchantShoppingCartModel) {
        merchantShoppingCartMapper.updateById(merchantShoppingCartModel);
    }
    @Override
    public void save(MerchantShoppingCartModel merchantShoppingCartModel) {
        MerchantShoppingCartModel existMerchantShoppingCart = merchantShoppingCartMapper.selectOne(
                new QueryWrapper<MerchantShoppingCartModel>()
                        .eq("merchant_id",merchantShoppingCartModel.getMerchantId())
                        .eq("sku_id",merchantShoppingCartModel.getSkuId())
        );
        if(existMerchantShoppingCart==null){
            merchantShoppingCartMapper.insert(merchantShoppingCartModel);
        }else{
            existMerchantShoppingCart.setNum(existMerchantShoppingCart.getNum()+merchantShoppingCartModel.getNum());
            merchantShoppingCartMapper.updateById(existMerchantShoppingCart);
        }
    }
    @Override
    public void deleteById(Long id) {
        merchantShoppingCartMapper.deleteById(id);
    }


    @Override
    public void deleteByMchIdAndSkuIdList(Long merchantId, List<Long> skuIdList) {
        LambdaQueryWrapper<MerchantShoppingCartModel> qw = new LambdaQueryWrapper<>();
        qw.eq(MerchantShoppingCartModel::getMerchantId, merchantId);
        qw.in(MerchantShoppingCartModel::getSkuId, skuIdList);
        merchantShoppingCartMapper.delete(qw);
    }
}
