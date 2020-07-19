package com.diandian.dubbo.business.service.impl.material;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.dubbo.business.mapper.material.MaterialDetailMapper;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.material.MaterialDetailModel;
import com.diandian.dubbo.facade.model.material.MaterialShareModel;
import com.diandian.dubbo.facade.model.material.res.MaterialDetailRes;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.merchant.MerchantAttentionModel;
import com.diandian.dubbo.facade.model.merchant.MerchantCollectModel;
import com.diandian.dubbo.facade.model.product.ProductInfoModel;
import com.diandian.dubbo.facade.service.material.MaterialDetailService;
import com.diandian.dubbo.facade.service.material.MaterialShareService;
import com.diandian.dubbo.facade.service.merchant.MerchantAttentionService;
import com.diandian.dubbo.facade.service.merchant.MerchantCollectService;
import com.diandian.dubbo.facade.service.product.ProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("materialDetailService")
@Slf4j
public class MaterialDetailServiceImpl implements MaterialDetailService {


    @Autowired
    private MaterialDetailMapper materialDetailMapper;

    @Autowired
    private MaterialShareService materialShareService;

    @Autowired
    private MerchantAttentionService merchantAttentionService;

    @Autowired
    private MerchantCollectService merchantCollectService;

    @Value("${softwareTypeId}")
    private Long softwareTypeId;

    @Value("${businessMapper}")
    private String businessMapper;

    @Value("${productMapper}")
    private String productMapper;


    @Override
    public PageResult listPage(Map<String, Object> params) {


        return null;
    }

    @Override
    public MaterialDetailModel getById(Long id) {
        return null;
    }

    @Override
    public void updateById(MaterialDetailModel MaterialDetailModel) {

    }

    /**
     * 保存素材
     *
     * @param materialDetailModel 实体对象
     */
    @Override
    public void save(MaterialDetailModel materialDetailModel) {
        StringBuffer sb = new StringBuffer();
        if (null != materialDetailModel.getImgUrl() && materialDetailModel.getImgUrl().length > 0) {
            for (int i = 0; i < materialDetailModel.getImgUrl().length; i++) {
                sb.append(materialDetailModel.getImgUrl()[i] + ",");
            }
            sb.deleteCharAt(sb.length() - 1);
            materialDetailModel.setMaterialImg(sb.toString());
        }
        materialDetailMapper.insert(materialDetailModel);

    }

    /**
     * 查看指定商品下的素材详情
     *
     * @param params
     * @return
     */

    @Override
    public List<MaterialDetailModel> FindList(Map<String, Object> params) {
        List<MaterialDetailModel> list = materialDetailMapper.findProductId(params);
        Long merchantId = Long.parseLong(params.get("merchantId").toString());
        for (MaterialDetailModel materialDetailModel : list) {
            MerchantAttentionModel ma = merchantAttentionService.isFollow(merchantId, materialDetailModel.getMerchantId());
            MerchantCollectModel mc = merchantCollectService.isCollect(materialDetailModel.getId(), merchantId);
            if (null == ma) {
                materialDetailModel.setAttentionFlag(0);
            } else {
                materialDetailModel.setAttentionFlag(1);
            }
            if (null == mc) {
                materialDetailModel.setCollectState(0);
            } else {
                materialDetailModel.setCollectState(1);
            }
            if (null != materialDetailModel.getMaterialImg()) {
                String[] arr = materialDetailModel.getMaterialImg().split(",");
                materialDetailModel.setImgUrl(arr);
            }
        }
        return list;
        /*List<MaterialDetailModel> list= materialDetailMapper.findProductId(params);
        String img[]=new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setMe(CountMe(Long.parseLong(params.get("productId").toString()),Long.parseLong(params.get("merchantId").toString())));
            list.get(i).setNews(CountNews(Long.parseLong(params.get("productId").toString())));
            list.get(i).setCollect(merchantCollectService.CountCollect(Long.parseLong(params.get("merchantId").toString())));
            MaterialDetailModel md= materialDetailMapper.selectById(list.get(i).getId());
            img[i]=md.getMaterialImg();
            list.get(i).setImgUrl(img);
        }

        return list;*/
    }

    /**
     * 点击修改收藏状态
     *
     * @param id 素材id
     * @return
     */
    @Override
    public boolean clickCollect(Long id) {
        MaterialDetailModel ma = materialDetailMapper.selectById(id);
        ma.setCollectState(ma.getCollectState() == 0 ? 1 : 0);
        return materialDetailMapper.updateById(ma) > 0;
    }

    /**
     * 点击添加分享数
     *
     * @param id 素材id
     */
    @Override
    public void clickShare(Long id, Long merchantId) {
        MaterialDetailModel ma = materialDetailMapper.selectById(id);
        MaterialShareModel ms = new MaterialShareModel();
        if (materialShareService.findShareById(merchantId, id) == null) {
            ma.setShareNum(ma.getShareNum() + 1);
            materialDetailMapper.updateById(ma);
            ms.setMaterialId(id);
            ms.setMerchantId(merchantId);
            materialShareService.save(ms);
        }
    }

    /**
     * 点击用户头像进入个人中心素材页面
     *
     * @param MerchantId        用户id
     * @param typeId            类型id
     * @param merchantInfoModel 当前用户信息对象
     * @return
     */
    @Override
    public List<MaterialDetailRes> findMerchantMaterial(Long MerchantId, Integer typeId, MerchantInfoModel merchantInfoModel) {
        List<MaterialDetailRes> materialDetailResList;
        if (typeId == 1) {
            materialDetailResList = materialDetailMapper.findMyCollect(MerchantId, softwareTypeId, typeId, businessMapper, productMapper);
        } else {
            materialDetailResList = materialDetailMapper.findList(MerchantId, softwareTypeId, businessMapper, productMapper);
        }

        for (MaterialDetailRes materialDetailRes : materialDetailResList) {
            if (null != materialDetailRes.getMaterialImg()) {
                String[] arr = materialDetailRes.getMaterialImg().split(",");
                materialDetailRes.setImgUrls(arr);
            }
        }
        return materialDetailResList;

        /*List<Long> idx = new ArrayList<>();
        List<MaterialDetailModel> list;
        if(typeId==1){
            list=materialDetailMapper.findMyCollect(meId.getId());
        }else{
            list = materialDetailMapper.findList(MerchantId);
        }
        if (list.isEmpty()){
            return list;
        }
        for (MaterialDetailModel materialDetailModel : list) {
            Long ids = materialDetailModel.getProductId();
            idx.add(ids);
        }
        List<ProductInfoModel> listP = productInfoService.getByIds(idx);
        String img[]=new String[listP.size()];
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < listP.size(); j++) {
                if (list.get(i).getProductId().equals(listP.get(j).getId())) {
                    list.get(i).setProductName(listP.get(j).getProductName());
                    int finalI = i;
                    listP.get(j).getSkuList().forEach(productSkuModel -> {
                        productSkuModel.getPriceList().forEach(productSkuPriceModel -> {
                            if (productSkuPriceModel.getSoftwareTypeId().equals(1104994625655316481L)){
                                list.get(finalI).setPrice(productSkuPriceModel.getPrice());
                            }
                        });
                    });
                    list.get(i).setMaterialImg(listP.get(j).getImageUrls().split(",")[0]);
                    img[j]=list.get(i).getMaterialImg();
                }

            }
            list.get(i).setImage(CountImage(MerchantId));
            list.get(i).setCollect(merchantCollectService.CountCollect(MerchantId));
            list.get(i).setImgUrl(img);
        }

        return list;*/
    }


    /**
     * 统计图文素材
     *
     * @param merchantId 用户id
     * @return
     */

    @Override
    public int CountImage(Long merchantId) {
        QueryWrapper<MaterialDetailModel> qw = new QueryWrapper<>();
        qw.eq("merchant_id", merchantId);
        return materialDetailMapper.selectCount(qw);
    }

    /**
     * 统计最新素材
     *
     * @param productId 产品id
     * @return
     */
    @Override
    public int CountNews(Long productId) {
        return materialDetailMapper.CountNews(productId);
    }

    /**
     * 统计我的素材
     *
     * @param productId  产品id
     * @param merchantId 用户id
     * @return
     */
    @Override
    public int CountMe(Long productId, Long merchantId) {
        return materialDetailMapper.CountMe(productId, merchantId);
    }


}
