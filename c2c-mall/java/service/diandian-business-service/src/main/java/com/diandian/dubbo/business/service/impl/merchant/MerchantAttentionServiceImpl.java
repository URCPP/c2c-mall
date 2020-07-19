package com.diandian.dubbo.business.service.impl.merchant;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.dubbo.business.mapper.material.MaterialDetailMapper;
import com.diandian.dubbo.business.mapper.merchant.MerchantAttentionMapper;
import com.diandian.dubbo.business.mapper.merchant.MerchantInfoMapper;
import com.diandian.dubbo.facade.model.material.MaterialDetailModel;
import com.diandian.dubbo.facade.model.merchant.MerchantAttentionModel;
import com.diandian.dubbo.facade.service.merchant.MerchantAttentionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("merchantAttentionService")
public class MerchantAttentionServiceImpl implements MerchantAttentionService {

    @Autowired
    private MerchantAttentionMapper merchantAttentionMapper;

    @Autowired
    private MaterialDetailMapper materialDetailMapper;



    /**
     * 返回查找关注列表
     * @param merchantId
     * @return
     */
    @Override
    public List<MerchantAttentionModel> findAttention(Long merchantId) {
        return merchantAttentionMapper.listAttention(merchantId);
    }

    /**
     * 返回查找粉丝列表
     * @param focusMerchantId
     * @return
     */

    @Override
    public List<MerchantAttentionModel> findFans(Long focusMerchantId) {
        return merchantAttentionMapper.listFans(focusMerchantId);
    }

    @Override
    public MerchantAttentionModel findOnlyOne(Long merchantId, Long focusMerchantId) {
        QueryWrapper<MerchantAttentionModel> qw=new QueryWrapper<>();
        qw.eq("merchant_id",merchantId).eq("focus_merchant_id",focusMerchantId);
        return merchantAttentionMapper.selectOne(qw);
    }

    /**
     * 添加关注列表
     * @param mer 关注实体类
     */
    @Override
    public void saveAttention(MerchantAttentionModel mer) {
        merchantAttentionMapper.insert(mer);
    }

    @Override
    public void delAttention(Long merchantId,Long id) {
        QueryWrapper<MerchantAttentionModel> qw=new QueryWrapper<>();
        qw.eq("merchant_id",merchantId).eq("focus_merchant_id",id);
          merchantAttentionMapper.delete(qw);
    }

    @Override
    public void updateFlag(Long id) {
        QueryWrapper<MaterialDetailModel> qw=new QueryWrapper<>();
        MaterialDetailModel ma=new MaterialDetailModel();
       List<MaterialDetailModel> ids=materialDetailMapper.selectList(qw.eq("merchant_id",id));
        for (int i = 0; i < ids.size(); i++) {
            ma.setAttentionFlag(ids.get(i).getAttentionFlag()==0?1:0);
        }
        materialDetailMapper.update(ma,qw);
    }

    @Override
    public int CountAttention(Long merchantId) {
        QueryWrapper<MerchantAttentionModel> qw=new QueryWrapper<>();
        qw.eq("merchant_id",merchantId);
        return merchantAttentionMapper.selectCount(qw);
    }

    @Override
    public int CountFans(Long focusMerchantId) {
        QueryWrapper<MerchantAttentionModel> qw=new QueryWrapper<>();
        qw.eq("focus_merchant_id",focusMerchantId);
        return merchantAttentionMapper.selectCount(qw);
    }

    @Override
    public MerchantAttentionModel isFollow(Long me,Long he) {
        return merchantAttentionMapper.selectOne(
                new QueryWrapper<MerchantAttentionModel>()
                        .eq("merchant_id",me)
                        .eq("focus_merchant_id",he)
        );
    }

}
