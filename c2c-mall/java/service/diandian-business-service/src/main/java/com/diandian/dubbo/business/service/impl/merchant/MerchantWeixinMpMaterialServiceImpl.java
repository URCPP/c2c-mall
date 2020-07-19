package com.diandian.dubbo.business.service.impl.merchant;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.business.mapper.merchant.MerchantWeixinMpMaterialMapper;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.exception.DubboException;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.dto.merchant.MerchantWeixinMpMaterialListDTO;
import com.diandian.dubbo.facade.dto.merchant.MerchantWeixinMpMaterialQueryDTO;
import com.diandian.dubbo.facade.model.merchant.MerchantWeixinMpMaterialModel;
import com.diandian.dubbo.facade.service.merchant.MerchantWeixinMpMaterialService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMaterialService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialCountResult;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialFileBatchGetResult;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNews;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNewsBatchGetResult;
import me.chanjar.weixin.open.api.WxOpenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.Query;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商户微信公众号素材表 服务实现类
 * </p>
 *
 * @author cjunyuan
 * @since size19-06-26
 */
@Service("merchantWeixinMpMaterialService")
public class MerchantWeixinMpMaterialServiceImpl implements MerchantWeixinMpMaterialService {

    @Autowired
    private MerchantWeixinMpMaterialMapper merchantWeixinMpMaterialMapper;

    @Autowired
    private WxOpenService wxOpenService;
    
    private final int size = 20;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncMaterialFromWeixin(String appId){
        WxMpService wxMpService = wxOpenService.getWxOpenComponentService().getWxMpServiceByAppid(appId);
        WxMpMaterialService wxMpMaterialService = wxMpService.getMaterialService();
        try {
            WxMpMaterialCountResult materialCnt = wxMpMaterialService.materialCount();
            QueryWrapper<MerchantWeixinMpMaterialModel> qw = new QueryWrapper<>();
            qw.eq("mp_app_id", appId);
            merchantWeixinMpMaterialMapper.delete(qw);
            this.getNewsMaterialList(wxMpMaterialService, appId, materialCnt.getNewsCount());
            this.getImageMaterialList(wxMpMaterialService, appId, materialCnt.getImageCount());
            this.getVideoMaterialList(wxMpMaterialService, appId, materialCnt.getVideoCount());
            this.getVoiceMaterialList(wxMpMaterialService, appId, materialCnt.getVoiceCount());
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PageResult listForPage(MerchantWeixinMpMaterialQueryDTO dto){
        Page<MerchantWeixinMpMaterialListDTO> page = new PageWrapper<MerchantWeixinMpMaterialListDTO>(dto).getPage();
        IPage<MerchantWeixinMpMaterialListDTO> iPage = merchantWeixinMpMaterialMapper.listForPage(page, dto);
        return new PageResult(iPage);
    }

    private void getNewsMaterialList(WxMpMaterialService wxMpMaterialService, String appId, Integer totalCnt){
        try {
            for (int i = 0;i < totalCnt;i += size){
                WxMpMaterialNewsBatchGetResult materialNewsRes = wxMpMaterialService.materialNewsBatchGet(i, size);
                List<WxMpMaterialNewsBatchGetResult.WxMaterialNewsBatchGetNewsItem> items = materialNewsRes.getItems();
                for (WxMpMaterialNewsBatchGetResult.WxMaterialNewsBatchGetNewsItem item : items){
                    MerchantWeixinMpMaterialModel material = new MerchantWeixinMpMaterialModel();
                    material.setUpdateTime(item.getUpdateTime());
                    material.setMediaId(item.getMediaId());
                    material.setType(BizConstant.WeixinMaterialType.NEWS.value());
                    material.setMpAppId(appId);
                    merchantWeixinMpMaterialMapper.insert(material);
                    WxMpMaterialNews content = item.getContent();
                    for (WxMpMaterialNews.WxMpMaterialNewsArticle article : content.getArticles()){
                        MerchantWeixinMpMaterialModel subMaterial = new MerchantWeixinMpMaterialModel();
                        subMaterial.setParentId(material.getId());
                        subMaterial.setType(BizConstant.WeixinMaterialType.NEWS.value());
                        subMaterial.setThumbMediaId(article.getThumbMediaId());
                        subMaterial.setTitle(article.getTitle());
                        subMaterial.setAuthor(article.getAuthor());
                        subMaterial.setDigest(article.getDigest());
                        subMaterial.setContent(article.getContent());
                        subMaterial.setContentSourceUrl(article.getContentSourceUrl());
                        subMaterial.setMpAppId(appId);
                        subMaterial.setUrl(article.getUrl());
                        subMaterial.setShowCoverPic(article.isShowCoverPic() ? 1 : 0);
                        merchantWeixinMpMaterialMapper.insert(subMaterial);
                    }
                }
            }
        }catch (WxErrorException e){
            throw new DubboException("微信图文素材获取失败");
        }
    }

    private void getVideoMaterialList(WxMpMaterialService wxMpMaterialService, String appId, Integer totalCnt){
        try {
            for (int i = 0;i < totalCnt;i += size){
                WxMpMaterialFileBatchGetResult materialFileRes = wxMpMaterialService.materialFileBatchGet(WxConsts.MaterialType.VIDEO, i, size);
                List<WxMpMaterialFileBatchGetResult.WxMaterialFileBatchGetNewsItem> items = materialFileRes.getItems();
                for (WxMpMaterialFileBatchGetResult.WxMaterialFileBatchGetNewsItem item : items){
                    MerchantWeixinMpMaterialModel material = new MerchantWeixinMpMaterialModel();
                    material.setType(BizConstant.WeixinMaterialType.VIDEO.value());
                    material.setMediaId(item.getMediaId());
                    material.setName(item.getName());
                    material.setUrl(item.getUrl());
                    material.setUpdateTime(item.getUpdateTime());
                    material.setMpAppId(appId);
                    merchantWeixinMpMaterialMapper.insert(material);
                }
            }
        }catch (WxErrorException e){
            throw new DubboException("微信视频素材获取失败");
        }
    }

    private void getImageMaterialList(WxMpMaterialService wxMpMaterialService, String appId, Integer totalCnt){
        try {
            for (int i = 0;i < totalCnt;i += size){
                WxMpMaterialFileBatchGetResult materialFileRes = wxMpMaterialService.materialFileBatchGet(WxConsts.MaterialType.IMAGE, i, size);
                List<WxMpMaterialFileBatchGetResult.WxMaterialFileBatchGetNewsItem> items = materialFileRes.getItems();
                for (WxMpMaterialFileBatchGetResult.WxMaterialFileBatchGetNewsItem item : items){
                    MerchantWeixinMpMaterialModel material = new MerchantWeixinMpMaterialModel();
                    material.setType(BizConstant.WeixinMaterialType.IMAGE.value());
                    material.setMediaId(item.getMediaId());
                    material.setName(item.getName());
                    material.setUrl(item.getUrl());
                    material.setUpdateTime(item.getUpdateTime());
                    material.setMpAppId(appId);
                    merchantWeixinMpMaterialMapper.insert(material);
                }
            }
        }catch (WxErrorException e){
            throw new DubboException("微信图片素材获取失败");
        }
    }

    private void getVoiceMaterialList(WxMpMaterialService wxMpMaterialService, String appId, Integer totalCnt){
        try {
            for (int i = 0;i < totalCnt;i += size){
                WxMpMaterialFileBatchGetResult materialFileRes = wxMpMaterialService.materialFileBatchGet(WxConsts.MaterialType.VOICE, i, size);
                List<WxMpMaterialFileBatchGetResult.WxMaterialFileBatchGetNewsItem> items = materialFileRes.getItems();
                for (WxMpMaterialFileBatchGetResult.WxMaterialFileBatchGetNewsItem item : items){
                    MerchantWeixinMpMaterialModel material = new MerchantWeixinMpMaterialModel();
                    material.setType(BizConstant.WeixinMaterialType.VOICE.value());
                    material.setMediaId(item.getMediaId());
                    material.setName(item.getName());
                    material.setUrl(item.getUrl());
                    material.setUpdateTime(item.getUpdateTime());
                    material.setMpAppId(appId);
                    merchantWeixinMpMaterialMapper.insert(material);
                }
            }
        }catch (WxErrorException e){
            throw new DubboException("微信音频素材获取失败");
        }
    }
}
