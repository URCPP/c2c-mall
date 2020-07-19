package com.diandian.dubbo.business.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.diandian.dubbo.business.mapper.RepositoryInfoMapper;
import com.diandian.dubbo.business.mapper.transport.TransportInfoMapper;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.util.AssertUtil;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.model.product.ProductInfoModel;
import com.diandian.dubbo.facade.model.repository.RepositoryInfoModel;
import com.diandian.dubbo.facade.service.product.ProductInfoService;
import com.diandian.dubbo.facade.service.repository.RepositoryInfoService;
import com.diandian.dubbo.facade.vo.RepositoryDetailVO;
import com.diandian.dubbo.facade.vo.TransportInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 仓库信息
 *
 * @author zzhihong
 * @date 2019/02/22
 */
@Service("repositoryInfoService")
@Slf4j
public class RepositoryInfoServiceImpl implements RepositoryInfoService {

    @Autowired
    private RepositoryInfoMapper repositoryInfoMapper;

    @Autowired
    private TransportInfoMapper transportInfoMapper;

    @Autowired
    private ProductInfoService productInfoService;

    @Override
    public List<RepositoryInfoModel> list(Long shopId) {
        QueryWrapper<RepositoryInfoModel> wrapper = new QueryWrapper<>();
        if (null!=shopId&&Long.valueOf(0)!=shopId){
            wrapper.eq("shop_info_id",shopId);
        }
        wrapper.eq("del_flag", BizConstant.STATE_NORMAL);
        return repositoryInfoMapper.selectList(wrapper);
    }

    @Override
    public PageResult listPage(Map<String, Object> params) {
        String keyword = (String) params.get("keyword");
        String shopId = null == params.get("shopId")?"":params.get("shopId").toString();
        IPage<RepositoryInfoModel> page = repositoryInfoMapper.selectPage(new PageWrapper<RepositoryInfoModel>(params).getPage(),
                new LambdaQueryWrapper<RepositoryInfoModel>()
                        .eq(RepositoryInfoModel::getDelFlag,BizConstant.STATE_NORMAL).eq(StrUtil.isNotBlank(shopId),RepositoryInfoModel::getShopInfoId,shopId)
                        .like(StrUtil.isNotBlank(keyword),RepositoryInfoModel::getRepositoryName,keyword));
        return new PageResult(page);
    }

    @Override
    public RepositoryInfoModel getById(Long id) {
        return repositoryInfoMapper.selectById(id);
    }

    @Override
    public void save(RepositoryInfoModel repositoryInfoModel) {
        repositoryInfoMapper.insert(repositoryInfoModel);
    }

    @Override
    public void updateById(RepositoryInfoModel repositoryInfoModel) {
        repositoryInfoMapper.updateById(repositoryInfoModel);
    }

    @Override
    public void logicDeleteById(Long id) {
        RepositoryInfoModel repositoryInfoModel = new RepositoryInfoModel();
        repositoryInfoModel.setId(id);
        repositoryInfoModel.setDelFlag(BizConstant.STATE_DISNORMAL);
        repositoryInfoMapper.updateById(repositoryInfoModel);
    }

    @Override
    public List<RepositoryDetailVO> getRepositoryDetailByProduct(Long productId, Long skuId, List<Integer> transportTypeIds){
        ProductInfoModel product = productInfoService.getById(productId);
        AssertUtil.notNull(product, "商品信息不存在");
        List<RepositoryDetailVO> list = productInfoService.getProductRepositoryStock(productId, skuId);
        List<Long> repositoryIds = new ArrayList<>();
        Iterator<RepositoryDetailVO> it = list.iterator();
        while (it.hasNext()){
            repositoryIds.add(it.next().getRepositoryId());
        }
        List<RepositoryDetailVO> list1 = repositoryInfoMapper.listByRepositoryId(repositoryIds);
        for(int i = 0,size = list1.size();i < size;++i){
            list.get(i).setAddress(list1.get(i).getAddress());
            list.get(i).setContactName(list1.get(i).getContactName());
            list.get(i).setContactPhone(list1.get(i).getContactPhone());
        }
        List<TransportInfoVO> transportList = transportInfoMapper.getTransportVOList(product.getTransportIds(), transportTypeIds);
        for (RepositoryDetailVO repository : list){
            for (TransportInfoVO item : transportList){
                if(item.getRepositoryId().equals(repository.getRepositoryId())){
                    repository.addTransport(item);
                }
            }
        }
        return list;
    }

    @Override
    public List<RepositoryInfoModel> getRepositoryByShopId(Long shopId) {
        return repositoryInfoMapper.selectList(new QueryWrapper<RepositoryInfoModel>()
                .eq("shop_info_id",shopId)
                .eq("del_flag",BizConstant.STATE_NORMAL));
    }
}
