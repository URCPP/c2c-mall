package com.diandian.dubbo.business.service.impl.merchant;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.dubbo.business.mapper.merchant.MerchantCollectMapper;
import com.diandian.dubbo.facade.model.material.MaterialDetailModel;
import com.diandian.dubbo.facade.model.merchant.MerchantAttentionModel;
import com.diandian.dubbo.facade.model.merchant.MerchantCollectModel;
import com.diandian.dubbo.facade.service.merchant.MerchantAttentionService;
import com.diandian.dubbo.facade.service.merchant.MerchantCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("merchantCollectService")
public class MerchantCollectServiceImpl implements MerchantCollectService {

    @Autowired
    private MerchantCollectMapper merchantCollectMapper;

    @Autowired
    private MerchantAttentionService merchantAttentionService;

    @Override
    public List<MaterialDetailModel> listCollect(Long merchantId, Long productId) {
        List<MaterialDetailModel> list = merchantCollectMapper.findCollect(merchantId, productId);

        for (MaterialDetailModel materialDetailModel : list) {
            MerchantAttentionModel ma = merchantAttentionService.isFollow(merchantId, materialDetailModel.getMerchantId());
            MerchantCollectModel mc = isCollect(materialDetailModel.getId(), merchantId);
            if (null == mc) {
                materialDetailModel.setCollectState(0);
            } else {
                materialDetailModel.setCollectState(1);
            }
            if (null == ma) {
                materialDetailModel.setAttentionFlag(0);
            } else {
                materialDetailModel.setAttentionFlag(1);
            }
            if (null != materialDetailModel.getMaterialImg()) {
                String[] arr = materialDetailModel.getMaterialImg().split(",");
                materialDetailModel.setImgUrl(arr);
            }
        }
        /*String img[]=new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setCollect(CountCollect(merchantId));
            img[i]=list.get(i).getMaterialImg();
            list.get(i).setImgUrl(img);
        }*/
        return list;

    }

    @Override
    public void saveCollect(MerchantCollectModel merchantCollectModel) {
        merchantCollectMapper.insert(merchantCollectModel);
    }

    @Override
    public boolean delCollect(Long id, Long merchantId) {
        QueryWrapper<MerchantCollectModel> qw = new QueryWrapper<>();
        qw.eq("material_id", id).eq("merchant_id", merchantId);
        return merchantCollectMapper.delete(qw) > 0;
    }

    @Override
    public MerchantCollectModel isCollect(Long id, Long merchantId) {
        return merchantCollectMapper.selectOne(
                new QueryWrapper<MerchantCollectModel>()
                        .eq("material_id", id)
                        .eq("merchant_id", merchantId)
        );

    }

    @Override
    public int CountCollect(Long merchantId) {
        QueryWrapper<MerchantCollectModel> qw = new QueryWrapper<>();
        qw.eq("merchant_id", merchantId);
        return merchantCollectMapper.selectCount(qw);
    }
}
